package com.skanderjabouzi.thescoretest.presentation.teams

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skanderjabouzi.thescoretest.presentation.R

class TeamsListFragment : Fragment() {

    companion object {
        fun newInstance() = TeamsListFragment()
    }

    private lateinit var viewModel: TeamsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teams_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeamsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
