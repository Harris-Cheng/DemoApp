package com.example.demoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.models.responses.AlbumsResponse
import com.example.demoapp.network.ApiService
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val BOOKMARK_LIST = "BOOKMARK_LIST"
    }

    val albumResponseLiveData: MutableLiveData<AlbumsResponse?> by lazy {
        MutableLiveData()
    }

    val bookMarkListUpdateLiveData: MutableLiveData<Boolean> by lazy {
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

    fun onItemClick(item: AlbumsResponse.Result) = viewModelScope.launch(Dispatchers.IO) {
        Hawk.put(BOOKMARK_LIST, Hawk.get(BOOKMARK_LIST, mutableSetOf<Long>()).let { bookmarkList ->
            if (!bookmarkList.add(item.collectionId)) {
                bookmarkList.remove(item.collectionId)
            }
            bookmarkList
        })
        bookMarkListUpdateLiveData.postValue(true)
    }

}