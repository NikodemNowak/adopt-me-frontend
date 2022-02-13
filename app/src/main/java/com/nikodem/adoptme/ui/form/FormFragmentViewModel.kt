package com.nikodem.adoptme.ui.form

import com.nikodem.adoptme.usecases.AddUserPreferenceUseCase
import com.nikodem.adoptme.usecases.GetQuestionAnswersUseCase
import com.nikodem.adoptme.usecases.UserPreference
import com.nikodem.adoptme.utils.AdoptMeError
import com.nikodem.adoptme.utils.BaseViewModel
import com.nikodem.adoptme.utils.ViewState
import com.nikodem.adoptme.utils.fireEvent
import kotlinx.coroutines.flow.collect

class FormFragmentViewModel(
    private val getQuestionAnswersUseCase: GetQuestionAnswersUseCase,
    private val addUserPreferenceUseCase: AddUserPreferenceUseCase
) :
    BaseViewModel<FormFragmentViewState>(initialState = FormFragmentViewState()) {

    fun loadQuestionAnswers() {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }

        safeLaunch {
            getQuestionAnswersUseCase.invoke().collect { data ->
                updateViewState {
                    it.copy(
                        questionId = data[0].uuid,
                        questionText = data[0].questionText.toNullableString(),
                        answer1 = data[0].answer1.toNullableString(),
                        answer2 = data[0].answer2.toNullableString(),
                        answer3 = data[0].answer3.toNullableString(),
                        answer4 = data[0].answer4.toNullableString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onFirstButtonClick() {

    }

    fun onSecondButtonClick() {

    }

    fun onThirdButtonClick() {

    }

    fun onFourthButtonClick() {

    }

    fun sendUserPreferences(answer: String) {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }
        safeLaunch {
            with(viewState.value!!) {
                addUserPreferenceUseCase.invoke(
                    UserPreference(
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

fun String?.toNullableString() = this ?: "-"