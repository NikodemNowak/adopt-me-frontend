package com.nikodem.adoptme.ui.profile

import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentProfileBinding
import com.nikodem.adoptme.utils.BaseFragment

class ProfileFragment :
    BaseFragment<ProfileFragmentViewState, ProfileFragmentViewModel, FragmentProfileBinding>(
        R.layout.fragment_profile, ProfileFragmentViewModel::class
    ) {
}