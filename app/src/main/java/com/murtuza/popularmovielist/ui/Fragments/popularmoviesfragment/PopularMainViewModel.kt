package com.murtuza.popularmovielist.ui.Fragments.popularmoviesfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.murtuza.popularmovielist.data.remote.ApiService
import com.murtuza.popularmovielist.data.respository.PopularMovieDatSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@HiltViewModel
class PopularMainViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private var pagingSource: PopularMovieDatSource? = null

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 10)
    ) {
        PopularMovieDatSource(api).also {
            pagingSource = it
        }
    }.flow
        .cachedIn(viewModelScope)


    fun clearData() =
        pagingSource?.invalidate()
}