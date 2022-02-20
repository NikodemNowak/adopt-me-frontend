package com.nikodem.adoptme.ui.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentHomeScreenBinding
import com.nikodem.adoptme.ui.theme.AdoptMeTheme
import com.nikodem.adoptme.utils.BaseFragment

class HomeScreenFragment :
    BaseFragment<HomeScreenFragmentViewState, HomeScreenFragmentViewModel, FragmentHomeScreenBinding>(
        R.layout.fragment_home_screen, HomeScreenFragmentViewModel::class
    ) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                AdoptMeTheme {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}