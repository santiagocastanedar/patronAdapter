package co.com.ceiba.patternadapter.ui.fragments

import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.R
import co.com.ceiba.patternadapter.data.DataSource
import co.com.ceiba.patternadapter.data.EventRepositoryImpl
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.ui.adapter.EventAdapter
import co.com.ceiba.patternadapter.ui.viewmodel.HomeViewModel
import co.com.ceiba.patternadapter.ui.viewmodel.VMFactory
import co.com.ceiba.patternadapter.vo.Resource
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),EventAdapter.OnEventClickListener {

    private val viewModel by activityViewModels<HomeViewModel> {VMFactory(
        EventRepositoryImpl(
        DataSource(appDataBase = AppDataBase.getDatabase(requireActivity().applicationContext))
        ,requireActivity().applicationContext)
    )  }


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
        setupRecyclerView()
        getEvents()
        checkPermission(42,android.Manifest.permission.READ_CALENDAR,android.Manifest.permission.WRITE_CALENDAR)
    }

    private fun checkPermission(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions =
                permissions && ContextCompat.checkSelfPermission(requireContext(), p) == PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(requireActivity(), permissionsId, callbackId)
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
                            eventEntity.enventId,
                            eventEntity.startDate,
                            eventEntity.endDate,
                            eventEntity.placeName,
                            eventEntity.eventName
                        )
                    }
                    recyclerViewEvents.adapter = EventAdapter(requireContext(),eventList,this)
                }
            }
        })
    }

    private fun setupRecyclerView(){
        recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(event: Event) {
        val bundle = Bundle()
        bundle.putParcelable("event",event)
        findNavController().navigate(R.id.eventInformationFragment,bundle)
    }
}