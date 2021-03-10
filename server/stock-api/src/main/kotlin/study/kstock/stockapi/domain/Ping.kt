package study.kstock.stockapi.domain

import java.io.Serializable


class Ping(var msg: String?, var name: String?) : Serializable {

    override fun toString(): String {
        return "$msg, $name!"
    }
}