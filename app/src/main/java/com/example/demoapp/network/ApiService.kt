package com.example.demoapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {

    private const val BASE_URL = "https://itunes.apple.com"

    private val apiService: Api by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(Api::class.java)
    }

    suspend fun getAlbum() = let {
        apiService.getAlbum()
    }

}