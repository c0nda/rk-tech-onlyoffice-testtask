package com.listener.onlyoffice.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.R
import com.listener.onlyoffice.databinding.AuthFragmentBinding
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.presentation.MainActivity
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).binding.llBottomNavigationView.visibility = View.GONE

        val authViewModel = DI.appComponent.viewModelFactory().create(AuthViewModel::class.java)

        var emailValid = false
        var urlValid = false

        binding.etPortal.doOnTextChanged { text, _, _, _ ->
            text?.let {
                urlValid = android.util.Patterns.WEB_URL.matcher(text.toString()).matches()
            }
        }

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            text?.let {
                emailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
            }
        }

        binding.bLogin.setOnClickListener {
            if (emailValid && urlValid) {
                authViewModel.loginUser(
                binding.etPortal.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
                )
            } else {
                Toast.makeText(context, resources.getText(R.string.invalid), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.accessToken.collect {
                    when (authViewModel.accessToken.value) {
                        is Request.Init -> {
                        }

                        is Request.Success -> {
                            binding.piLoading.visibility = View.GONE
                            navigateToDocsFragment()
                        }

                        is Request.Error -> {
                            binding.bLogin.isClickable = true
                            binding.piLoading.visibility = View.GONE
                            Toast.makeText(
                                context,
                                (authViewModel.accessToken.value as Request.Error<AccessToken>).error?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Request.Loading -> {
                            binding.bLogin.isClickable = false
                            binding.piLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.isAuth.collect {
                    when (authViewModel.isAuth.value) {
                        is Request.Init -> {
                            authViewModel.checkAuth()
                        }

                        is Request.Success -> {
                            if ((authViewModel.isAuth.value as Request.Success<Boolean>).data) {
                                binding.piLoading.visibility = View.GONE
                                navigateToDocsFragment()
                            }
                        }

                        is Request.Error -> {
                        }

                        is Request.Loading -> {
                            binding.bLogin.isClickable = false
                            binding.piLoading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDocsFragment() {
        val action = AuthFragmentDirections.actionAuthFragmentToDocsFragment()
        findNavController().navigate(action)
    }
}