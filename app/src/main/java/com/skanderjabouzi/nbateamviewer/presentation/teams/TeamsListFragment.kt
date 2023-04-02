package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.databinding.TeamsListFragmentBinding
import com.skanderjabouzi.nbateamviewer.presentation.listener.TeamClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TeamsListFragment : Fragment(), TeamClickListener {

    lateinit var viewModel: TeamsListViewModel
    lateinit var adapter: TeamsListAdapter
    private var _binding: TeamsListFragmentBinding? = null
    private val binding get() = _binding!!
    //lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this, viewModelFactory).get(TeamsListViewModel::class.java)
        viewModel = ViewModelProvider(this).get(TeamsListViewModel::class.java)
        _binding = TeamsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        observeTeams()
        observeErrors()

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        with(binding.toolbarView) {
            teamToolbarTitle.text = getString(R.string.teams_list)
            playersToolbarTitle.isVisible = false
            playersTitles.root.isVisible = false
        }

        setMenu(view)

        activity?.let {
            adapter =  TeamsListAdapter(this, it)
            binding.teamsRecyclerView.adapter = adapter
        }

        showLoading()
        setRetryButton()
        getTeams()
    }

    private fun showLoading() {
        binding.teamsRecyclerView.isEnabled = false
        binding.teamsProgressBar.isVisible = true
    }

    private fun setRetryButton() {
        with(binding) {
            teamsRetryButton.setOnClickListener {
                teamsRetryButton.isVisible = false
                teamsErroMessage.isVisible = false
                showLoading()
                getTeams()
            }
        }

    }

    private fun hideLoading() {
        with(binding) {
            teamsRecyclerView.isEnabled = true
            teamsProgressBar.isVisible = false
        }
    }

    private fun showMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            with(binding) {
                teamsRetryButton.isVisible = true
                teamsErroMessage.isVisible = true
                teamsErroMessage.text = errorMessage
            }
        }
    }

    private fun getTeams() {
        viewModel.getTeams()
    }

    private fun observeTeams() {
        lifecycleScope.launchWhenStarted {
            viewModel.teams.collect { teams ->
                hideLoading()
                adapter.setTeams(teams)
            }
        }
    }

    private fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            hideLoading()
            showMessage(errorMessage)
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
            R.id.action_teamsListFragment_to_teamDetailsFragment, teamBumble)
    }
}
