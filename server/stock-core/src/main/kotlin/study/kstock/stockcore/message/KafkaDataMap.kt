package study.kstock.stockcore.message

import study.kstock.stockcore.model.Commander

class KafkaDataMap(private var request: LinkedHashMap<String, Any>) {

    fun command(): Commander {
        return request.getOrDefault<String, Any>("command", Commander.None) as Commander
    }

    fun data(): Any {
        return request.getOrDefault<String, Any>("data", "")
    }
}
