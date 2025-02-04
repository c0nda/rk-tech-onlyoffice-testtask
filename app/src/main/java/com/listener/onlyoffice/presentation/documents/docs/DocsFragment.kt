package com.listener.onlyoffice.presentation.documents.docs

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
import kotlinx.coroutines.launch

class DocsFragment : Fragment() {

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

        tvDocuments.text = getString(R.string.documents)

        val docsViewModel = DI.appComponent.viewModelFactory().create(DocsViewModel::class.java)

        if (docsViewModel.states.value.size > 1) {
            ibBack.visibility = View.VISIBLE
        }

        val documentsAdapter = DocumentsAdapter(object : DocumentsListItemClickListener {
            override fun onItemClicked(listItem: ListItem) {
                when (listItem) {
                    is ListItem.FileItem -> {

                    }

                    is ListItem.FolderItem -> {
                        docsViewModel.getFolderContent(listItem.folder.id)
                        ibBack.visibility = View.VISIBLE
                        tvDocuments.text = listItem.folder.title
                    }
                }
            }
        })
        rvFiles.adapter = documentsAdapter
        rvFiles.layoutManager = LinearLayoutManager(this.context)
        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.divider, resources.newTheme())!!)
        rvFiles.addItemDecoration(dividerItemDecoration)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                docsViewModel.states.collect { pageList ->
                    if (pageList.isNotEmpty()) {
                        val data = mutableListOf<ListItem>()
                        data.addAll(pageList.last().folders.map { ListItem.FolderItem(it) })
                        data.addAll(pageList.last().files.map { ListItem.FileItem(it) })
                        documentsAdapter.submitList(data)
                    } else {
                        docsViewModel.getDocuments()
                    }
                }
            }
        }

        ibBack.setOnClickListener {
            docsViewModel.goBack()
            if (docsViewModel.states.value.size <= 1) {
                ibBack.visibility = View.GONE
                tvDocuments.text = getString(R.string.documents)
            }
        }
    }
}