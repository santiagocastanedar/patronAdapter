package co.com.ceiba.patternadapter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.domain.EventRepository
import kotlinx.coroutines.launch

class InformationViewModel(private val repo: EventRepository): ViewModel() {

    fun deleteEvent(event: Event){
        viewModelScope.launch {
            repo.deleteEvent(event)
        }
    }
    fun updateEvent(event: Event){
        viewModelScope.launch {
            repo.editEvent(event)
        }
    }
}