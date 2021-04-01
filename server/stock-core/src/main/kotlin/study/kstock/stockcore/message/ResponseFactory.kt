package study.kstock.stockcore.message

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseFactory {

    companion object {
        fun <T> createResponse(result: MutableList<T>): ResponseEntity<MutableList<T>> {
            val status = if (result.isEmpty()) HttpStatus.NOT_FOUND else HttpStatus.OK
            return ResponseEntity(result, status)
        }
    }
}