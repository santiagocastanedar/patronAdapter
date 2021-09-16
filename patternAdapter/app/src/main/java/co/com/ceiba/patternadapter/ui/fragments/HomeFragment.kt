package co.com.ceiba.patternadapter.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.R
import co.com.ceiba.patternadapter.data.DataSource
import co.com.ceiba.patternadapter.data.model.Event
import co.com.ceiba.patternadapter.data.model.Ubication
import co.com.ceiba.patternadapter.domain.EventRepositoryImpl
import co.com.ceiba.patternadapter.ui.viewmodel.HomeViewModel
import co.com.ceiba.patternadapter.ui.viewmodel.VMFactory
import co.com.ceiba.patternadapter.vo.Resource


class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel> {VMFactory(EventRepositoryImpl(
        DataSource(appDataBase = AppDataBase.getDatabase(requireActivity().applicationContext))
    ))  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.insertEvent(Event("2022","10","11", Ubication(1.2,1.4,"casa"),"reunion"))
        getEvents()
    }

    private fun getEvents(){
        viewModel.getEvents().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    Log.d("DATA1","Error")
                }
                is Resource.Success ->{
                    var eventList:List<Event> = result.data.map { eventEntity ->
                        Event(
                            eventEntity.eventDate,
                            eventEntity.startHour,
                            eventEntity.endHour,
                            Ubication(eventEntity.latitude,eventEntity.longitude,eventEntity.placeName),
                            eventEntity.eventName
                        )
                    }
                    Log.d("DATA1","${eventList}")
                }
            }
        })
    }
}