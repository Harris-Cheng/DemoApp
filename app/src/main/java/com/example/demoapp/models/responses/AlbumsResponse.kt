package com.example.demoapp.models.responses

data class AlbumsResponse (
    val resultCount: Long = 0,
    val results: List<Result>? = null
) {
    data class Result (
        val wrapperType: String? = null,
        val collectionType: String? = null,
        val artistID: Long? = null,
        val collectionID: Long? = null,
        val amgArtistID: Long? = null,
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