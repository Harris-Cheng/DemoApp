package com.example.demoapp.network

import com.example.demoapp.models.responses.AlbumsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search")
    suspend fun getAlbum(@Query("term") term: String = "jack+johnson", @Query("entity") entity: String = "album"): Response<AlbumsResponse>

}