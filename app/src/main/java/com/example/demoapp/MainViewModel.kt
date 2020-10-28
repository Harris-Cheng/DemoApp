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

    private val albumResponseLiveData: MutableLiveData<AlbumsResponse?> = MutableLiveData()

    private val bookMarkListLiveData: MutableLiveData<MutableSet<Long>> = MutableLiveData(Hawk.get(BOOKMARK_LIST, mutableSetOf()))

    private val displayBookmarkOnly: MutableLiveData<Boolean> = MutableLiveData(false)

    val albumModelLiveData: LiveData<List<AlbumModel>> by lazy {
        albumResponseLiveData.combine(bookMarkListLiveData, displayBookmarkOnly) { albumList, bookmarks, bookmarkOnly ->
            val list: List<AlbumsResponse.Result>? = if (bookmarkOnly == true) {
                albumList?.results?.filter {
                    bookmarks?.contains(it.collectionId) ?: false
                }
            } else {
                albumList?.results
            }
            list?.let { album ->
                List(album.size) {
                    AlbumModel(
                        bookmarks?.contains(album[it].collectionId) ?: false,
                        album[it]
                    )
                }
            } ?: listOf()
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

    fun onBookMarkListClicked(): Boolean {
        displayBookmarkOnly.value = displayBookmarkOnly.value?.not()
        return displayBookmarkOnly.value == true
    }

}