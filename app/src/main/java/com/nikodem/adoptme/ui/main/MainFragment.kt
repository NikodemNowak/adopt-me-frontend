package com.nikodem.adoptme.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentMainBinding
import com.nikodem.adoptme.ui.favorites.FavoritesFragment
import com.nikodem.adoptme.ui.home_screen.HomeScreenFragment
import com.nikodem.adoptme.ui.profile.ProfileFragment
import com.nikodem.adoptme.utils.BaseFragment

class MainFragment :
    BaseFragment<MainFragmentViewState, MainFragmentViewModel, FragmentMainBinding>(
        R.layout.fragment_main, MainFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val pagerAdapter = ScreenSlidePagerAdapter(this@MainFragment)
            viewPagerFragmentMain.adapter = pagerAdapter

            TabLayoutMediator(tabLayoutFragmentMain, viewPagerFragmentMain) { _, _ ->

            }.attach()

            with(tabLayoutFragmentMain) {
                getTabAt(0)?.setIcon(R.drawable.ic_baseline_home_24)
                getTabAt(1)?.setIcon(R.drawable.ic_baseline_star_24)
                getTabAt(2)?.setIcon(R.drawable.ic_baseline_person_24)
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUMBER_OF_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> HomeScreenFragment()
            1 -> FavoritesFragment()
            2 -> ProfileFragment()
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        const val NUMBER_OF_PAGES = 3
    }
}