package study.kstock.stockcore.message

class ApiRequest(private var request: LinkedHashMap<String, Any>) {

    fun command(): String {
        return request.getOrDefault("command", "") as String
    }

    fun data(): Any {
        return request.getOrDefault("data", "")
    }
}
