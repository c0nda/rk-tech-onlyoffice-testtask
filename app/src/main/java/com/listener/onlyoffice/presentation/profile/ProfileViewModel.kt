package com.listener.onlyoffice.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listener.onlyoffice.data.local.DataStoreManager
import com.listener.onlyoffice.data.remote.retrofit.ApiKeyInterceptor
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.domain.usecase.GetUserProfileUseCase
import com.listener.onlyoffice.domain.usecase.LogoutUserUseCase
import com.listener.onlyoffice.utils.Constants
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _profile: MutableStateFlow<Request<UserProfile>> = MutableStateFlow(Request.Init())
    val profile = _profile.asStateFlow()

    private val _isLogout: MutableStateFlow<Request<Unit>> = MutableStateFlow(Request.Init())
    val isLogout = _isLogout.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            apiKeyInterceptor.setApiKey(dataStoreManager.get(Constants.API_KEY))
            getUserProfileUseCase.execute().collect { request ->
                _profile.value = request
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUserUseCase.execute().collect { request ->
                if (request is Request.Success) {
                    dataStoreManager.save(Constants.PORTAL, "")
                    dataStoreManager.save(Constants.API_KEY, "")
                }
                _isLogout.value = request
            }
        }
    }

    suspend fun getPortal(): String {
        val portal = viewModelScope.async(Dispatchers.IO) {
            dataStoreManager.get(Constants.PORTAL)!!
        }
        return portal.await()
    }
}