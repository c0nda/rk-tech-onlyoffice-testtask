package com.listener.onlyoffice.domain.usecase

import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): Flow<Request<Unit>> {
        return userRepository.logoutUser()
    }
}