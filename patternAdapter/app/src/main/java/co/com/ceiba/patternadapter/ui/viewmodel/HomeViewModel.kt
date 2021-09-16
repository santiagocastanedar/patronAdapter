package co.com.ceiba.patternadapter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import co.com.ceiba.patternadapter.data.model.Event
import co.com.ceiba.patternadapter.domain.EventRepository
import co.com.ceiba.patternadapter.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repo:EventRepository):ViewModel() {
    fun insertEvent(event: Event){
        viewModelScope.launch {
            repo.createEvent(event)
        }
    }

    fun getEvents() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getEvents())
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}