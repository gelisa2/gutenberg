package com.example.gutenbergbooks.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.databinding.BookListItemBinding
import com.example.gutenbergbooks.domain.model.BookListResponse

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList = ArrayList<BookListResponse>()

    private var clickListener: ((item: BookListResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }


    inner class BookViewHolder(private val binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookListResponse) {
            binding.apply {
                if(item.authors.isNotEmpty()) {
                    author.text = item.authors.first().name
                }
                title.text = item.title
                subjects.text = item.subjects?.joinToString(",")
                initImage(item.formats.bookCover, binding)

                itemView.setOnClickListener {
                    clickListener?.invoke(item)
                }
            }
        }


    }

    fun setOnItemClickListener(clickListener: ((item: BookListResponse) -> Unit)?) {
        this.clickListener = clickListener
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = bookList[position]
        holder.bind(item)
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

    class BookDiffUtils : DiffUtil.ItemCallback<BookListResponse>() {
        override fun areItemsTheSame(oldItem: BookListResponse, newItem: BookListResponse): Boolean {
            // Compare unique IDs to check if items are the same
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookListResponse, newItem: BookListResponse): Boolean {
            // Compare the entire content to check if items are equal
            return oldItem == newItem
        }
    }

}