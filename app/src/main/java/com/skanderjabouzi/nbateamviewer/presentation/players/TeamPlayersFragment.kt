package com.skanderjabouzi.nbateamviewer.presentation.players

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
import coil.Coil
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.databinding.TeamPlayersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamPlayersFragment : Fragment() {

    private val viewModel: TeamPlayersViewModel by viewModels()
    lateinit var adapter: TeamPlayersListAdapter
    private var _binding: TeamPlayersFragmentBinding? = null
    private val binding get() = _binding!!
    //var viewModelFactory: ViewModelFactory = ViewModelFactory(this)
    var teamId = 0
    val imageLoader = activity?.imageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this, viewModelFactory)[TeamPlayersViewModel::class.java]
        _binding = TeamPlayersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        observePlayers()
        observeErrors()
        imageLoader?.let { Coil.setImageLoader(it) }

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        with(binding.toolbarView) {
            playersToolbarTitle.text = getString(R.string.players_list)
            teamToolbarTitle.isVisible = false
            teamsTitles.root.isVisible = false
        }

        setMenu(view)

        adapter = TeamPlayersListAdapter()
        binding.playersRecyclerView.adapter = adapter
        showLoading()
        setRetryButton()
        getTeamBundle()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getTeamBundle() {
        val team = arguments?.getSerializable("team") as Team
        team.let {
            it.id?.let { it1 -> viewModel.getPlayers(it1)
                teamId = it1
            }
            with(binding.toolbarView.playersTitles.playerTeamValues) {
                teamNameValue.text = it.name
                teamWinsValue.text = it.wins.toString()
                teamLossesValue.text = it.losses.toString()
                teamImage.load(team.imgURL) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun setRetryButton() {
        with(binding) {
            playersRetryButton.setOnClickListener {
                it.isVisible = false
                playersErrorMessage.isVisible = false
                showLoading()
                getPlayers(teamId)
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            playersRecyclerView.isEnabled = false
            playersProgressBar.isVisible = true
        }
    }

    private fun hideLoading() {
        with(binding) {
            playersRecyclerView.isEnabled = true
            playersProgressBar.isVisible = false
        }
    }

    private fun showMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            with(binding) {
                playersRetryButton.isVisible = true
                playersErrorMessage.isVisible = true
                playersErrorMessage.text = errorMessage
            }
        }
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
            adapter.setPlayers(players)
        })
    }

    private fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            hideLoading()
            showMessage(errorMessage)
        })
    }
}
