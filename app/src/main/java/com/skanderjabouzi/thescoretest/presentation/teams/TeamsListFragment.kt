package com.skanderjabouzi.thescoretest.presentation.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.skanderjabouzi.thescoretest.R
import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.domain.listener.TeamClickListener
import com.skanderjabouzi.thescoretest.presentation.ViewModelFactory
import com.skanderjabouzi.thescoretest.presentation.action
import com.skanderjabouzi.thescoretest.presentation.snack
import kotlinx.android.synthetic.main.teams_list_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import javax.inject.Inject

class TeamsListFragment : Fragment(), TeamClickListener {

    private lateinit var viewModel: TeamsListViewModel
    @Inject
    lateinit var adapter: TeamsListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TheScoreApp.INSTANCE.appComponent.getTeamsListFragmentComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TeamsListViewModel::class.java)
        return inflater.inflate(R.layout.teams_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        observeTeams()

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.team_toolbar_title.text = getString(R.string.teams_list)
        toolbar.players_toolbar_title.isVisible = false
        players_titles.isVisible = false

        adapter = TeamsListAdapter(this)
        teamsRecyclerView.adapter = adapter
        showLoading()

        getTeams()
    }

    private fun showLoading() {
        teamsRecyclerView.isEnabled = false
        teamsProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        teamsRecyclerView.isEnabled = true
        teamsProgressBar.visibility = View.GONE
    }

    private fun showMessage() {
        teamsLayout.snack(getString(R.string.error_occured), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
            }
        }
    }

    private fun getTeams() {
        viewModel.getTeams()
    }

    private fun observeTeams() {
        viewModel.teams.observe(viewLifecycleOwner, Observer { teams ->
            hideLoading()
            if (teams == null) {
                showMessage()
            } else {
                adapter.setTeams(teams)
            }
        })
    }

    override fun onItemClick(team: Team) {
        val teamBumble = Bundle().apply {
            putSerializable("team", team)
        }
        view?.findNavController()?.navigate(
            R.id.action_teamsListFragment_to_teamPlayersFragment, teamBumble)
    }
}
