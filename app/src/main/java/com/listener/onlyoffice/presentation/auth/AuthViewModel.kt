package com.listener.onlyoffice.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listener.onlyoffice.data.local.DataStoreManager
import com.listener.onlyoffice.data.remote.retrofit.ApiKeyInterceptor
import com.listener.onlyoffice.data.remote.retrofit.HostSelectionInterceptor
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.usecase.AuthenticateUserUseCase
import com.listener.onlyoffice.domain.usecase.CheckUserAuthenticationUseCase
import com.listener.onlyoffice.utils.Constants
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val checkUserAuthenticationUseCase: CheckUserAuthenticationUseCase,
    private val hostSelectionInterceptor: HostSelectionInterceptor,
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _accessToken: MutableStateFlow<Request<AccessToken>> = MutableStateFlow(Request.Init())
    val accessToken = _accessToken.asStateFlow()

    private val _isAuth: MutableStateFlow<Request<Boolean>> = MutableStateFlow(Request.Init())
    val isAuth = _isAuth.asStateFlow()

    fun loginUser(inputPortal: String, inputEmail: String, inputPasswordString: String) {
        hostSelectionInterceptor.setNewHost(inputPortal)
        viewModelScope.launch(Dispatchers.IO) {
            authenticateUserUseCase.execute(inputEmail, inputPasswordString).collect { request ->
                _accessToken.value = request
                if (request is Request.Success) {
                    dataStoreManager.save(Constants.PORTAL, inputPortal)
                    dataStoreManager.save(Constants.API_KEY, request.data.token)
                }
            }
        }
    }

    fun checkAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            val portal = dataStoreManager.get(Constants.PORTAL)
            apiKeyInterceptor.setApiKey(dataStoreManager.get(Constants.API_KEY))
            if (!portal.isNullOrEmpty()) {
                hostSelectionInterceptor.setNewHost(portal)
                checkUserAuthenticationUseCase.execute().collect { request ->
                    _isAuth.value = request
                }
            }
        }
    }
}