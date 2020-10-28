package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.demoapp.base.BaseAdapter.Companion.ITEM_CLICK_BASE
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
            it?.run {
                adapter.submitList(this)
            } ?: run {
                // error
                Toast.makeText(this@MainActivity, "Cannot fetch album list", Toast.LENGTH_SHORT).show()
            }
        })
    }
}