package co.com.ceiba.patternadapter.domain

import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.vo.Resource

interface EventRepository {
    suspend fun createEvent(event: Event)
    suspend fun editEvent()
    suspend fun deleteEvent()
    suspend fun getEvents(): Resource<List<EventEntity>>
}