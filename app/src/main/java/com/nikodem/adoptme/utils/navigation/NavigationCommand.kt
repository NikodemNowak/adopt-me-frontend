package com.nikodem.adoptme.utils.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.hadilq.liveevent.LiveEvent
import com.nikodem.adoptme.R
import com.nikodem.adoptme.utils.fireEvent

sealed class NavigationCommand {
    object Logout : NavigationCommand()
    object Back : NavigationCommand()
    data class To(val navDirections: NavDirections) : NavigationCommand()
}

interface NavigationManager {
    val navigationEvent: LiveEvent<NavigationCommand>

    fun logout()
    fun navigateBack()
    fun navigateTo(navDirections: NavDirections)
}

class NavigationManagerImpl : NavigationManager {
    override val navigationEvent: LiveEvent<NavigationCommand> = LiveEvent()
    override fun logout() {
        navigationEvent.fireEvent(NavigationCommand.Logout)
    }

    override fun navigateBack() {
        navigationEvent.fireEvent(NavigationCommand.Back)
    }

    override fun navigateTo(navDirections: NavDirections) {
        navigationEvent.fireEvent(NavigationCommand.To(navDirections))
    }
}

fun NavigationManager.observeNavigationEvents(fragment: Fragment) {
    navigationEvent.observe(fragment.viewLifecycleOwner) {
        when (it) {
            NavigationCommand.Logout -> {
                fragment.findNavController()
                    .navigate(
                        R.id.loginOrRegisterFragment,
                        bundleOf(),
                        navOptions {
                            popUpTo(R.id.nav_graph) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    )
            }
            NavigationCommand.Back -> fragment.findNavController().navigateUp()
            is NavigationCommand.To -> fragment.findNavController().navigate(it.navDirections)
        }
    }
}