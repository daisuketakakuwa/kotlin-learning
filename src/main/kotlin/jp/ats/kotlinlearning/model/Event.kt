package jp.ats.kotlinlearning.model

import jp.ats.kotlinlearning.annotation.NoArgsConstructor
import org.apache.ibatis.annotations.AutomapConstructor
import java.time.LocalDateTime

@NoArgsConstructor
data class Event(
    val id: Long?, // BigSerialだからLong
    val organizerId: Int, // SerialだからInt
    val eventName: String?,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val eventDetails: EventDetails?
)

@NoArgsConstructor
data class EventWithParticipants @AutomapConstructor constructor(
    val id: Long?, // BigSerialだからLong
    val eventName: String?,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val eventDetails: EventDetails?,
    val organizer: Organizer?,
    val participants: List<Participant>
)

data class EventCreateRequest(
    val organizerId: Int, // SerialだからInt
    val eventName: String,
    val startsAt: String,
    val endsAt: String,
    val eventDetails: String?
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    // → noargsプラグインで @NoArgsConstructorを使えばOK👍
    constructor() : this(
        0,
        "",
        "",
        "",
        ""
    )
}

data class EventDetails(
    val description: String,
    val address1: String,
    val address2: String
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor(): this("","","")
}

