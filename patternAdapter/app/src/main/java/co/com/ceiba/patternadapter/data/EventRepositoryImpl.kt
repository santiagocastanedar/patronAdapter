package co.com.ceiba.patternadapter.data

import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.domain.EventRepository
import co.com.ceiba.patternadapter.vo.Resource
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class EventRepositoryImpl(private val dataSource: DataSource): EventRepository {
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