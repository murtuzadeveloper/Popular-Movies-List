package com.murtuza.popularmovielist.ui.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.murtuza.popularmovielist.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.my_nav_host_fragment

/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  //  private lateinit var connectivityObserver : ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContentView(R.layout.activity_main)

      /*  connectivityObserver.observe().onEach {it

            when(it.toString()){

                K.NetworkState.Available ->{
                    showToast(K.NetworkState.Available)
                }
                K.NetworkState.Unavailable ->{
                    showToast(K.NetworkState.Unavailable)
                }
                K.NetworkState.Losing ->{
                    showToast(K.NetworkState.Losing)
                }
                K.NetworkState.Lost ->{
                    showToast(K.NetworkState.Lost)
                }
            }

        }.launchIn(lifecycleScope)*/

        bottomNavigationView.setupWithNavController(my_nav_host_fragment.findNavController())
    }

}