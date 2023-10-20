package com.murtuza.popularmovielist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@Entity
data class MovieModel(
    val adult: Boolean,
    @field:SerializedName("backdrop_path")
    val backdropPath: String,
    @field:SerializedName("genre_ids")
    val genreIds: List<Int>,
    @PrimaryKey
    val id: Long,
    @field:SerializedName("original_language")
    val originalLanguage: String,
    @field:SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @field:SerializedName("vote_average")
    val voteAverage: Double,
    @field:SerializedName("vote_count")
    val voteCount: Int
)