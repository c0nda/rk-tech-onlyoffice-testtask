package com.listener.onlyoffice.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.databinding.ProfileFragmentBinding
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.presentation.MainActivity
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).binding.llBottomNavigationView.visibility = View.VISIBLE

        val profileViewModel =
            DI.appComponent.viewModelFactory().create(ProfileViewModel::class.java)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.profile.collect {
                    when (profileViewModel.profile.value) {
                        is Request.Init -> {
                        }

                        is Request.Success -> {
                            val data =
                                (profileViewModel.profile.value as Request.Success<UserProfile>).data
                            withContext(Dispatchers.Main) {
                                binding.tvEmail.text = data.email
                                binding.tvUserName.text = data.displayName
                            }
                            Glide.with(view)
                                .load(profileViewModel.getPortal() + data.avatar)
                                .circleCrop()
                                .into(binding.ivAvatar)
                        }

                        is Request.Error -> {
                        }

                        is Request.Loading -> {

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.isLogout.collect { request ->
                    if (request is Request.Success) {
                        navigateToLogin()
                    }
                }
            }
        }

        binding.bLogout.setOnClickListener {
            profileViewModel.logout()
        }
    }

    private fun navigateToLogin() {
        val action = ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
        findNavController().navigate(action)
    }
}