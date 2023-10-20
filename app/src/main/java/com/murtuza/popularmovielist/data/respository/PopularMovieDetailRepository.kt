package com.murtuza.popularmovielist.data.respository

import com.murtuza.popularmovielist.ConstValue.API_KEY
import com.murtuza.popularmovielist.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */
class PopularMovieDetailRepository @Inject constructor(private val api: ApiService){

    fun getMovieDetail(movieId:Long)= flow{

        emit(api.getMovie(movieId,API_KEY))

    }.flowOn(Dispatchers.IO)


}