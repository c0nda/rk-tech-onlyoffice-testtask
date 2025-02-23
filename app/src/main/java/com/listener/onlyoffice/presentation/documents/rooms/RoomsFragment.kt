package com.listener.onlyoffice.presentation.documents.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.listener.onlyoffice.databinding.DocsFragmentBinding
import com.listener.onlyoffice.presentation.MainActivity
import com.listener.onlyoffice.presentation.documents.common.DocumentsAdapter
import com.listener.onlyoffice.presentation.documents.common.DocumentsListItemClickListener
import com.listener.onlyoffice.presentation.documents.common.ListItem
import kotlinx.coroutines.launch

class RoomsFragment : Fragment() {

    private var _binding: DocsFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DocsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llBottomNavView =
            requireActivity().findViewById<LinearLayout>(R.id.ll_bottom_navigation_view)
        llBottomNavView.visibility = View.VISIBLE

        (requireActivity() as MainActivity).binding.llBottomNavigationView.visibility = View.VISIBLE

        binding.tvDocuments.text = getString(R.string.rooms)

        val roomsViewModel = DI.appComponent.viewModelFactory().create(RoomsViewModel::class.java)

        if (roomsViewModel.states.value.size > 1) {
            binding.ibBack.visibility = View.VISIBLE
        }

        val documentsAdapter = DocumentsAdapter(object : DocumentsListItemClickListener {
            override fun onItemClicked(listItem: ListItem) {
                when (listItem) {
                    is ListItem.FileItem -> {

                    }

                    is ListItem.FolderItem -> {
                        roomsViewModel.getFolderContent(listItem.folder.id)
                        binding.ibBack.visibility = View.VISIBLE
                        binding.tvDocuments.text = listItem.folder.title
                    }
                }
            }
        })
        binding.rvFiles.adapter = documentsAdapter
        binding.rvFiles.layoutManager = LinearLayoutManager(this.context)
        val dividerItemDecoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.divider, resources.newTheme())!!)
        binding.rvFiles.addItemDecoration(dividerItemDecoration)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                roomsViewModel.states.collect { pageList ->
                    if (pageList.isNotEmpty()) {
                        val data = mutableListOf<ListItem>()
                        data.addAll(pageList.last().folders.map { ListItem.FolderItem(it) })
                        data.addAll(pageList.last().files.map { ListItem.FileItem(it) })
                        documentsAdapter.submitList(data)
                    }
                }
            }
        }

        binding.ibBack.setOnClickListener {
            roomsViewModel.goBack()
            if (roomsViewModel.states.value.size <= 1) {
                binding.ibBack.visibility = View.GONE
                binding.tvDocuments.text = getString(R.string.rooms)
            }
        }
    }
}