package com.listener.onlyoffice.presentation.documents.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.listener.onlyoffice.data.local.DataStoreManager
import com.listener.onlyoffice.data.remote.retrofit.ApiKeyInterceptor
import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.usecase.GetFolderContentUseCase
import com.listener.onlyoffice.domain.usecase.GetRoomsUseCase
import com.listener.onlyoffice.utils.Constants
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomsViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val getFolderContentUseCase: GetFolderContentUseCase,
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _states: MutableStateFlow<List<Page>> = MutableStateFlow(emptyList())
    val states = _states.asStateFlow()

    init {
        getRooms()
    }

    private fun getRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            apiKeyInterceptor.setApiKey(dataStoreManager.get(Constants.API_KEY))
            getRoomsUseCase.execute().collect { request ->
                if (request is Request.Success) {
                    val list = mutableListOf<Page>()
                    list.addAll(_states.value)
                    list.add(request.data)
                    _states.value = list
                }
            }
        }
    }

    fun getFolderContent(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            apiKeyInterceptor.setApiKey(dataStoreManager.get(Constants.API_KEY))
            getFolderContentUseCase.execute(id).collect { request ->
                if (request is Request.Success) {
                    val list = mutableListOf<Page>()
                    list.addAll(_states.value)
                    list.add(request.data)
                    _states.value = list
                }
            }
        }
    }

    fun goBack() {
        val list = mutableListOf<Page>()
        list.addAll(_states.value)
        list.removeLast()
        _states.value = list
    }
}