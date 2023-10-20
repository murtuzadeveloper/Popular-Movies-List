package com.murtuza.popularmovielist.ui.Fragments.popularmoviesdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.murtuza.popularmovielist.data.model.MovieDetail
import com.murtuza.popularmovielist.data.respository.PopularMovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@HiltViewModel
class PopularMovieDetailViewModel @Inject constructor(private val repository: PopularMovieDetailRepository):
    ViewModel() {

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error


    fun getDetail(movieId: Long): LiveData<MovieDetail> =
        repository.getMovieDetail(movieId)
            .catch { _error.postValue(true) }
            .asLiveData()
}