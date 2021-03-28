package study.kstock.stockapi.domain

import java.time.LocalDateTime

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    lateinit var createdDate: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var modifiedDate: LocalDateTime
        private set
}