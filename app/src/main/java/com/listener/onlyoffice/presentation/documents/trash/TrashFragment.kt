package com.listener.onlyoffice.presentation.documents.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.listener.onlyoffice.DI
import com.listener.onlyoffice.R
import com.listener.onlyoffice.presentation.documents.common.DocumentsAdapter
import com.listener.onlyoffice.presentation.documents.common.DocumentsListItemClickListener
import com.listener.onlyoffice.presentation.documents.common.ListItem
import com.listener.onlyoffice.presentation.documents.rooms.RoomsViewModel
import kotlinx.coroutines.launch

class TrashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.docs_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llBottomNavView =
            requireActivity().findViewById<LinearLayout>(R.id.ll_bottom_navigation_view)
        llBottomNavView.visibility = View.VISIBLE

        val tvDocuments = view.findViewById<TextView>(R.id.tvDocuments)
        val ibBack = view.findViewById<ImageButton>(R.id.ibBack)
        val rvFiles = view.findViewById<RecyclerView>(R.id.rvFiles)

        ibBack.visibility = View.GONE
        tvDocuments.text = getString(R.string.trash)

        val trashViewModel = DI.appComponent.viewModelFactory().create(TrashViewModel::class.java)

        val documentsAdapter = DocumentsAdapter(object : DocumentsListItemClickListener {
            override fun onItemClicked(listItem: ListItem) {
                when (listItem) {
                    is ListItem.FileItem -> {
                    }

                    is ListItem.FolderItem -> {
                    }
                }
            }
        })
        rvFiles.adapter = documentsAdapter
        rvFiles.layoutManager = LinearLayoutManager(this.context)
        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider,
                resources.newTheme()
            )!!
        )
        rvFiles.addItemDecoration(dividerItemDecoration)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                trashViewModel.states.collect { page ->
                    val data = mutableListOf<ListItem>()
                    data.addAll(page.folders.map { ListItem.FolderItem(it) })
                    data.addAll(page.files.map { ListItem.FileItem(it) })
                    documentsAdapter.submitList(data)
                }
            }
        }
    }
}