package com.example.demoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.models.responses.AlbumsResponse
import com.example.demoapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val albumResponseLiveData: MutableLiveData<AlbumsResponse?> by lazy {
        MutableLiveData()
    }

    fun requestAlbums() = viewModelScope.launch(Dispatchers.IO) {
        ApiService.getAlbum().apply {
            if (this.isSuccessful && this.body() != null) {
                albumResponseLiveData.postValue(this.body())
            } else {
                // there are some errors
            }
        }
    }

}