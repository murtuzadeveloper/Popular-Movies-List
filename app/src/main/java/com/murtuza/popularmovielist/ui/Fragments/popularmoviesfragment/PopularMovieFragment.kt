package com.murtuza.popularmovielist.ui.Fragments.popularmoviesfragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.Constant.K
import com.bumptech.glide.Glide
import com.murtuza.popularmovielist.ConstValue
import com.murtuza.popularmovielist.ConstValue.MOVIE_ID
import com.murtuza.popularmovielist.R
import com.murtuza.popularmovielist.SharedPreferences.ModelPreferencesManager
import com.murtuza.popularmovielist.data.model.MovieDetail
import com.murtuza.popularmovielist.extentions.formatTextToCurrency
import com.murtuza.popularmovielist.extentions.removeLast
import com.murtuza.popularmovielist.extentions.thisOrNA
import com.murtuza.popularmovielist.ui.Listners.ClickListener
import com.murtuza.popularmovielist.ui.adaptor.PopularMoviesAdapter
import com.murtuza.popularmovielist.util.ConnectivityObserver
import com.murtuza.popularmovielist.util.NetworkConnectivityObserver
import com.murtuza.popularmovielist.util.SpeedyLinearLayoutManager
import com.murtuza.popularmovielist.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.last_info_movie.backdrop_path_last
import kotlinx.android.synthetic.main.last_info_movie.budgetValue_last
import kotlinx.android.synthetic.main.last_info_movie.genresValue_last
import kotlinx.android.synthetic.main.last_info_movie.languageValue_last
import kotlinx.android.synthetic.main.last_info_movie.movie_name_last
import kotlinx.android.synthetic.main.last_info_movie.overview_detail_last
import kotlinx.android.synthetic.main.last_info_movie.poster_path_last
import kotlinx.android.synthetic.main.last_info_movie.productionCompaniesValue_last
import kotlinx.android.synthetic.main.last_info_movie.productionCountriesValue_last
import kotlinx.android.synthetic.main.last_info_movie.releaseDateDetails_last
import kotlinx.android.synthetic.main.last_info_movie.statusDetails_last
import kotlinx.android.synthetic.main.last_info_movie.status_last
import kotlinx.android.synthetic.main.last_info_movie.tagLineDetails_last
import kotlinx.android.synthetic.main.last_info_movie.vote_average_detail_last
import kotlinx.android.synthetic.main.popular_movie_fragment.recyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@AndroidEntryPoint
class PopularMovieFragment : Fragment(R.layout.popular_movie_fragment), ClickListener {

    @Volatile var i = 0
    @Volatile var genre = ""
    @Volatile var productionCompanies = ""
    @Volatile var productionCountries = ""
    private lateinit var connectivityObserver : ConnectivityObserver
    private var popularMoviesAdapter: PopularMoviesAdapter = PopularMoviesAdapter(this)
    private val popularMainViewModel: PopularMainViewModel by viewModels()
    private lateinit var rotate: Animation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObj()
        initRecyclerView()
        gettingData()
        activity?.let { ModelPreferencesManager.with(it.application) }
        val dialogBindings = layoutInflater.inflate(R.layout.last_info_movie,null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBindings)
        connectivityObserver = NetworkConnectivityObserver(requireContext())
        connectivityObserver.observe().onEach {it
            when(it.toString()){
                K.NetworkState.Available ->{
                    myDialog.dismiss()
                    gettingData()
                }
                K.NetworkState.Unavailable ->{
                    requireContext().showToast(K.NetworkState.Unavailable)
                }
                K.NetworkState.Losing ->{
                    requireContext().showToast(K.NetworkState.Losing)
                }
                K.NetworkState.Lost ->{
                    lastInfoDetails(myDialog)
                }
            }
        }.launchIn(lifecycleScope)

    }

    private fun lastInfoDetails(myDialog : Dialog){
        val movieDetail = ModelPreferencesManager.get<MovieDetail>("MoviesDetails")
        if (movieDetail != null) {
            Glide.with(this).load(ConstValue.IMAGE_URL + movieDetail.backdropPath)
                .into(myDialog.backdrop_path_last)
        }
        if (movieDetail != null) {
            Glide.with(this).load(ConstValue.IMAGE_URL + movieDetail.posterPath)
                .into(myDialog.poster_path_last)
        }

        while (i< movieDetail?.genres!!.size){
            this.genre=this.genre+movieDetail.genres[i].name+", "
            i++
        }

        i=0
        while (i< movieDetail.productionCompanies.size){
            productionCompanies=productionCompanies+movieDetail.productionCompanies[i].name+", "
            i++
        }

        i=0
        while (i< movieDetail.productionCountries.size){
            productionCountries=productionCountries+movieDetail.productionCountries[i].name+", "
            i++
        }
        myDialog.movie_name_last.text = movieDetail.originalTitle.thisOrNA()
        myDialog.productionCompaniesValue_last.text = productionCompanies.removeLast().thisOrNA()
        myDialog.productionCountriesValue_last.text = productionCountries.removeLast().thisOrNA()
        myDialog.genresValue_last.text = this.genre.removeLast()
        myDialog.languageValue_last.text = movieDetail.originalLanguage.thisOrNA()
        myDialog.tagLineDetails_last.text = movieDetail.tagLine.thisOrNA()
        myDialog.overview_detail_last.text = movieDetail.overview.thisOrNA()
        myDialog.status_last.text = movieDetail.status.thisOrNA()
        myDialog.vote_average_detail_last.text = movieDetail.voteAverage.toString().thisOrNA()
        myDialog.releaseDateDetails_last.text = movieDetail.releaseDate.thisOrNA()
        myDialog.statusDetails_last.text = movieDetail.status
        myDialog.budgetValue_last.text = movieDetail.budget.formatTextToCurrency().thisOrNA()
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    private fun initObj() {
        rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
    }

    private fun gettingData() {
        lifecycleScope.launch {
            popularMainViewModel.flow.collectLatest { pagingData ->
                popularMoviesAdapter.submitData(pagingData)
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = popularMoviesAdapter
            val mLayoutManager = SpeedyLinearLayoutManager(requireContext())
            layoutManager = mLayoutManager
            setItemViewCacheSize(20)
            isNestedScrollingEnabled = true
        }
    }

    override fun clicked(value: Long?) {
        val movieId = Bundle()
        movieId.putLong(MOVIE_ID, value ?: 0)
        findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment, movieId)
    }
}