package study.kstock.stockapi.user

import javax.persistence.*

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    @Column(nullable = false)
    var name: String? = null
        private set

    @Column(nullable = false)
    var email: String? = null
        private set

    @Column
    var picture: String? = null
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = null
        private set

    constructor(name: String?, email: String?, picture: String?, role: Role?) {
        this.name = name
        this.email = email
        this.picture = picture
        this.role = role
    }

    constructor() {}

    fun update(name: String, picture: String): User {
        this.name = name
        this.picture = picture

        return this
    }

    fun getRoleKey(): String {
        return this.role!!.getKey()
    }
}