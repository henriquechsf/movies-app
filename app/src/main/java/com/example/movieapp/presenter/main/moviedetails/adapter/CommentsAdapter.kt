package com.example.movieapp.presenter.main.moviedetails.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemCommentReviewBinding
import com.example.movieapp.domain.model.MovieReview
import com.example.movieapp.util.formatCommentDate

class CommentsAdapter : ListAdapter<MovieReview, CommentsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieReview>() {
            override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.i("INFOTEST", "onCreateViewHolder: ${parent.context}")
        return MyViewHolder(
            ItemCommentReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movieReview = getItem(position)
        movieReview.authorDetails?.avatarPath?.let {avatarPath ->
            Glide.with(holder.binding.root.context)
                .load("https://image.tmdb.org/t/p/w500$avatarPath")
                .into(holder.binding.imgUser)
        } ?: run {
            Glide.with(holder.binding.root.context)
                .load(R.drawable.avatar)
                .into(holder.binding.imgUser)
        }

        with(holder.binding) {
            tvUsername.text = movieReview.authorDetails?.username
            tvComment.text = movieReview.content
            tvRating.text = movieReview.authorDetails?.rating.toString()
            tvDate.text = formatCommentDate(movieReview.createdAt)
        }
    }

    inner class MyViewHolder(val binding: ItemCommentReviewBinding) :
        RecyclerView.ViewHolder(binding.root)
}