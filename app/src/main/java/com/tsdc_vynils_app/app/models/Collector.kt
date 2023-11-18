package com.tsdc_vynils_app.app.models

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val imagenResId: Int,
    val comments: List<Comment>?,
    val favoritePerformers: List<Musician>?,
    val collectorAlbums: List<Album>?
)