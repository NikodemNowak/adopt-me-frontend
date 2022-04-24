package com.nikodem.adoptme.ui.home_screen

import androidx.compose.animation.core.updateTransition
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nikodem.adoptme.BuildConfig
import com.nikodem.adoptme.db.dao.AnimalDao
import com.nikodem.adoptme.usecases.Animal
import com.nikodem.adoptme.usecases.GetAnimalsUseCase
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.navigation.NavigationManager
import com.nikodem.adoptme.utils.navigation.NavigationManagerImpl
import kotlinx.coroutines.flow.collect

class HomeScreenFragmentViewModel(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val animalMediator: AnimalMediator,
    private val animalDao: AnimalDao
) :
    BaseViewModel<HomeScreenFragmentViewState>(
        initialState = HomeScreenFragmentViewState()
    ), NavigationManager by NavigationManagerImpl() {

    @OptIn(ExperimentalPagingApi::class)
    val animals = Pager(
        PagingConfig(
            enablePlaceholders = true,
            pageSize = 20,
            prefetchDistance = 20
        ),
        remoteMediator = animalMediator,
        pagingSourceFactory = { animalDao.pagingSource() }
    ).flow.cachedIn(viewModelScope)

    fun onButtonClick() {
        updateViewState { it.copy(isLoading = !it.isLoading) }
    }

    fun onItemClicked(animalId: String) {
        navigateTo(HomeScreenFragmentDirections.toDetailsScreenFragment())
    }

    fun navigateToProfile() {
        navigateTo(HomeScreenFragmentDirections.actionHomeScreenFragmentToProfileScreenFragment())
    }
}

data class HomeScreenFragmentViewState(
    override val isLoading: Boolean = false,
    val animals: List<Animal> = emptyList()
) : ViewState