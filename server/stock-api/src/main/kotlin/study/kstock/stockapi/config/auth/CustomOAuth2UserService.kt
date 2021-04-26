package study.kstock.stockapi.config.auth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import study.kstock.stockapi.config.auth.dto.OAuthAttributes
import study.kstock.stockapi.config.auth.dto.SessionUser
import study.kstock.stockapi.domain.user.User
import study.kstock.stockapi.domain.user.UserRepository
import java.util.*
import javax.servlet.http.HttpSession


@Service
class CustomOAuth2UserService(
    private var userRepository: UserRepository,
    private var httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User: OAuth2User = delegate.loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val attributes: OAuthAttributes = of(registrationId, userNameAttributeName, oAuth2User.attributes)
        val user = saveOrUpdate(attributes) // JPA update

        // TODO: 이부분에서 예외 발생 java.lang.IllegalStateException: No thread-bound request found
        //   -> Redis DB에서 로그인 세션을 관리하는 것으로 변경하기
        (SecurityContextHolder.getContext().authentication.principal as HttpSession).setAttribute("user", user)

        return DefaultOAuth2User(
            Collections.singleton(
                SimpleGrantedAuthority(user.getRoleKey())
            ),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val user = userRepository.findByEmail(attributes.email)
            .map { entity: User -> entity.update(attributes.name, attributes.picture) }
            .orElse(attributes.toEntity())
        return userRepository.save(user)
    }

    private fun of(
        registrationId: String,
        userNameAttributeName: String,
        attributes: Map<String, Any>
    ): OAuthAttributes {
        return ofGoogle(userNameAttributeName, attributes)
    }

    private fun ofGoogle(userNameAttributeName: String, attributes: Map<String, Any>): OAuthAttributes {
        return OAuthAttributes(
            name = attributes["name"] as String,
            email = attributes["email"] as String,
            picture = attributes["picture"] as String,
            attributes = attributes,
            nameAttributeKey = userNameAttributeName
        )
    }
}
