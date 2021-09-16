package co.com.ceiba.patternadapter.data.model

data class Event(
    val date:String,
    val startHour:String,
    val endHour:String,
    val ubication: Ubication,
    val eventName:String
)