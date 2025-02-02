package com.listener.onlyoffice.presentation.documents.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.listener.onlyoffice.R

class DocumentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val ivFileIcon = itemView.findViewById<ImageView>(R.id.ivFileIcon)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

    fun bind(item: ListItem, listener: DocumentsListItemClickListener) {
        if (item is ListItem.FileItem) {
            tvTitle.text = item.file.title
            ivFileIcon.setImageResource(R.drawable.ic_document)
        } else {
            tvTitle.text = (item as ListItem.FolderItem).folder.title
            ivFileIcon.setImageResource(R.drawable.ic_folder)
        }
        itemView.setOnClickListener {
            listener.onItemClicked(item)
        }
    }
}