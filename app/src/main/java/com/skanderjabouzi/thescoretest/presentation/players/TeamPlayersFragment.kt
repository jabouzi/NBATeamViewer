package com.skanderjabouzi.thescoretest.presentation.players

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.skanderjabouzi.thescoretest.R


class TeamPlayersFragment : Fragment() {

    companion object {
        fun newInstance() = TeamPlayersFragment()
    }

    private lateinit var viewModel: TeamPlayersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamPlayersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
