package com.murtuza.popularmovielist.util

import kotlinx.coroutines.flow.Flow
import javax.net.ssl.SSLEngineResult.Status

/**
 * Created by murtuza khalid saleem 03060824762 on 10/18/2023.
 */
interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
        Available, Unavailable, Losing, Lost
    }

}