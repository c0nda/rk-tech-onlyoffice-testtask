package com.listener.onlyoffice.presentation.documents.common

import androidx.recyclerview.widget.DiffUtil

class DocumentsDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem is ListItem.FolderItem && newItem is ListItem.FolderItem && oldItem.folder.id == newItem.folder.id ||
                oldItem is ListItem.FileItem && newItem is ListItem.FileItem && oldItem.file.id == newItem.file.id

    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}