package com.murtuza.popularmovielist.data.model

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */
import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    val id: Int,
    @field:SerializedName("logo_path")
    val logoPath: Any,
    val name: String,
    @field:SerializedName("origin_country")
    val originCountry: String
)