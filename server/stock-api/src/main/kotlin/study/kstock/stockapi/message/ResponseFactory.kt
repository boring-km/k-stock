package study.kstock.stockapi.message

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseFactory {

    companion object {
        fun createResponse(result: Array<Any>): ResponseEntity<Array<Any>> {
            val status = if (result.isEmpty()) HttpStatus.NOT_FOUND else HttpStatus.OK
            return ResponseEntity(result, status)
        }
    }
}