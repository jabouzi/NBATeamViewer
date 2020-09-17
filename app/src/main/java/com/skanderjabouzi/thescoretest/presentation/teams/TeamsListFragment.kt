package com.skanderjabouzi.thescoretest.presentation.teams

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.skanderjabouzi.thescoretest.R
import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.domain.listener.TeamClickListener
import com.skanderjabouzi.thescoretest.presentation.ViewModelFactory
import javax.inject.Inject

class TeamsListFragment : Fragment(), TeamClickListener {

    private lateinit var viewModel: TeamsListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TheScoreApp.INSTANCE.appComponent.getTeamsListFragmentComponent().inject(this)
        viewModel = ViewModelProvider(this).get(TeamsListViewModel::class.java)
        return inflater.inflate(R.layout.teams_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onItemClick(team: Team) {
        val teamBumble = Bundle().apply {
            putSerializable("team", team)
        }
        view?.findNavController()?.navigate(
            R.id.action_teamsListFragment_to_teamPlayersFragment, teamBumble)
    }


}
