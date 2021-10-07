package co.com.ceiba.patternadapter.data

import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.vo.Resource

class DataSource(private val appDataBase: AppDataBase) {

    suspend fun insertEvent(event: Event){
        appDataBase.eventDao().insertEvent(
            EventEntity(
                enventId = event.id,
                eventName = event.eventName,
                startDate = event.startDate,
                endDate = event.endDate,
                placeName = event.placeName
            )
        )
    }

    suspend fun getEvents():Resource<List<EventEntity>>{
        return Resource.Success(appDataBase.eventDao().getEvents())
    }

    suspend fun deleteEvent(event: Event){
        appDataBase.eventDao().deleteEvent(
            EventEntity(
                event.id,
                eventName = event.eventName,
                startDate = event.startDate,
                endDate = event.endDate,
                placeName = event.placeName
            )
        )
    }
}