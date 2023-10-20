package com.murtuza.popularmovielist.ui.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murtuza.popularmovielist.ConstValue.IMAGE_URL
import com.murtuza.popularmovielist.R
import com.murtuza.popularmovielist.data.model.MovieModel
import com.murtuza.popularmovielist.ui.Listners.ClickListener
import kotlinx.android.synthetic.main.popular_movies_item.view.movie_name
import kotlinx.android.synthetic.main.popular_movies_item.view.movie_poster
import kotlinx.android.synthetic.main.popular_movies_item.view.popularity
import kotlinx.android.synthetic.main.popular_movies_item.view.releaseDate
import javax.inject.Inject



class PopularMoviesAdapter @Inject constructor(private val listener: ClickListener) :
    PagingDataAdapter<MovieModel, PopularMoviesAdapter.ViewHolder>(MovieDiff) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.clicked(getItem(position)?.id)
        }
        Glide.with(holder.itemView).load(IMAGE_URL + getItem(position)?.posterPath)
            .into(holder.itemView.movie_poster)
        holder.itemView.movie_name.text = getItem(position)?.title
        holder.itemView.popularity.text = holder.itemView.context.resources.getString(R.string.popularity)+getItem(position)?.popularity.toString()
        holder.itemView.releaseDate.text = holder.itemView.context.resources.getString(R.string.releaseDate)+getItem(position)?.releaseDate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.popular_movies_item, parent, false)
        )
    }

    object MovieDiff : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            newItem == oldItem
    }
}