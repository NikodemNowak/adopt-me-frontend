package com.nikodem.adoptme.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.nikodem.adoptme.BR
import com.nikodem.adoptme.R
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseFragment<STATE : ViewState, VM : BaseViewModel<STATE>, VDB : ViewDataBinding>(
    @LayoutRes private val contentLayout: Int,
    viewModelKClass: KClass<VM>
) : Fragment(contentLayout) {

    private var _binding: VDB? = null
    protected val binding get() = _binding!!

    val viewModel: VM by lazy {
        getViewModel(clazz = viewModelKClass)
    }

    private var loader: LottieAnimationView? = null

    var onBackEvent: () -> Unit = {
        runCatching {
            findNavController().navigateUp()
        }.onFailure {
            Timber.e(it, "onBackEvent error")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<VDB>(inflater, contentLayout, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.vm, viewModel)
        }
        return RelativeLayout(requireContext()).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT,
            )
            id = R.id.loader_container
            addView(binding.root, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
            loader = LottieAnimationView(requireContext()).apply {
                isClickable = true
                isFocusable = true
                visibility = View.GONE
                setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                setAnimation(R.raw.loading_animation)
                repeatMode = LottieDrawable.RESTART
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }
            addView(loader, RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackEvent.invoke()
        }

        with(viewModel) {
            inProgress.observe(viewLifecycleOwner) {
                loader?.goneIfWithAnimation(!it)
            }
            viewState.observe(viewLifecycleOwner) {
                onStateChanged(it)
            }
            showSnackbarEvent.observe(viewLifecycleOwner) {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
            showToastEvent.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    protected open fun onStateChanged(state: STATE) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}