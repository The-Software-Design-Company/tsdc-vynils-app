package com.tsdc_vynils_app.app.models

import java.util.Date

data class Musician(
    val id: Int,
    val name: String,
    val description: String,
    val birthDate: Date,
    val imagenResId: Int
)