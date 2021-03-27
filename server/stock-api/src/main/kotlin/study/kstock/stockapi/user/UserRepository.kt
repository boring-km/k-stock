package study.kstock.stockapi.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
}