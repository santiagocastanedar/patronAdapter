package co.com.ceiba.patternadapter.data

import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.data.model.Event
import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.vo.Resource

class DataSource(private val appDataBase: AppDataBase) {

    suspend fun insertEvent(event: Event){
        appDataBase.eventDao().insertEvent(
            EventEntity(
                null,
                eventName = event.eventName,
                eventDate = event.date,
                startHour = event.startHour,
                endHour = event.endHour,
                latitude = event.ubication.latitude,
                longitude = event.ubication.longitude,
                placeName = event.ubication.placeName
            )
        )
    }

    suspend fun getEvents():Resource<List<EventEntity>>{
        return Resource.Success(appDataBase.eventDao().getEvents())
    }
}