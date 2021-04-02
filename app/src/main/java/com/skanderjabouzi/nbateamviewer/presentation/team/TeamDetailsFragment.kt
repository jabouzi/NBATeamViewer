package com.skanderjabouzi.nbateamviewer.presentation.team

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import coil.Coil
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Team
import kotlinx.android.synthetic.main.players_titles.view.*
import kotlinx.android.synthetic.main.team_players_fragment.*
import kotlinx.android.synthetic.main.team_players_fragment.playersRecyclerView
import kotlinx.android.synthetic.main.teams_details_fragment.*
import kotlinx.android.synthetic.main.teams_item.view.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*


class TeamDetailsFragment : Fragment() {

    private val viewModel: TeamDetailsViewModel by viewModels()
    //var viewModelFactory: ViewModelFactory = ViewModelFactory(this)
    var teamId = 0
    val imageLoader = activity?.imageLoader
    var team: Team? = null

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
        imageLoader?.let { Coil.setImageLoader(it) }

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.players_toolbar_title.isVisible = false
        teams_titles.isVisible = false

        showLoading()
        setRetryButton()
        setClickButton()
        getTeamBundle()
        observeTeamDetails()
        observeErrors()
    }

    private fun setClickButton() {
        teamPlayersButton.setOnClickListener {
            val teamBumble = Bundle().apply {
                putSerializable("team", team)
            }
            view?.findNavController()?.navigate(
                R.id.action_teamsDetailsFragment_to_teamPlayersFragment, teamBumble)
        }
    }

    private fun getTeamBundle() {
        team = arguments?.getSerializable("team") as Team
        team.let {
            if (team?.id != null) {
                viewModel.getTeamDetails(team?.id!!)
                teamId = team?.id!!
            }
            toolbar.team_toolbar_title.text = it?.name
            players_toolbar_title.isVisible = false
            teams_titles.isVisible = false
            players_titles.isVisible = false
        }
    }

    private fun setRetryButton() {
        teamRetryButton.setOnClickListener {
            playersRetryButton.isVisible = false
            playersErroMessage.isVisible = false
            showLoading()
            getPlayers(teamId)
        }
    }

    private fun showLoading() {
        teamDetailsLayout.isVisible = false
        teamProgressBar.isVisible = true
    }

    private fun hideLoading() {
        teamDetailsLayout.isVisible = false
        teamProgressBar.visibility = View.GONE
    }

    private fun showMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            teamRetryButton.isVisible = true
            teamErroMessage.isVisible = true
            teamErroMessage.text = errorMessage
            teamDetailsLayout.isEnabled = false
        }
    }

    private fun getPlayers(teamId: Int) {
        viewModel.getTeamDetails(teamId)
    }

    private fun observeTeamDetails() {
        viewModel.teamDetails.observe(viewLifecycleOwner, Observer { teamDetails ->
            hideLoading()
            teamDetailsLayout.isVisible = true
            teamName.text = teamDetails.name
            teamAbbrevaition.text = teamDetails.abbrev
            teamRegion.text = teamDetails.region
            teamPopularity.text = teamDetails.pop.toString()
            teamImage.load(teamDetails.imgURL) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
        })
    }

    private fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            hideLoading()
            showMessage(errorMessage)
        })
    }
}
