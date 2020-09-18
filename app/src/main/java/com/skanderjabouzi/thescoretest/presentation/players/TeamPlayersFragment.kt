package com.skanderjabouzi.thescoretest.presentation.players

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.skanderjabouzi.thescoretest.R
import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.presentation.ViewModelFactory
import kotlinx.android.synthetic.main.players_titles.view.*
import kotlinx.android.synthetic.main.team_players_fragment.*
import kotlinx.android.synthetic.main.teams_item.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import javax.inject.Inject


class TeamPlayersFragment : Fragment() {

    private lateinit var viewModel: TeamPlayersViewModel
    @Inject
    lateinit var adapter: TeamPlayersListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TheScoreApp.INSTANCE.appComponent.getTeamPlayersFragmentComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[TeamPlayersViewModel::class.java]
        return inflater.inflate(R.layout.team_players_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        observePlayers()

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.players_toolbar_title.text = getString(R.string.players_list)
        toolbar.team_toolbar_title.isVisible = false
        teams_titles.isVisible = false

        setMenu(view)

        adapter = TeamPlayersListAdapter()
        playersRecyclerView.adapter = adapter
        showLoading()
        setRetryButton()
        getTeamBundle()
    }

    private fun getTeamBundle() {
        val team = arguments?.getSerializable("team") as Team
        team?.let {
            viewModel.getPlayers(it.id)
            teamId = it.id
            players_titles.player_team_values.team_name_value.text = it.name
            players_titles.player_team_values.team_wins_value.text = it.wins.toString()
            players_titles.player_team_values.team_losses_value.text = it.losses.toString()
        }
    }

    private fun setRetryButton() {
        playersRetryButton.setOnClickListener {
            playersRetryButton.isVisible = false
            playersErroMessage.isVisible = false
            showLoading()
            getPlayers(teamId)
        }
    }

    private fun showLoading() {
        playersRecyclerView.isEnabled = false
        playersProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        playersRecyclerView.isEnabled = true
        playersProgressBar.visibility = View.GONE
    }

    private fun showMessage() {
        playersRetryButton.isVisible = true
        playersErroMessage.isVisible = true
    }

    private fun getPlayers(teamId: Int) {
        viewModel.getPlayers(teamId)
    }

    private fun setMenu(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.players_menu)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_by_name -> viewModel.sortByName(teamId)
            R.id.action_sort_by_position -> viewModel.sortByPosition(teamId)
            R.id.action_sort_by_number -> viewModel.sortByNumber(teamId)
        }
        return true
    }

    private fun observePlayers() {
        viewModel.players.observe(viewLifecycleOwner, Observer { players ->
            hideLoading()
            if (players == null) {
                showMessage()
            } else {
                adapter.setPlayers(players)
            }
        })
    }
}
