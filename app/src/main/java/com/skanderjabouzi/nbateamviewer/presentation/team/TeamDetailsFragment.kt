package com.skanderjabouzi.nbateamviewer.presentation.team

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Team
import kotlinx.android.synthetic.main.players_titles.view.*
import kotlinx.android.synthetic.main.team_players_fragment.*
import kotlinx.android.synthetic.main.teams_item.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*


class TeamDetailsFragment : Fragment() {

    private val viewModel: TeamDetailsViewModel by viewModels()
    //var viewModelFactory: ViewModelFactory = ViewModelFactory(this)
    var teamId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this, viewModelFactory)[TeamPlayersViewModel::class.java]
        return inflater.inflate(R.layout.teams_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        observePlayers()
        observeErrors()

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.players_toolbar_title.text = getString(R.string.players_list)
        toolbar.team_toolbar_title.isVisible = false
        teams_titles.isVisible = false

        showLoading()
        setRetryButton()
        getTeamBundle()
    }

    private fun getTeamBundle() {
        val team = arguments?.getSerializable("team") as Team
        team?.let {
            viewModel.getTeamDetails(it.id)
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

    private fun showMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            playersRetryButton.isVisible = true
            playersErroMessage.isVisible = true
            playersErroMessage.text = errorMessage
        }
    }

    private fun getPlayers(teamId: Int) {
        viewModel.getTeamDetails(teamId)
    }

    private fun setMenu(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.players_menu)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }


    private fun observePlayers() {
        viewModel.teamDetails.observe(viewLifecycleOwner, Observer { players ->
            hideLoading()
            //adapter.setPlayers(players)
        })
    }

    private fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            hideLoading()
            showMessage(errorMessage)
        })
    }
}