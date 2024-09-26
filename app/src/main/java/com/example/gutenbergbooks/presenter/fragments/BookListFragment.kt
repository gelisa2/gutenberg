package com.example.gutenbergbooks.presenter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.databinding.FragmentBookListBinding
import com.example.gutenbergbooks.presenter.adapters.BookListAdapter
import com.example.gutenbergbooks.presenter.viewmodel.BookListViewModel
import com.example.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    private val binding by lazy {
        FragmentBookListBinding.inflate(layoutInflater)
    }

    private val bookListAdapter = BookListAdapter()

    private val viewmodel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {
        initRecycler()
        observeBookList()
        initRecyclerClickListener()
    }

    private fun observeBookList() {
        lifecycleScope.launch {
            viewmodel.bookListFlow.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progress.visibility = View.GONE
                        it.data?.results?.let {
                            bookListAdapter.addBooks(it)
                        }
                    }

                    is Resource.Error -> {
                        binding.progress.visibility = View.GONE
                    }
                }
            }
        }

    }

    private fun initRecycler() {
        binding.bookRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookListAdapter
            setHasFixedSize(true)
        }
    }

    private fun initRecyclerClickListener() {
        bookListAdapter.setOnItemClickListener { data ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.DATA_FROM_LIST_TO_DETAILS, data)

            findNavController().navigate(R.id.action_bookListFragment_to_bookDetailFragment, bundle)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BookListFragment().apply {}
    }
}