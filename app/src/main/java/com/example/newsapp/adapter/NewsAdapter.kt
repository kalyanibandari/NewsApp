package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.R
import com.example.newsapp.model.Articles

class NewsAdapter(val context: Context, val articles: List<Articles>?, val itemClick: (Articles) -> Unit) : RecyclerView.Adapter<NewsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return articles?.count()!!
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindNews(articles?.get(position)!!)
    }

    inner class Holder(itemView: View, val itemClick: (Articles) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val articlesTitle = itemView.findViewById<TextView>(R.id.title_text)
        val articlesImage = itemView.findViewById<ImageView>(R.id.news_image)

        fun bindNews(articles: Articles){
            articlesTitle.text = articles.title
            Glide.with(context).load(articles.urlToImage)
                .apply(RequestOptions().centerCrop())
                .into(articlesImage)

            itemView.setOnClickListener { itemClick(articles) }

        }
    }

}