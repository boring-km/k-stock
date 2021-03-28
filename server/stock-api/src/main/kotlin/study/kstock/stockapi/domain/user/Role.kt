package study.kstock.stockapi.domain.user

enum class Role(
    private var key: String,
    private var title: String
) {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    fun getKey(): String {
        return key
    }

    fun getTitle(): String {
        return title
    }
}
