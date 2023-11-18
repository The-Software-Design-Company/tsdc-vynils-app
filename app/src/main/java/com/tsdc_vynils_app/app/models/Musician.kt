package com.tsdc_vynils_app.app.models

import java.util.Date

data class Musician(
    val id: Int,
    val name: String,
    val description: String,
    val birthDate: Date,
    val image: String,
    val albums: List<Album>,
    val performerPrizes: List<Performer>,
    val imagenResId: Int
)