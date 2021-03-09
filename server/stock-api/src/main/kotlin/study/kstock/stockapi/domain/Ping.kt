package study.kstock.stockapi.domain

import java.io.Serializable


class Ping(private var msg: String?, private var name: String?) : Serializable {

    override fun toString(): String {
        return "$msg, $name!"
    }
}