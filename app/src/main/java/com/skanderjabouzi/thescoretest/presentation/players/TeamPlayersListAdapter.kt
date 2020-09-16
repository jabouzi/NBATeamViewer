package com.skanderjabouzi.thescoretest.presentation.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skanderjabouzi.thescoretest.R
import com.skanderjabouzi.thescoretest.data.model.Player
import kotlinx.android.synthetic.main.players_item.view.*

class TeamPlayersListAdapter
  : RecyclerView.Adapter<TeamPlayersListAdapter.PlayerViewHolder>() {

  lateinit var players: List<Player>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.players_item, parent, false)
    return PlayerViewHolder(view)
  }

  override fun getItemViewType(position: Int): Int = R.layout.players_item


  override fun getItemCount(): Int {
    return players.size
  }

  override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
    holder.bind(players[position])
  }

  inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(player: Player) {
      itemView.player_name_value.text = player.full_name
      itemView.player_position_value.text = player.position
      itemView.player_number_value.text = player.number.toString()
    }
  }
}