package com.example.gutenbergbooks.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.databinding.BookListItemBinding
import com.example.gutenbergbooks.domain.model.BookListResponse

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList = ArrayList<BookListResponse>()

    private var clickListener: ((item: BookListResponse, imageView: ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }


    inner class BookViewHolder(private val binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookListResponse, pos: Int) {
            binding.apply {
                if (item.authors?.isNotEmpty() == true) {
                    author.text = item.authors.first().name
                }
                title.text = item.title
                subjects.text = item.subjects?.joinToString(",")
                initImage(item.formats?.bookCover ?: return, this)
                itemView.setOnClickListener {
                    clickListener?.invoke(item, coverImage)
                }
            }
        }


    }

    fun setOnItemClickListener(clickListener: ((item: BookListResponse, transitionImage: ImageView) -> Unit)?) {
        this.clickListener = clickListener
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = bookList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = bookList.size

    private fun initImage(imageUrl: String, binding: BookListItemBinding) {
        Glide.with(binding.root)
            .load(imageUrl)
            .placeholder(R.drawable.no_image_icon)
            .into(binding.coverImage)
    }

    fun addBooks(newBooks: List<BookListResponse>) {
        val startPosition = bookList.size
        bookList.addAll(newBooks)
        notifyItemRangeInserted(startPosition, newBooks.size)
    }

}