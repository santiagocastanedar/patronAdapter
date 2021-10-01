package co.com.ceiba.patternadapter.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    var id: Long?,
    val startDate:String,
    val endDate:String,
    val placeName:String,
    val eventName:String
):Parcelable