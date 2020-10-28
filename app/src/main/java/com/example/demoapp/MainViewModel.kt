package com.example.demoapp

import androidx.lifecycle.*
import com.example.demoapp.models.responses.AlbumsResponse
import com.example.demoapp.models.ui.AlbumModel
import com.example.demoapp.network.ApiService
import com.example.demoapp.utils.combine
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        const val BOOKMARK_LIST = "BOOKMARK_LIST"
    }

    private val albumResponseLiveData: MutableLiveData<AlbumsResponse?> by lazy {
        MutableLiveData()
    }

    private val bookMarkListLiveData: MutableLiveData<MutableSet<Long>> by lazy {
        MutableLiveData(Hawk.get(BOOKMARK_LIST, mutableSetOf()))
    }

    val albumModelLiveData: LiveData<List<AlbumModel>?> by lazy {
        albumResponseLiveData.combine(bookMarkListLiveData) { albumList, bookmarks ->
            albumList?.results?.let { album ->
                List(album.size) {
                    AlbumModel(
                        bookmarks?.contains(album[it].collectionId) ?: false,
                        album[it]
                    )
                }
            }
        }
    }

    fun requestAlbums() = viewModelScope.launch(Dispatchers.IO) {
        ApiService.getAlbum().apply {
            albumResponseLiveData.postValue(this.body())
        }
    }

    fun onItemClick(item: AlbumModel) = viewModelScope.launch(Dispatchers.IO) {
        val bookmarkList = Hawk.get(BOOKMARK_LIST, mutableSetOf<Long>()).let { bookmarkList ->
            if (item.isBookmarked) {
                bookmarkList.remove(item.data.collectionId)
            } else {
                bookmarkList.add(item.data.collectionId)
            }
            bookmarkList
        }
        Hawk.put(BOOKMARK_LIST, bookmarkList)
        bookMarkListLiveData.postValue(bookmarkList)
    }

}