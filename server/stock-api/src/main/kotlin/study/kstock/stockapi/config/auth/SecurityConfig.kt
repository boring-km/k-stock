package study.kstock.stockapi.config.auth

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import study.kstock.stockapi.user.Role
import kotlin.jvm.Throws

@EnableWebSecurity
class SecurityConfig(): WebSecurityConfigurerAdapter() {
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    constructor(customOAuth2UserService: CustomOAuth2UserService) : this() {
        this.customOAuth2UserService = customOAuth2UserService
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name)
            .anyRequest().authenticated()
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
    }

}