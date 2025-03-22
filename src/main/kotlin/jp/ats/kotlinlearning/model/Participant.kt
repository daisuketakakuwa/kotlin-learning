package jp.ats.kotlinlearning.model

data class Participant(
    val id: Long,  // BigSerialだからLong
    val firstName: String,
    val lastName: String,
    val gender: String
)