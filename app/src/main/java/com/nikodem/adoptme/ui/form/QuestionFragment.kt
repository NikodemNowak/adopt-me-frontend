package com.nikodem.adoptme.ui.form

import android.os.Bundle
import android.view.View
import com.nikodem.adoptme.R
import com.nikodem.adoptme.databinding.FragmentFormQuestionBinding
import com.nikodem.adoptme.ui.dialog.CancelRegistrationDialog
import com.nikodem.adoptme.utils.BaseFragment


class QuestionFragment :
    BaseFragment<FormFragmentViewState, FormFragmentViewModel, FragmentFormQuestionBinding>(
        R.layout.fragment_form_question, FormFragmentViewModel::class
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadQuestionAnswers()

        onBackEvent = {
        }

        with(binding) {
            answer1.setOnClickListener {
                val answer = viewModel.viewState.value!!.answer1
                viewModel.sendUserPreferences(answer)
            }
            answer2.setOnClickListener {
                val answer = viewModel.viewState.value!!.answer2
                viewModel.sendUserPreferences(answer)
            }
            answer3.setOnClickListener {
                val answer = viewModel.viewState.value!!.answer3
                viewModel.sendUserPreferences(answer)
            }
            answer4.setOnClickListener {
                val answer = viewModel.viewState.value!!.answer4
                viewModel.sendUserPreferences(answer)
            }
        }
    }
}