package com.listener.onlyoffice.domain.usecase

import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUserAuthenticationUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): Flow<Request<Boolean>> {
        return userRepository.checkAuth()
    }
}