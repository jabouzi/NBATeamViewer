package com.skanderjabouzi.thescoretest.presentation.players

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.skanderjabouzi.thescoretest.R
import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.presentation.ViewModelFactory
import javax.inject.Inject


class TeamPlayersFragment : Fragment() {

    private lateinit var viewModel: TeamPlayersViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TheScoreApp.INSTANCE.appComponent.getTeamPlayersFragmentComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[TeamPlayersViewModel::class.java]
        return inflater.inflate(R.layout.team_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
