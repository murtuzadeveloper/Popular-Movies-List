package com.murtuza.popularmovielist.ui.Fragments.searchbynamefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.murtuza.popularmovielist.data.model.MovieModel
import com.murtuza.popularmovielist.data.respository.SearchByNameDatSource
import com.murtuza.popularmovielist.data.respository.SearchByNameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@HiltViewModel
class SearchByNameViewModel @Inject constructor(private val searchRepository: SearchByNameRepository) :
    ViewModel() {

    private var pagingSource: SearchByNameDatSource? = null

    fun searchMovie(searchQuery: String): Flow<PagingData<MovieModel>> {
        return Pager(
            // Configure how data is loaded by passing additional properties to


            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
        ) {
            searchRepository.getMovieSearch(searchQuery).also {
                pagingSource = it
            }
        }.flow
            .cachedIn(viewModelScope)
    }


    fun clearData() =
        pagingSource?.invalidate()
}