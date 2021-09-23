package co.com.ceiba.patternadapter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.patternadapter.domain.Event
import co.com.ceiba.patternadapter.domain.EventRepository
import kotlinx.coroutines.launch

class CreateViewModel(private val repo: EventRepository): ViewModel() {
    fun insertEvent(event: Event){
        viewModelScope.launch {
            repo.createEvent(event)
        }
    }
}