package com.example.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        mainViewModel.requestAlbums()

        mainViewModel.albumResponseLiveData.observe(this, {
            it?.run {
                Log.i("Response", "success $this")
            }
        })
    }
}