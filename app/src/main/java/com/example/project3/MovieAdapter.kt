package com.example.project3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mvPoster = itemView.findViewById<ImageView>(R.id.PosterImg)
        private val mvTitle = itemView.findViewById<TextView>(R.id.MoveTitle)
        private val mvSummary = itemView.findViewById<TextView>(R.id.MovieDescription)

        fun bind(movie: Movie) {
            mvTitle.text = movie.title
            mvSummary.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).into(mvPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size
}
