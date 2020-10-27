package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel.requestAlbums()

        mainViewModel.albumResponseLiveData.observe(this, {
            it?.results?.run {
                adapter.submitList(this)
            }
        })
    }
}