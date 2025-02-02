package com.listener.onlyoffice.presentation.documents.common

import com.listener.onlyoffice.domain.model.File
import com.listener.onlyoffice.domain.model.Folder

sealed class ListItem {
    data class FileItem(val file: File): ListItem()
    data class FolderItem(val folder: Folder): ListItem()
}