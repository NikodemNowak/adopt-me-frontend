package com.nikodem.adoptme.ui.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.nikodem.adoptme.utils.adoptMeComposeView
import com.nikodem.adoptme.utils.navigation.observeNavigationEvents
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return adoptMeComposeView {
            HomeScreen(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeNavigationEvents(this)
    }
}
