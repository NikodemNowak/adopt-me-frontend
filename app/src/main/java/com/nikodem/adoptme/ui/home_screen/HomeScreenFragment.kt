package com.nikodem.adoptme.ui.home_screen

import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentHomeScreenBinding
import com.nikodem.adoptme.utils.BaseFragment

class HomeScreenFragment :
    BaseFragment<HomeScreenFragmentViewState, HomeScreenFragmentViewModel, FragmentHomeScreenBinding>(
        R.layout.fragment_home_screen, HomeScreenFragmentViewModel::class
    ) {
}