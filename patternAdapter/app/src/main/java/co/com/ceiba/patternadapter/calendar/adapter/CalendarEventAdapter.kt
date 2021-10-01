package co.com.ceiba.patternadapter.calendar.adapter

import co.com.ceiba.patternadapter.calendar.model.CalendarEvent
import co.com.ceiba.patternadapter.domain.Event
import java.text.SimpleDateFormat
import java.util.*

class CalendarEventAdapter(private val event:Event) {

    fun eventToCalendarEventAdapter():CalendarEvent{
        val calID: Long = 3
        val c:Calendar = Calendar.getInstance()
        c.time = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(event.startDate)
        val startMillis:Long = c.timeInMillis
        c.time = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(event.endDate)
        val endMillis:Long = c.timeInMillis

        return CalendarEvent(startMillis,endMillis,event.eventName,event.placeName,calID,TimeZone.getDefault().displayName)
    }
}