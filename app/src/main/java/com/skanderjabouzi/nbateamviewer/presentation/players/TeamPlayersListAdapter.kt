package com.skanderjabouzi.nbateamviewer.presentation.players

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.databinding.PlayersItemBinding
import javax.inject.Inject

class TeamPlayersListAdapter @Inject constructor()
  : RecyclerView.Adapter<TeamPlayersListAdapter.PlayerViewHolder>() {

  private var players = mutableListOf<Player>()
  private lateinit var binding: PlayersItemBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
    binding = PlayersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    var holder = PlayerViewHolder(binding, parent.context)
    return holder
  }

  override fun getItemViewType(position: Int): Int = R.layout.players_item


  override fun getItemCount(): Int {
    return players.size
  }

  override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
    holder.bind(players[position])
  }

  fun setPlayers(playerList: List<Player>) {
    this.players.clear()
    this.players.addAll(playerList)
    notifyDataSetChanged()
  }

  inner class PlayerViewHolder(binding: PlayersItemBinding, context: Context) : RecyclerView.ViewHolder(binding.root) {
    fun bind(player: Player) {
      with(binding) {
        playerNameValue.text = player.full_name?.trim()
        playerPositionValue.text = player.position?.trim()
        playerNumberValue.text = player.number?.trim()
      }

    }
  }
}