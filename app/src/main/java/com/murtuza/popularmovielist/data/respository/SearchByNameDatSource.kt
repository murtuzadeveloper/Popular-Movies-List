package com.murtuza.popularmovielist.data.respository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.murtuza.popularmovielist.ConstValue.API_KEY
import com.murtuza.popularmovielist.data.model.MovieModel
import com.murtuza.popularmovielist.data.remote.ApiService
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */
class SearchByNameDatSource constructor(
    private val api: ApiService,
    private val searchQuery: String
) :
    PagingSource<Int, MovieModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getMovieSearch(searchQuery, API_KEY, nextPageNumber)
            val responseData = mutableListOf<MovieModel>()
            val data = response.movieModels ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextPageNumber.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    //todo
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return null
    }
}
