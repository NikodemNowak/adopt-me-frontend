package com.nikodem.adoptme.utils

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<STATE : ViewState>(
    private val initialState: STATE
) : ViewModel() {
    private val _viewState: MutableLiveData<STATE> = MutableLiveData(initialState)
    val viewState: LiveData<STATE> = _viewState
    val inProgress: LiveData<Boolean> = _viewState.map { it.isLoading }

    val isInitialState: Boolean
        get() = _viewState.value === initialState
    val currentState: STATE
        get() = viewState.value!!

    val showToastEvent = LiveEvent<String>()
    val showSnackbarEvent = LiveEvent<String>()
    val showErrorDialogEvent = LiveEvent<ErrorEvent>()

    protected fun updateViewState(update: (STATE) -> STATE) {
        val newState = update(_viewState.value!!)
        if (newState != _viewState.value!!) {
            _viewState.value = newState
        }
    }

    open fun handleError(throwable: Throwable, retryAction: () -> Unit) {
        showErrorDialogEvent.fireEvent(
            ErrorEvent(
                throwable = throwable,
                retryAction = retryAction,
                onDialogCancelled = ::onErrorDialogCancelled
            )
        )
    }

    protected fun safeLaunch(
        onFailure: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, exception ->
                Timber.e(exception, "CoroutineExceptionHandler")

                if (onFailure != null) {
                    onFailure.invoke(exception)
                } else {
                    handleError(exception, retryAction = { safeLaunch(onFailure, block) })
                }
            },
            block = block
        )
    }

    open fun onErrorDialogCancelled() {
        // Implement
    }
}

fun LiveEvent<Unit>.fireEvent() {
    value = Unit
}

fun <T> LiveEvent<T>.fireEvent(t: T) {
    value = t
}
