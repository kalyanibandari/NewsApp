package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.News

class NewsAdapter(val context : Context, val news : List<News>) : RecyclerView.Adapter<NewsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return news.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindNews(news[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle = itemView.findViewById<TextView>(R.id.title_text)
        val newsDescription = itemView.findViewById<TextView>(R.id.description_text)

        fun bindNews(news: News){
            /*newsTitle.text = news.status
            newsDescription.text = news.totalResults*/
        }
    }

}