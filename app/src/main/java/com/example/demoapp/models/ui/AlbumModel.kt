package com.example.demoapp.models.ui

import com.example.demoapp.models.responses.AlbumsResponse

data class AlbumModel(
    val isBookmarked: Boolean,
    val data: AlbumsResponse.Result
)