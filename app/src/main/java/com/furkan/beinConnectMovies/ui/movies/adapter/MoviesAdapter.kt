package com.furkan.beinConnectMovies.ui.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.furkan.beinConnectMovies.R
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.ui.detail.view.DetailActivity


class MoviesAdapter(private var items: List<MoviesResult>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.adapter_movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setSpots(data: List<MoviesResult>) {
        this.items = data
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = items[position]

        Glide.with(holder.itemView.context)
            .load("http://image.tmdb.org/t/p/w185" + spot.posterPath)
            .transform(CenterCrop(), RoundedCorners(35))
            .into(holder.photo)



        holder.text.text = spot.title
        holder.photo.setOnClickListener {
            DetailActivity.moviesInfo = spot
            val intent = Intent(it.context, DetailActivity::class.java)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var photo: ImageView = view.findViewById(R.id.movieIcon)
        var text: TextView = view.findViewById(R.id.movieName)
    }
}