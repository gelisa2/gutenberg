package com.example.gutenbergbooks.presenter.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.gutenbergbooks.R
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
        initTransitions()
        getData()
        initView()
        openBookReadingPage()
    }


    private fun initTransitions() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_element_transition)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun getData() {
        arguments?.let { bundle ->
            bookDetails = ParcelableUtils.getParcelableCompat(
                bundle,
                Constants.DATA_FROM_LIST_TO_DETAILS,
                BookListResponse::class.java
            )
                ?: return@let

            binding.bookImage.transitionName = "sharedImage_${bookDetails.id}"
        }
    }

    private fun initView() {
        Glide.with(binding.root)
            .load(bookDetails.formats?.bookCover)
            .fitCenter()
            .placeholder(R.drawable.no_image_icon)
            .apply(bitmapTransform(BlurTransformation(100)))
            .into(binding.backgroundImage)

        Glide.with(binding.root)
            .load(bookDetails.formats?.bookCover)
            .placeholder(R.drawable.no_image_icon)
            .into(binding.bookImage)

        binding.titleValue.text = bookDetails.title
        binding.authorValue.text = bookDetails.authors?.first()?.name
        binding.bookshelvesValue.text = bookDetails.bookshelves?.joinToString()
        binding.subjectValue.text = bookDetails.subjects?.joinToString()
        binding.languageValue.text = bookDetails.languages?.first().toString()
        binding.downloadCount.text = bookDetails.downloadCount.toString()
    }

    private fun openBookReadingPage() {
        binding.startReadingButton.setOnClickListener {
            val url = bookDetails.formats?.eBook
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            activity?.startActivity(intent)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BookDetailFragment().apply {}
    }
}