package com.listener.onlyoffice.domain.usecase

import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(email: String, password: String): Flow<Request<AccessToken>> {
        return userRepository.doAuth(email, password)
    }
}