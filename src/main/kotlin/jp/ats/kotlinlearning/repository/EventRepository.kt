package jp.ats.kotlinlearning.repository

import jp.ats.kotlinlearning.model.Event
import jp.ats.kotlinlearning.model.EventWithParticipants
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
interface EventRepository {

    fun createEvent(model: Event)

    fun findEvents(): List<Event>

    fun findEventById(@Param("id") id: Int): Event

    fun findEventsWithOrganizerAndParticipants(@Param("id") id: Int): EventWithParticipants

}