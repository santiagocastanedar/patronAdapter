package co.com.ceiba.patternadapter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val enventId:Int?,
    @ColumnInfo(name = "eventName")
    val eventName:String = "",
    @ColumnInfo(name = "eventDate")
    val eventDate:String = "",
    @ColumnInfo(name = "startHour")
    val startHour:String = "",
    @ColumnInfo(name = "endHour")
    val endHour:String = "",
    @ColumnInfo(name = "latitude")
    val latitude:Double,
    @ColumnInfo(name = "longitude")
    val longitude:Double,
    @ColumnInfo(name = "placeName")
    val placeName:String = ""
)