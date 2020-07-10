package com.example.newsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.model.News
import com.example.newsapp.service.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        newsRecyclerView.layoutManager = layoutManager

        val getNewsDataService = RetrofitInstance.service
        val requestCall = getNewsDataService.getNewsList("bitcoin", "2020-07-07",
            "publishedAt", "8a8e48de7aba4773b389643a11f10236")

        requestCall.enqueue(object : Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {

                adapter = NewsAdapter(this@MainActivity, response.body()?.articles){articles ->
                    Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_SHORT).show()
                    val webViewIntent = Intent(this@MainActivity, WebViewActivity::class.java)
                    webViewIntent.putExtra("url", articles.url)
                    startActivity(webViewIntent)

                }
                adapter.notifyDataSetChanged()
                newsRecyclerView.adapter = adapter

                Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
                //status_text.text = response.body()?.status

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}

