package com.example.demoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapp.base.BaseAdapter.Companion.ITEM_CLICK_BASE
import com.jakewharton.rxbinding4.view.clicks
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

    @SuppressLint("SetTextI18n")
    private fun setupUi() {
        fabBookMark.clicks().subscribe {
            if (mainViewModel.onBookMarkListClicked()) {
                fabBookMark.text = "Show All Albums"
            } else {
                fabBookMark.text = "Show Bookmarked Albums"
            }
        }

        adapter = AlbumAdapter()

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

        mainViewModel.albumModelLiveData.observe(this, {
            adapter.submitList(it)
        })
    }
}