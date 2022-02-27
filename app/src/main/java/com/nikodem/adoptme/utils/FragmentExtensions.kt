package com.nikodem.adoptme.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.nikodem.adoptme.ui.theme.AdoptMeTheme

fun Fragment.adoptMeComposeView(
    content: @Composable () -> Unit
): ComposeView {
    return ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        setContent {
            AdoptMeTheme {
                content()
            }
        }
    }
}
