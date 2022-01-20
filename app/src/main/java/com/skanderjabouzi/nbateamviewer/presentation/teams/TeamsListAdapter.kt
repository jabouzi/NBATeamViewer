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

package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.databinding.PlayersItemBinding
import com.skanderjabouzi.nbateamviewer.databinding.TeamsItemBinding
import com.skanderjabouzi.nbateamviewer.presentation.listener.TeamClickListener

class TeamsListAdapter (private val itemClickListener: TeamClickListener,
                        private val context: Context) :
        RecyclerView.Adapter<TeamsListAdapter.TeamHolder>() {

  private lateinit var binding: TeamsItemBinding
  private val teams = mutableListOf<Team>()
  val imageLoader = context.imageLoader

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
    binding = TeamsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    val holder = TeamHolder(binding, parent.context)
    Coil.setImageLoader(imageLoader)
    return holder
  }

  override fun getItemCount(): Int = teams.size ?: 0

  override fun onBindViewHolder(holder: TeamHolder, position: Int) {
      holder.bind(teams[position], position, itemClickListener)
  }

  fun setTeams(teamsList: List<Team>) {
    this.teams.clear()
    this.teams.addAll(teamsList)
    notifyDataSetChanged()
  }


  inner class TeamHolder(val binding: TeamsItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
    fun bind(team: Team, position: Int, itemClickListener: TeamClickListener) = with(binding) {
      teamNameValue.text = team.name?.trim()
      teamWinsValue.text = team.wins.toString().trim()
      teamLossesValue.text = team.losses.toString().trim()
      teamImage.load(team.imgURL) {
        crossfade(true)
        placeholder(R.drawable.placeholder)
        error(R.drawable.placeholder)
        transformations(CircleCropTransformation())
      }
      binding.root.setOnClickListener {
        teams.get(position).let { itemClickListener.onItemClick(it) }
      }
    }
  }
}