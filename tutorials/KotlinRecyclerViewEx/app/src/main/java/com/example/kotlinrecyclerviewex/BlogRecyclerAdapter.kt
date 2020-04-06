package com.example.kotlinrecyclerviewex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinrecyclerviewex.models.BlogPost
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class BlogRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private  var items: List<BlogPost> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return BlogViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false)
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when(holder) {
      is BlogViewHolder -> {
        holder.bind(items.get(position))
      }
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun submitList(blogList: List<BlogPost>) {
    items = blogList
  }

  class BlogViewHolder constructor(
    itemView: View
  ): RecyclerView.ViewHolder(itemView) {
    val blog_image = itemView.blog_image
    val blog_title = itemView.blog_title
    val blog_author = itemView.blog_author

    fun bind(blogPost: BlogPost) {
      blog_title.setText(blogPost.title)
      blog_author.setText(blogPost.username)

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
        .load(blogPost.image)
        .into(blog_image)
    }
  }
}