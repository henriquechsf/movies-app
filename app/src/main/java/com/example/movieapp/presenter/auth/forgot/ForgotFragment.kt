package com.example.movieapp.presenter.auth.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentForgotBinding
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.util.FirebaseHelper
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.isEmailValid
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)

        initListeners()
    }

    private fun initListeners() {
        binding.btnForgot.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressLoading)
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString()

        if (email.isEmailValid()) {
            hideKeyboard()
            forgot(email)
        } else {
            showSnackBar(R.string.text_email_empty_forgot_fragment)
        }
    }

    private fun forgot(email: String) {
        viewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressLoading.isVisible = true
                }
                is StateView.Success -> {
                    showSnackBar(message = R.string.text_send_email_success_forgot_fragment)
                }
                is StateView.Error -> {
                    binding.progressLoading.isVisible = false
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}