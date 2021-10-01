package co.com.ceiba.patternadapter.calendar.model

data class CalendarEvent(
    val startMillis:Long,
    val endMillis:Long,
    val title:String,
    val description:String,
    val calendarID:Long,
    val timeZone:String
)