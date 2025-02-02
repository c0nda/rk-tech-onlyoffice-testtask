package com.listener.onlyoffice.domain.usecase

import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFolderContentUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(id: Long): Flow<Request<Page>> {
        return userRepository.getFolderContent(id)
    }
}