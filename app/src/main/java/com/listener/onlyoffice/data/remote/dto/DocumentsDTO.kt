package com.listener.onlyoffice.data.remote.dto

data class DocumentsDTO(
    val files: List<FileDTO>,
    val folders: List<FolderDTO>
)