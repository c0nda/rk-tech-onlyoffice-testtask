package com.listener.onlyoffice.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.R
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llBottomNavView =
            requireActivity().findViewById<LinearLayout>(R.id.ll_bottom_navigation_view)
        llBottomNavView.visibility = View.GONE

        val editTextPortal = view.findViewById<EditText>(R.id.etPortal)
        val editTextEmail = view.findViewById<EditText>(R.id.etEmail)
        val editTextPassword = view.findViewById<EditText>(R.id.etPassword)
        val buttonLogin = view.findViewById<Button>(R.id.bLogin)

        val authViewModel = DI.appComponent.viewModelFactory().create(AuthViewModel::class.java)

        editTextPortal.doOnTextChanged { text, _, _, _ ->
            text?.let {
                authViewModel.setPortal(text.toString())
            }
        }

        editTextEmail.doOnTextChanged { text, _, _, _ ->
            text?.let {
                authViewModel.setEmail(text.toString())
            }
        }

        editTextPassword.doOnTextChanged { text, _, _, _ ->
            text?.let {
                authViewModel.setPassword(text.toString())
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.portal.collect {
                    editTextPortal.setText(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.email.collect {
                    editTextEmail.setText(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.password.collect {
                    editTextPassword.setText(it)
                }
            }
        }

        buttonLogin.setOnClickListener {
            authViewModel.loginUser(
                editTextPortal.text.toString(),
                editTextEmail.text.toString(),
                editTextPassword.text.toString()
            )
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.requestResult.collect {
                    when (authViewModel.requestResult.value) {
                        is Request.Success -> {

                        }
                        is Request.Error -> {

                        }
                        is Request.Loading -> {}
                    }
                }
            }
        }
    }
}