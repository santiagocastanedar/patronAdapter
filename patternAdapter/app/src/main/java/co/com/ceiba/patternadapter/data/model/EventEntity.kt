package co.com.ceiba.patternadapter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    val enventId: Long?,
    @ColumnInfo(name = "eventName")
    val eventName:String = "",
    @ColumnInfo(name = "startDate")
    val startDate:String = "",
    @ColumnInfo(name = "endDate")
    val endDate:String = "",
    @ColumnInfo(name = "placeName")
    val placeName:String = ""
)