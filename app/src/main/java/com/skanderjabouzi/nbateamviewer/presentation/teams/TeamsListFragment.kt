package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.domain.entity.Team
import com.skanderjabouzi.nbateamviewer.presentation.listener.TeamClickListener
import kotlinx.android.synthetic.main.teams_list_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class TeamsListFragment : Fragment(), TeamClickListener {

    val viewModel: TeamsListViewModel by viewModels()
    lateinit var adapter: TeamsListAdapter
    //lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this, viewModelFactory).get(TeamsListViewModel::class.java)
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

        setMenu(view)

        adapter = TeamsListAdapter(this)
        teamsRecyclerView.adapter = adapter
        showLoading()
        setRetryButton()
        getTeams()
    }

    private fun showLoading() {
        teamsRecyclerView.isEnabled = false
        teamsProgressBar.isVisible = true
    }

    private fun setRetryButton() {
        teamsRetryButton.setOnClickListener {
            teamsRetryButton.isVisible = false
            teamsErroMessage.isVisible = false
            showLoading()
            getTeams()
        }
    }

    private fun hideLoading() {
        teamsRecyclerView.isEnabled = true
        teamsProgressBar.isVisible = false
    }

    private fun showMessage() {
        teamsRetryButton.isVisible = true
        teamsErroMessage.isVisible = true
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

    private fun setMenu(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.teams_menu)
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_by_name -> viewModel.sortByName()
            R.id.action_sort_by_wins -> viewModel.sortByWins()
            R.id.action_sort_by_losses -> viewModel.sortByLosses()
        }
        return true
    }

    override fun onItemClick(team: Team) {
        val teamBumble = Bundle().apply {
            putSerializable("team", team)
        }
        view?.findNavController()?.navigate(
            R.id.action_teamsListFragment_to_teamPlayersFragment, teamBumble)
    }
}
