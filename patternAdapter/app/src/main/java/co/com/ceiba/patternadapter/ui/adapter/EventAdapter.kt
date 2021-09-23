package co.com.ceiba.patternadapter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.patternadapter.R
import co.com.ceiba.patternadapter.base.BaseViewHolder
import co.com.ceiba.patternadapter.domain.Event
import kotlinx.android.synthetic.main.event_list_item.view.*

class EventAdapter(private val context: Context,
                   private val eventList:List<Event>,
                   private val itemClickListener:OnEventClickListener):RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnEventClickListener{
        fun onClick(event: Event)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is EventAdapter.EventViewHolder -> holder.bind(eventList[position],position)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class EventViewHolder(itemView: View):BaseViewHolder<Event>(itemView){
        override fun bind(item: Event, position: Int) {
            itemView.eventName.text = item.eventName
            itemView.startEvent.text = item.startDate
            itemView.endEvent.text = item.endDate
            itemView.eventPlace.text = item.placeName
        }

    }
}