package com.inbedroom.myapplication.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inbedroom.AppApplication
import com.inbedroom.myapplication.data.RepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repositoryClass: RepositoryClass
): ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = AppApplication.repositoryClass
                return MainViewModel(repository) as T
            }
        }
    }

    private val _uiState: MutableLiveData<UiState> = MutableLiveData()
    val uiState: LiveData<UiState> = _uiState

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repositoryClass.getMovies()
            if (result != null) {
                _uiState.postValue(UiState.Success(result))
            } else {
                _uiState.postValue(UiState.Failed)
            }
        }
    }
}