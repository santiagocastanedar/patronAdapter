package co.com.ceiba.patternadapter.domain

import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.vo.Resource

interface EventRepository {
    suspend fun createEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    suspend fun getEvents(): Resource<List<EventEntity>>
}