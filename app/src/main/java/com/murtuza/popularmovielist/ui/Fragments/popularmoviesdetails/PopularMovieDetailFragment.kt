package com.murtuza.popularmovielist.ui.Fragments.popularmoviesdetails

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.murtuza.popularmovielist.ConstValue
import com.murtuza.popularmovielist.ConstValue.MOVIE_ID
import com.murtuza.popularmovielist.R
import com.murtuza.popularmovielist.SharedPreferences.ModelPreferencesManager
import com.murtuza.popularmovielist.extentions.formatTextToCurrency
import com.murtuza.popularmovielist.extentions.removeLast
import com.murtuza.popularmovielist.extentions.thisOrNA
import com.murtuza.popularmovielist.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.backdrop_path
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.budgetValue
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.genresValue
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.languageValue
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.movie_name
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.overview_detail
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.poster_path
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.productionCompaniesValue
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.productionCountriesValue
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.releaseDateDetails
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.status
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.statusDetails
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.tagLineDetails
import kotlinx.android.synthetic.main.popular_movies_detail_fragment.vote_average_detail
/**
 * Created by murtuza khalid saleem 03060824762 on 10/17/2023.
 */

@AndroidEntryPoint
class PopularMovieDetailFragment : Fragment(R.layout.popular_movies_detail_fragment) {

    @Volatile private var i = 0
    @Volatile private var genre = ""
    @Volatile private var productionCompanies = ""
    @Volatile private var productionCountries = ""

    private val viewModel: PopularMovieDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { ModelPreferencesManager.with(it.application) }

        val movieId = arguments?.getLong(MOVIE_ID) ?: 0

        val rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_inf)
        poster_path.startAnimation(rotate)

        if (movieId == 0L)
            errorOperation()

        viewModel.getDetail(movieId).observe(viewLifecycleOwner) { it ->
            poster_path.clearAnimation()

            ModelPreferencesManager.put(it, "MoviesDetails")

            Glide.with(this).load(ConstValue.IMAGE_URL + it.backdropPath)
                .into(backdrop_path)

            Glide.with(this).load(ConstValue.IMAGE_URL + it.posterPath)
                .into(poster_path)

            movie_name.text = it.originalTitle.thisOrNA()

            while (i< it.genres.size){
                genre=genre+it.genres[i].name+", "
                i++
            }

            genresValue.text = genre.removeLast().thisOrNA()

            languageValue.text = it.originalLanguage.thisOrNA()

            i=0
            while (i< it.productionCompanies.size){
                productionCompanies=productionCompanies+it.productionCompanies[i].name+", "
                i++
            }

            productionCompaniesValue.text = productionCompanies.removeLast().thisOrNA()

            i=0
            while (i< it.productionCountries.size){
                productionCountries=productionCountries+it.productionCountries[i].name+", "
                i++
            }

            productionCountriesValue.text = productionCountries.removeLast()

            tagLineDetails.text = it.tagLine.thisOrNA()

            overview_detail.text = it.overview.thisOrNA()

            status.text = it.status.thisOrNA()

            vote_average_detail.text = it.voteAverage.toString().thisOrNA()

            releaseDateDetails.text = it.releaseDate.thisOrNA()

            statusDetails.text = it.status

            budgetValue.text = it.budget.formatTextToCurrency().thisOrNA()

        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it)
                errorOperation()

        }

    }

    // show toast and back to previous fragment
    private fun errorOperation() {
        requireContext().showToast(getString(R.string.error_occurred))
        activity?.onBackPressed()
    }
}