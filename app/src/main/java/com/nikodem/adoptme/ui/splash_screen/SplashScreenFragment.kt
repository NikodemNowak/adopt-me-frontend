package com.nikodem.adoptme.ui.splash_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nikodem.adoptme.utils.adoptMeComposeView
import com.nikodem.adoptme.utils.navigation.observeNavigationEvents

import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment: Fragment() {

    private val viewModel: SplashScreenFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return adoptMeComposeView {
            SplashScreen(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeNavigationEvents(this)
        viewModel.onViewInitialized()
    }
}