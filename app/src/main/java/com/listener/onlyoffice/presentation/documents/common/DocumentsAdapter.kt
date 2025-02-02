package com.listener.onlyoffice.presentation.documents.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.listener.onlyoffice.R

class DocumentsAdapter(
    private val listener: DocumentsListItemClickListener
) : ListAdapter<ListItem, DocumentsViewHolder>(DocumentsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        return DocumentsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }
}