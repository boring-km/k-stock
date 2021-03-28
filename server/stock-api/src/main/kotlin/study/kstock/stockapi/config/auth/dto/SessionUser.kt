package study.kstock.stockapi.config.auth.dto

import study.kstock.stockapi.domain.user.User
import java.io.Serializable

class SessionUser(user: User) : Serializable {
    var name: String? = user.name
        private set
    var email: String? = user.email
        private set
    var picture: String? = user.picture
        private set
}