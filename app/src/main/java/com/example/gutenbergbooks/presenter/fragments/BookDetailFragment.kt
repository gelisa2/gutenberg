package com.example.gutenbergbooks.presenter.fragments

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.databinding.BookListItemBinding
import com.example.gutenbergbooks.databinding.FragmentBookDetailBinding
import com.example.gutenbergbooks.domain.model.BookListResponse
import com.example.utils.Constants
import com.example.utils.ParcelableUtils
import jp.wasabeef.glide.transformations.BlurTransformation


class BookDetailFragment : Fragment() {

    private val binding by lazy {
        FragmentBookDetailBinding.inflate(layoutInflater)
    }

    private lateinit var bookDetails: BookListResponse


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()

    }


    private fun init() {
        getData()
        initView()
    }

    private fun getData() {
        arguments?.let { bundle ->
            bookDetails = ParcelableUtils.getParcelableCompat(
                bundle,
                Constants.DATA_FROM_LIST_TO_DETAILS,
                BookListResponse::class.java
            )
                ?: return@let
        }
    }

    private fun initView() {
        Glide.with(binding.root)
            .load(bookDetails.formats.bookCover)
            .fitCenter()
            .apply(bitmapTransform(BlurTransformation(100)))
            .into(binding.backgroundImage)

        Glide.with(binding.root)
            .load(bookDetails.formats.bookCover)
            .into(binding.bookImage)

        binding.titleValue.text = bookDetails.title
        binding.authorValue.text = bookDetails.authors.first().name
        binding.bookshelvesValue.text = bookDetails.bookshelves?.joinToString()
        binding.subjectValue.text = bookDetails.subjects?.joinToString()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            BookDetailFragment().apply {}
    }
}