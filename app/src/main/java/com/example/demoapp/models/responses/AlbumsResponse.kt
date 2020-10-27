package com.example.demoapp.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumsResponse (
    val resultCount: Long = 0,
    val results: List<Result>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Result (
        val wrapperType: String? = null,
        val collectionType: String? = null,
        val artistId: Long = 0,
        val collectionId: Long = 0,
        val amgArtistId: Long = 0,
        val artistName: String? = null,
        val collectionName: String? = null,
        val collectionCensoredName: String? = null,
        val artistViewURL: String? = null,
        val collectionViewURL: String? = null,
        val artworkUrl60: String? = null,
        val artworkUrl100: String? = null,
        val collectionPrice: Double = 0.0,
        val collectionExplicitness: String? = null,
        val trackCount: Long = 0,
        val copyright: String? = null,
        val country: String? = null,
        val currency: String? = null,
        val releaseDate: String? = null,
        val primaryGenreName: String? = null,
        val contentAdvisoryRating: String? = null
    )
}