package com.murtuza.popularmovielist.data.model

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */
import com.google.gson.annotations.SerializedName

data class MovieResultModel(
    val page: Int,
    @field:SerializedName("results")
    val movieModels: List<MovieModel>,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int
)