package jp.ats.kotlinlearning.controller

import jp.ats.kotlinlearning.model.Event
import jp.ats.kotlinlearning.model.EventCreateRequest
import jp.ats.kotlinlearning.service.EventService
import jp.ats.kotlinlearning.service.TypeConvertor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("events")
class EventController(
    private val eventService: EventService
) {

    @PostMapping
    fun createEvent(@RequestBody request: EventCreateRequest) {

        // TODO: validateして、ダメだったらBadRequestExceptionなげて400エラーを投げる。
        //       ExceptionHandlerを定義する

        // request -> modelへ変換
        // TODO: modelMapper を使って変換する
        val model: Event = Event(
            id = null,
            organizerId = request.organizerId,
            eventName = request.eventName,
            startsAt = TypeConvertor.strToLocalDateTime(
                request.startsAt, TypeConvertor.FORMAT_YYYYMMDDHHMMSS
            ),
            endsAt =TypeConvertor.strToLocalDateTime(
                request.endsAt, TypeConvertor.FORMAT_YYYYMMDDHHMMSS
            ),
            eventDetails = null
        )

        eventService.createEvent(model)
    }

    @GetMapping
    fun findEvents(): List<Event> = eventService.findEvents()

    @GetMapping("{id}")
    fun findEventById(@PathVariable id: Int) = eventService.findEventById(id)

    @GetMapping("{id}/participants")
    fun findEventsWithOrganizerAndParticipants(@PathVariable id: Int) = eventService.findEventsWithOrganizerAndParticipants(id)

}