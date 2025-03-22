package jp.ats.kotlinlearning.service

import jp.ats.kotlinlearning.model.Event
import jp.ats.kotlinlearning.model.EventWithParticipants
import jp.ats.kotlinlearning.repository.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class EventService(private val eventRepository: EventRepository) {

    // TODO: rollbackFor を使ってみる
    @Transactional
    fun createEvent(model: Event) = eventRepository.createEvent(model)

    fun findEvents(): List<Event> = eventRepository.findEvents()

    fun findEventById(id: Int): Event {
        val event: Event = eventRepository.findEventById(id)
        println(event.eventName.length)
        println(event.startsAt?.format(DateTimeFormatter.ofPattern("YYYY-MM-DD")))
        return event
    }

    fun findEventsWithOrganizerAndParticipants(id: Int): EventWithParticipants {
        val events: EventWithParticipants = eventRepository.findEventsWithOrganizerAndParticipants(id)
        println(events.participants.size)
        return events
    }

}