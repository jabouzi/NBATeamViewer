/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.skanderjabouzi.thescoretest.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.skanderjabouzi.thescoretest.listener.TeamClickListener
import java.util.*
import javax.inject.Inject

class TeamPlayersListAdapter @Inject constructor(private val itemClickListener: TeamClickListener)
  : RecyclerView.Adapter<TeamPlayersListAdapter.MovieHolder>() {

  val selectedMovies = HashSet<Movie>()
  private val movies = mutableListOf<Movie>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie_main, parent, false)
    return MovieHolder(view)
  }

  override fun getItemCount(): Int = movies.size ?: 0

  override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    holder.bind(movies[position], itemClickListener)
  }

  fun setMovies(movieList: List<Movie>) {
    this.movies.clear()
    this.movies.addAll(movieList)
      notifyDataSetChanged()
  }

  inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie, itemClickListener: MovieClickListener) = with(view) {
      movieTitleTextView.text = movie.title
      movieReleaseDateTextView.text = movie.releaseDate
      checkbox.isChecked = movie.watched
      if (movie.posterPath != null)
        Picasso.get().load(BuildConfig.TMDB_IMAGEURL + movie.posterPath).into(movieImageView)
      else {
        movieImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_local_movies_gray, null))
      }
      checkbox.setOnCheckedChangeListener { checkbox, isChecked ->
        if (!selectedMovies.contains(movie) && isChecked) {
          selectedMovies.add(movie)
        }else{
          selectedMovies.remove(movie)
        }
      }

      itemView.setOnClickListener {
        movie.id?.let { it -> itemClickListener.onItemClick(it) }
      }
    }

  }
}