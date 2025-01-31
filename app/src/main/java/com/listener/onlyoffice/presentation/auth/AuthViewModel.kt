package com.listener.onlyoffice.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listener.onlyoffice.data.remote.retrofit.HostSelectionInterceptor
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.usecase.AuthenticateUserUseCase
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val _portal: MutableStateFlow<String> = MutableStateFlow("")
    val portal = _portal.asStateFlow()

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _requestResult: MutableStateFlow<Request<AccessToken>> = MutableStateFlow(
        Request.Success(
            AccessToken("")
        )
    )
    val requestResult = _requestResult.asStateFlow()

    fun loginUser(inputPortal: String, inputEmail: String, inputPasswordString: String) {
        HostSelectionInterceptor.setNewHost(inputPortal)
        viewModelScope.launch(Dispatchers.IO) {
            authenticateUserUseCase.execute(inputEmail, inputPasswordString).collect { request ->
                _requestResult.value = request
            }
        }
    }

    fun setPortal(text: String) {
        _portal.value = text
    }

    fun setEmail(text: String) {
        _email.value = text
    }

    fun setPassword(text: String) {
        _password.value = text
    }
}