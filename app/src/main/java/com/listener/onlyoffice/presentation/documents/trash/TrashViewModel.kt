package com.listener.onlyoffice.presentation.documents.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listener.onlyoffice.data.local.DataStoreManager
import com.listener.onlyoffice.data.remote.retrofit.ApiKeyInterceptor
import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.usecase.GetTrashUseCase
import com.listener.onlyoffice.utils.Constants
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrashViewModel @Inject constructor(
    private val getTrashUseCase: GetTrashUseCase,
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _states: MutableStateFlow<Page> = MutableStateFlow(Page(emptyList(), emptyList()))
    val states = _states.asStateFlow()

    init {
        getTrash()
    }

    private fun getTrash() {
        viewModelScope.launch(Dispatchers.IO) {
            apiKeyInterceptor.setApiKey(dataStoreManager.get(Constants.API_KEY))
            getTrashUseCase.execute().collect { request ->
                if (request is Request.Success) {
                    _states.value = request.data
                }
            }
        }
    }
}