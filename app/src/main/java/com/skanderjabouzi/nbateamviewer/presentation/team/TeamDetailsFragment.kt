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
import com.skanderjabouzi.nbateamviewer.databinding.TeamsDetailsFragmentBinding


class TeamDetailsFragment : Fragment() {

    private val viewModel: TeamDetailsViewModel by viewModels()
    private var _binding: TeamsDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    //var viewModelFactory: ViewModelFactory = ViewModelFactory(this)
    var teamId = 0
    val imageLoader = activity?.imageLoader
    var team: Team? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this, viewModelFactory)[TeamPlayersViewModel::class.java]
        _binding = TeamsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        imageLoader?.let { Coil.setImageLoader(it) }

        view.findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController, appBarConfiguration)

        with (binding.toolbarView) {
            playersToolbarTitle.isVisible = false
            teamsTitles.root.isVisible = false
        }

        showLoading()
        setRetryButton()
        setClickButton()
        getTeamBundle()
        observeTeamDetails()
        observeErrors()
    }

    private fun setClickButton() {
        binding.teamPlayersButton.setOnClickListener {
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
            with(binding.toolbarView) {
                teamToolbarTitle.text = it?.name
                playersToolbarTitle.isVisible = false
                teamToolbarTitle.isVisible = false
                playersTitles.root.isVisible = false
            }
        }
    }

    private fun setRetryButton() {
        with(binding) {
            teamRetryButton.setOnClickListener {
                teamRetryButton.isVisible = false
                teamErroMessage.isVisible = false
                showLoading()
                getPlayers(teamId)
            }
        }

    }

    private fun showLoading() {
        with(binding) {
            teamDetailsLayout.isVisible = false
            teamProgressBar.isVisible = true
        }
    }

    private fun hideLoading() {
        with(binding) {
            teamDetailsLayout.isVisible = false
            teamProgressBar.visibility = View.GONE
        }
    }

    private fun showMessage(errorMessage: String) {
        if (errorMessage.isNotEmpty()) {
            with(binding) {
                teamRetryButton.isVisible = true
                teamErroMessage.isVisible = true
                teamErroMessage.text = errorMessage
                teamDetailsLayout.isEnabled = false
            }
        }
    }

    private fun getPlayers(teamId: Int) {
        viewModel.getTeamDetails(teamId)
    }

    private fun observeTeamDetails() {
        viewModel.teamDetails.observe(viewLifecycleOwner, Observer { teamDetails ->
            hideLoading()
            with(binding) {
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
