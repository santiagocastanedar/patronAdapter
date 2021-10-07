package co.com.ceiba.patternadapter.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import co.com.ceiba.patternadapter.calendar.adapter.CalendarEventAdapter
import co.com.ceiba.patternadapter.calendar.model.CalendarEvent
import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.domain.EventRepository
import co.com.ceiba.patternadapter.vo.Resource
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class EventRepositoryImpl(private val dataSource: DataSource,private val context: Context): EventRepository {
    override suspend fun createEvent(event: Event) {
        val calendarEvent: CalendarEvent = CalendarEventAdapter(event).eventToCalendarEventAdapter()
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART,calendarEvent.startMillis)
            put(CalendarContract.Events.DTEND,calendarEvent.endMillis)
            put(CalendarContract.Events.TITLE,calendarEvent.title)
            put(CalendarContract.Events.DESCRIPTION,calendarEvent.description)
            put(CalendarContract.Events.CALENDAR_ID,calendarEvent.calendarID)
            put(CalendarContract.Events.EVENT_TIMEZONE,calendarEvent.timeZone)
        }
        val uri: Uri? = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
        val eventID: Long? = uri?.lastPathSegment?.toLong()
        event.id = eventID
        return dataSource.insertEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        val eventID: Long? = event.id
        val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,
            eventID!!
        )
        val rows: Int = context.contentResolver.delete(deleteUri, null, null)
        dataSource.deleteEvent(event)
    }

    override suspend fun getEvents(): Resource<List<EventEntity>> {
        return dataSource.getEvents()
    }


}