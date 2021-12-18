package com.nikodem.adoptme.ui.form

import android.view.View
import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.CachedAdoptMeRepository
import com.nikodem.adoptme.services.UserPreferencesPostRequest
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent

class FormFragmentViewModel(
    private val questionAnswersRepository: AdoptMeRepository,
    private val cachedAdoptMeRepository: CachedAdoptMeRepository
) :
    BaseViewModel<FormFragmentViewState>(initialState = FormFragmentViewState()) {

    fun loadQuestionAnswers() {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }
        safeLaunch {
            val data = questionAnswersRepository.getQuestionAnswers()
            cachedAdoptMeRepository.apply {
                questionAnswers = data
                currentQuestion = 0
            }

            updateViewState {
                it.copy(
                    questionId = data[0].id,
                    questionText = data[0].questionText,
                    answer1 = data[0].answer1,
                    answer2 = data[0].answer2,
                    answer3 = data[0].answer3,
                    answer4 = data[0].answer4,
                    isLoading = false
                )
            }

        }
    }

    fun onFirstButtonClick() {

    }

    fun sendUserPreferences(answer: String) {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }
        safeLaunch {
            with(viewState.value!!) {
                questionAnswersRepository.addUserPreferences(
                    UserPreferencesPostRequest(
                        userId,
                        questionId,
                        answer
                    )
                )
            }
            updateViewState {
                it.copy(
                    isLoading = false
                )
            }

        }
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        showToastEvent.fireEvent(exception.toString())
    }
}

data class FormFragmentViewState(
    val questionId: String = "",
    val questionText: String = "",
    val answer1: String = "",
    val answer2: String = "",
    val answer3: String = "",
    val answer4: String = "",
    val userId: String = "ae16f6a0-4e36-463d-b95e-9f1916f748c3",
    override val isLoading: Boolean = false
) : ViewState