package study.kstock.stockapi.message

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseFactory {

    companion object {
        fun <T> createResponse(result: MutableList<T>): ResponseEntity<MutableList<T>> {
            val status = if (result.isEmpty()) HttpStatus.NOT_FOUND else HttpStatus.OK
            return ResponseEntity(result, status)
        }

        fun <T> createResponse(result: T): ResponseEntity<T> {
            val status = if (result == null) HttpStatus.NOT_FOUND else HttpStatus.OK
            return ResponseEntity(result, status)
        }
    }
}