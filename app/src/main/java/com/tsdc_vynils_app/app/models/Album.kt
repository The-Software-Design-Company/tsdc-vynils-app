package com.tsdc_vynils_app.app.models
import java.util.*
data class Album(
    var id: Int=0,
    var name: String ="",
    var cover: String ="",
    var releaseDate: String ="",
    var description: String ="",
    var genre: String ="",
    var recordLabel: String ="",
    var tracks: List<Track> = emptyList(),
    var performers: List<Performer> = emptyList(),
    var comments: List<Comment> = emptyList()
)