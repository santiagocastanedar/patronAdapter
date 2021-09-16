package co.com.ceiba.patternadapter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.patternadapter.domain.EventRepository

class VMFactory(private val repo: EventRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EventRepository::class.java).newInstance(repo)
    }
}