package com.example.gutenbergbooks.presenter.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.databinding.FragmentBookListBinding
import com.example.gutenbergbooks.presenter.adapters.BookListAdapter
import com.example.gutenbergbooks.presenter.viewmodel.BookListViewModel
import com.example.utils.Constants
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class BookListFragment : Fragment() {

    private val binding by lazy {
        FragmentBookListBinding.inflate(layoutInflater)
    }

    private val bookListAdapter = BookListAdapter()

    private val viewmodel: BookListViewModel by viewModels()

    private var page = 1
    private var isDataLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBookList()
        observeBookList()
    }

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
        initRecyclerClickListener()
        addRecyclerScrollListener()
    }

    private fun getBookList() {
        lifecycleScope.launch {
            viewmodel.getBookList(page)
        }
    }

    private fun observeBookList() {
        lifecycleScope.launch {
            viewmodel.bookListFlow.collect {
                when (it) {
                    is Resource.Loading -> {
                        showProgressBar(true)
                    }

                    is Resource.Success -> {
                        showProgressBar(false)
                        it.data?.results?.let {
                            bookListAdapter.addBooks(it)
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                        showProgressBar(false)
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

    private fun showProgressBar(show: Boolean) {
        isDataLoading = show
        if (show) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    private fun initRecyclerClickListener() {
        bookListAdapter.setOnItemClickListener { data, image ->
            val bundle = Bundle()
            bundle.putParcelable(Constants.DATA_FROM_LIST_TO_DETAILS, data)

            image.transitionName = "sharedImage_${data.id}"
            val extras = FragmentNavigatorExtras(
                image to "sharedImage_${data.id}"
            )

            findNavController().navigate(
                R.id.action_bookListFragment_to_bookDetailFragment,
                bundle,
                null,
                extras
            )
        }
    }

    private fun addRecyclerScrollListener() {
        binding.bookRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch {
                        if (!isDataLoading) {
                            page++
                            viewmodel.getBookList(page)
                        }
                    }
                }
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BookListFragment().apply {}
    }
}