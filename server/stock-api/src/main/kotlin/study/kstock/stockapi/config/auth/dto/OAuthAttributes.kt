package study.kstock.stockapi.config.auth.dto

import study.kstock.stockapi.domain.user.Role
import study.kstock.stockapi.domain.user.User

class OAuthAttributes(
    attributes: Map<String, Any>,
    nameAttributeKey: String,
    name: String,
    email: String,
    picture: String
) {

    var attributes: Map<String, Any> = attributes
        private set
    var nameAttributeKey: String = nameAttributeKey
        private set
    var name: String = name
        private set
    var email: String = email
        private set
    var picture: String = picture
        private set

    fun toEntity(): User {
        return User(
            name, email, picture, Role.GUEST
        )
    }
}