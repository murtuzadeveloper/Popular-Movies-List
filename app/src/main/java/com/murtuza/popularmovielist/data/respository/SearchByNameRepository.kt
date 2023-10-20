package com.murtuza.popularmovielist.data.respository

import com.murtuza.popularmovielist.data.remote.ApiService
import javax.inject.Inject

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

class SearchByNameRepository @Inject constructor(private val api: ApiService) {

    fun getMovieSearch(searchQuery: String) = SearchByNameDatSource(api, searchQuery)
}