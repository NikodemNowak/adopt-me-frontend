package com.nikodem.adoptme.ui.favorites

import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentFavoritesBinding
import com.nikodem.adoptme.utils.BaseFragment

class FavoritesFragment :
    BaseFragment<FavoritesFragmentViewState, FavoritesFragmentViewModel, FragmentFavoritesBinding>(
        R.layout.fragment_favorites,
        FavoritesFragmentViewModel::class
    ) {
}