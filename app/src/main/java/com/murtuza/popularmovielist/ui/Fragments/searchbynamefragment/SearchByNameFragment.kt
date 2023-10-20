package com.murtuza.popularmovielist.ui.Fragments.searchbynamefragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.murtuza.popularmovielist.ConstValue
import com.murtuza.popularmovielist.R
import com.murtuza.popularmovielist.ui.Listners.ClickListener
import com.murtuza.popularmovielist.ui.adaptor.PopularMoviesAdapter
import com.murtuza.popularmovielist.util.SpeedyLinearLayoutManager
import com.murtuza.popularmovielist.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_by_name_fragment.edt_search
import kotlinx.android.synthetic.main.search_by_name_fragment.rec_search
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchByNameFragment : Fragment(R.layout.search_by_name_fragment), ClickListener {

    var popularMoviesAdapter: PopularMoviesAdapter = PopularMoviesAdapter(this)

    private val viewModel: SearchByNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        edt_search.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                clearData()
                gettingData(edt_search.text.toString())

                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun clearData() {
        lifecycleScope.launch {
            popularMoviesAdapter.submitData(PagingData.empty())
            viewModel.clearData()
            activity?.hideKeyboard()
        }
    }

    private fun gettingData(searchQuery: String) {
        lifecycleScope.launch {
            viewModel.searchMovie(searchQuery).collectLatest { pagingData ->
                popularMoviesAdapter.submitData(pagingData)
            }
        }

    }

    private fun initRecyclerView() {
        rec_search.apply {
            adapter = popularMoviesAdapter
            val mLayoutManager = SpeedyLinearLayoutManager(requireContext())
            layoutManager = mLayoutManager
            setItemViewCacheSize(20)
            isNestedScrollingEnabled = true
        }
    }

    override fun clicked(value: Long?) {

        val movieId = Bundle()
        movieId.putLong(ConstValue.MOVIE_ID, value ?: 0)
        findNavController().navigate(R.id.action_searchFragment_to_movieDetailFragment, movieId)
    }


}