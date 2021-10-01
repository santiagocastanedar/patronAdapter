package co.com.ceiba.patternadapter.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.R
import co.com.ceiba.patternadapter.data.DataSource
import co.com.ceiba.patternadapter.data.EventRepositoryImpl
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.ui.viewmodel.HomeViewModel
import co.com.ceiba.patternadapter.ui.viewmodel.InformationViewModel
import co.com.ceiba.patternadapter.ui.viewmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_create_event.*
import kotlinx.android.synthetic.main.fragment_event_information.*
import kotlinx.android.synthetic.main.fragment_event_information.buttonUpdate
import kotlinx.android.synthetic.main.fragment_event_information.editTextEventName
import kotlinx.android.synthetic.main.fragment_event_information.editTextPlaceName
import kotlinx.android.synthetic.main.fragment_event_information.textViewEndDate
import kotlinx.android.synthetic.main.fragment_event_information.textViewEndHour
import kotlinx.android.synthetic.main.fragment_event_information.textViewStartDate
import kotlinx.android.synthetic.main.fragment_event_information.textViewStartHour
import java.text.SimpleDateFormat
import java.util.*


class EventInformationFragment : Fragment() {
    private lateinit var event:Event
    private val c = Calendar.getInstance()

    private val viewModel by activityViewModels<InformationViewModel> {
        VMFactory(
        EventRepositoryImpl(
            DataSource(appDataBase = AppDataBase.getDatabase(requireActivity().applicationContext))
        ,requireActivity().applicationContext)
    )  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            event = it.getParcelable<Event>("event")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInfo()
        setupTextView()

        buttonDelete.setOnClickListener {
            viewModel.deleteEvent(event)
            Toast.makeText(requireContext(),"Evento eliminado con exito",Toast.LENGTH_LONG)
            //findNavController().navigate(R.id.homeFragment)
        }

        buttonEdit.setOnClickListener {
            linearLayoutUpdate.visibility = View.VISIBLE
            cardViewInfo.visibility = View.GONE
            buttonEdit.visibility = View.GONE
            buttonUpdate.visibility = View.VISIBLE
        }

        buttonUpdate.setOnClickListener {
            update()
        }
    }

    private fun update(){
        val eventName = editTextEventName.text.toString()
        val eventPlace = editTextPlaceName.text.toString()
        val startDate = "${textViewStartDate.text} ${textViewStartHour.text}"
        val endDate = "${textViewEndDate.text} ${textViewEndHour.text}"
        if (eventName.equals("")){
            Toast.makeText(requireContext(),"El nombre del evento es obligatorio",Toast.LENGTH_LONG).show()
        }else{
            event = Event(event.id,startDate,endDate,eventPlace,eventName)
            viewModel.updateEvent(event)
            Toast.makeText(requireContext(),"Evento actualizado con exito",Toast.LENGTH_LONG).show()
            linearLayoutUpdate.visibility = View.GONE
            cardViewInfo.visibility = View.VISIBLE
            buttonEdit.visibility = View.VISIBLE
            buttonUpdate.visibility = View.GONE
            setUpInfo()
        }
    }

    private fun setUpInfo(){
        textViewEventName.text = event.eventName
        textViewEventPlace.text = event.placeName
        textViewStartEvent.text = event.startDate
        textViewEndEvent.text = event.endDate

        editTextEventName.setText(event.eventName)
        editTextPlaceName.setText(event.placeName)
        val startDate = event.startDate.split(" ")
        val endDate = event.endDate.split(" ")
        textViewStartDate.text = startDate[0]
        textViewEndDate.text = endDate[0]
        textViewStartHour.text = startDate[1]
        textViewEndHour.text = endDate[1]
    }

    private fun setupTextView(){

        textViewStartDate.setOnClickListener {
            showDatePickerDialog(textViewStartDate)
            textViewEndDate.visibility = View.VISIBLE
            textViewEndHour.visibility = View.VISIBLE
        }
        textViewEndDate.setOnClickListener {
            showDatePickerDialog(textViewEndDate)
        }
        textViewStartHour.setOnClickListener {
            showTimePickerDialog(textViewStartHour)
            textViewEndDate.visibility = View.VISIBLE
            textViewEndHour.visibility = View.VISIBLE
        }
        textViewEndHour.setOnClickListener {
            showTimePickerDialog(textViewEndHour)
        }
        textViewStartDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                textViewEndDate.text = s.toString()
            }

        })
    }

    private fun showDatePickerDialog(textView: TextView){
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            textView.text = "$dayOfMonth-${monthOfYear+1}-$year"

        }, year, month, day)
        if (textView.equals(textViewEndDate)){
            val mDate = SimpleDateFormat("dd-M-yyyy").parse(textView.text.toString())
            dpd.datePicker.minDate = mDate.time
        }else{
            dpd.datePicker.minDate = c.timeInMillis
        }

        dpd.show()
    }



    private fun showTimePickerDialog(textView: TextView){

        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            c.set(Calendar.HOUR_OF_DAY,hour)
            c.set(Calendar.MINUTE,minute)
            textView.text = SimpleDateFormat("HH:mm").format(c.time)
        }
        TimePickerDialog(requireActivity(),timeSetListener,c.get(Calendar.HOUR_OF_DAY),c.get(
            Calendar.MINUTE),true).show()
    }
}