package com.example.movieapp.presenter.main.bottombar.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.GenreItemBinding
import com.example.movieapp.presenter.model.GenrePresentation

class GenreMovieAdapter(
    private val showAllListener: (Int, String) -> Unit
) :
    ListAdapter<GenrePresentation, GenreMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenrePresentation>() {
            override fun areItemsTheSame(
                oldItem: GenrePresentation,
                newItem: GenrePresentation
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GenrePresentation,
                newItem: GenrePresentation
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = getItem(position)

        holder.binding.tvGenre.text = genre.name

        val movieAdapter = MovieAdapter(
            context = holder.binding.root.context,
            layoutInflater = R.layout.movie_item
        )
        val layoutManager = LinearLayoutManager(
            holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false
        )

        holder.binding.tvShowAll.setOnClickListener {
            genre.id?.let { showAllListener(genre.id, genre.name ?: "") }
        }

        holder.binding.rvMovies.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        movieAdapter.submitList(genre.movies)
    }

    inner class MyViewHolder(val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}