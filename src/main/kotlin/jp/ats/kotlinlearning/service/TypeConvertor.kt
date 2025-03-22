package jp.ats.kotlinlearning.service

import org.springframework.context.annotation.Bean
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TypeConvertor {

    val FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"

    fun strToLocalDateTime(value: String, format: String): LocalDateTime {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(format)
        return LocalDateTime.parse(value, formatter)
    }

}