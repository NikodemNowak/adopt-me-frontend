package com.nikodem.adoptme.utils.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.utils.fireEvent

sealed class NavigationCommand {
    object Back : NavigationCommand()
    data class To(val navDirections: NavDirections) : NavigationCommand()
}

interface NavigationManager {
    val navigationEvent: LiveEvent<NavigationCommand>

    fun navigateBack()
    fun navigateTo(navDirections: NavDirections)
}

class NavigationManagerImpl : NavigationManager {
    override val navigationEvent: LiveEvent<NavigationCommand> = LiveEvent()

    override fun navigateBack() {
        navigationEvent.fireEvent(NavigationCommand.Back)
    }

    override fun navigateTo(navDirections: NavDirections) {
        navigationEvent.fireEvent(NavigationCommand.To(navDirections))
    }
}

fun NavigationManager.observeNavigationEvents(fragment: Fragment) {
    navigationEvent.observe(fragment.viewLifecycleOwner) {
        when (it){
            NavigationCommand.Back -> fragment.findNavController().navigateUp()
            is NavigationCommand.To -> fragment.findNavController().navigate(it.navDirections)
        }
    }
}