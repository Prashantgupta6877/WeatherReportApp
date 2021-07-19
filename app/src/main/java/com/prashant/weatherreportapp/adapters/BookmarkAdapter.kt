package com.prashant.weatherreportapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.prashant.weatherreportapp.R
import com.prashant.weatherreportapp.database.models.ModelBookmark

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */
class BookmarkAdapter(
    private var bookmarks: List<ModelBookmark>,
    private val bookmarkItemClickListener: BookmarkItemClickListener
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.txtCity.text = bookmark.city
        holder.imgDelete.setOnClickListener {
            bookmarkItemClickListener.onDeleteClicked(bookmark = bookmark)
        }
        holder.txtDetails.setOnClickListener {
            bookmarkItemClickListener.onItemClicked(it, bookmark = bookmark)
        }
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCity: MaterialTextView = itemView.findViewById(R.id.txtCity)
        val txtDetails: MaterialTextView = itemView.findViewById(R.id.txtDetails)
        val imgDelete: AppCompatImageView = itemView.findViewById(R.id.imgRemove)
    }

    interface BookmarkItemClickListener {
        fun onItemClicked(view: View, bookmark: ModelBookmark)
        fun onDeleteClicked(bookmark: ModelBookmark)
    }
}
