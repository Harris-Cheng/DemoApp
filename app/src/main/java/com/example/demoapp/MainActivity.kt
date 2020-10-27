package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.demoapp.MainViewModel.Companion.BOOKMARK_LIST
import com.example.demoapp.base.BaseAdapter.Companion.ITEM_CLICK_BASE
import com.example.demoapp.models.responses.AlbumsResponse
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var adapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
        setupViewModel()
    }

    private fun setupUi() {
        adapter = AlbumAdapter(Hawk.get(BOOKMARK_LIST, mutableSetOf()))

        adapter.getClickObservable().subscribe {
            when (it.id) {
                ITEM_CLICK_BASE -> {
                    mainViewModel.onItemClick(adapter.getItem(it.position))
                }
            }
        }

        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel.requestAlbums()

        mainViewModel.albumResponseLiveData.observe(this, {
            it?.results?.run {
                adapter.submitList(this)
            }
        })

        mainViewModel.bookMarkListUpdateLiveData.observe(this, {
            if (::adapter.isInitialized) {
                adapter.bookmarkList = Hawk.get(BOOKMARK_LIST, mutableSetOf())
            }
        })
    }
}