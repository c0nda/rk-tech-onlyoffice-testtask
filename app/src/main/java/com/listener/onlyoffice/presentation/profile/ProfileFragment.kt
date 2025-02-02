package com.listener.onlyoffice.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.R
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.presentation.auth.AuthFragmentDirections
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llBottomNavView =
            requireActivity().findViewById<LinearLayout>(R.id.ll_bottom_navigation_view)
        llBottomNavView.visibility = View.VISIBLE

        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val buttonLogout = view.findViewById<Button>(R.id.bLogout)

        val profileViewModel =
            DI.appComponent.viewModelFactory().create(ProfileViewModel::class.java)

        profileViewModel.getProfile()

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
                                tvEmail.text = data.email
                                tvUserName.text = data.displayName
                            }
                            Glide.with(view)
                                .load(profileViewModel.getPortal() + data.avatar)
                                .circleCrop()
                                .into(ivAvatar)
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

        buttonLogout.setOnClickListener {
            profileViewModel.logout()
        }
    }

    private fun navigateToLogin() {
        val action = ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
        findNavController().navigate(action)
    }
}