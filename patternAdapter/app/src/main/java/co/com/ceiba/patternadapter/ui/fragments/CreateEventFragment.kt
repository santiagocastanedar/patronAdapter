package co.com.ceiba.patternadapter.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import co.com.ceiba.patternadapter.AppDataBase
import co.com.ceiba.patternadapter.R
import co.com.ceiba.patternadapter.calendar.adapter.CalendarEventAdapter
import co.com.ceiba.patternadapter.calendar.model.CalendarEvent
import co.com.ceiba.patternadapter.data.DataSource
import co.com.ceiba.patternadapter.data.EventRepositoryImpl
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.ui.viewmodel.CreateViewModel
import co.com.ceiba.patternadapter.ui.viewmodel.VMFactory
import kotlinx.android.synthetic.main.fragment_create_event.*
import java.text.SimpleDateFormat
import java.util.*


class CreateEventFragment : Fragment() {
    private val c = Calendar.getInstance()

    private val viewModel by activityViewModels<CreateViewModel> {
        VMFactory(
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
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpInfo()
        setupTextView()
        buttonSave.setOnClickListener {
            insertEvent()
        }
    }

    private fun setUpInfo(){
        textViewStartDate.text = SimpleDateFormat("dd-M-yyyy").format(System.currentTimeMillis())
        textViewStartHour.text = SimpleDateFormat("HH:mm").format(System.currentTimeMillis())
        textViewEndDate.visibility = View.GONE
        textViewEndHour.visibility = View.GONE
    }

    private fun insertEvent(){
        val eventName = editTextEventName.text.toString()
        val eventPlace = editTextPlaceName.text.toString()
        val startDate = "${textViewStartDate.text} ${textViewStartHour.text}"
        val endDate = "${textViewEndDate.text} ${textViewEndHour.text}"
        if (eventName.equals("") || " ".equals(endDate)){
            Toast.makeText(requireContext(),"El nombre del evento y la fecha son obligatorios",Toast.LENGTH_LONG).show()
        }else{
            viewModel.insertEvent(Event(null,startDate,endDate,eventPlace,eventName))
            Toast.makeText(requireContext(),"Evento creado con exito",Toast.LENGTH_LONG).show()
            editTextEventName.setText("")
            editTextPlaceName.setText("")
        }
    }

    private fun setupTextView(){

        textViewStartDate.setOnClickListener {
            showDatePickerDialog(textViewStartDate)
            textViewEndDate.visibility = View.VISIBLE
            textViewEndHour.visibility = View.VISIBLE
            textViewEndHour.text = SimpleDateFormat("HH:mm").format(System.currentTimeMillis())
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
        textViewStartDate.addTextChangedListener(object :TextWatcher{
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

        val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker,hour,minute ->
            c.set(Calendar.HOUR_OF_DAY,hour)
            c.set(Calendar.MINUTE,minute)
            textView.text = SimpleDateFormat("HH:mm").format(c.time)
        }
        TimePickerDialog(requireActivity(),timeSetListener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true).show()
    }
}