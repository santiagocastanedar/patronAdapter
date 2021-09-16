package co.com.ceiba.patternadapter.domain

import co.com.ceiba.patternadapter.data.DataSource
import co.com.ceiba.patternadapter.data.model.Event
import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.vo.Resource

class EventRepositoryImpl(private val dataSource: DataSource):EventRepository {
    override suspend fun createEvent(event: Event) {
        return dataSource.insertEvent(event)
    }

    override suspend fun editEvent() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEvent() {
        TODO("Not yet implemented")
    }

    override suspend fun getEvents(): Resource<List<EventEntity>> {
        return dataSource.getEvents()
    }


}