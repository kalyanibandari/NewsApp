package com.example.newsapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.model.News
import com.example.newsapp.service.RetrofitInstance
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    lateinit var adapter: NewsAdapter
    lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        checkPermission()
    }

    fun checkPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                displayNews()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1
                )
            }
        }
    }

    fun displayNews(){
        newsRecyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        newsRecyclerView.layoutManager = layoutManager

        val getNewsDataService = RetrofitInstance.service
        val requestCall = getNewsDataService.getNewsList("bitcoin", "2020-07-07",
            "publishedAt", "8a8e48de7aba4773b389643a11f10236")

        requestCall.enqueue(object : Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {
                adapter = NewsAdapter(this@MainActivity, response.body()?.articles){articles ->
                  //  Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_SHORT).show()
                    val webViewIntent = Intent(this@MainActivity, WebViewActivity::class.java)
                    webViewIntent.putExtra("url", articles.url)
                    startActivity(webViewIntent)
                }
                adapter.notifyDataSetChanged()
                progressbar.visibility = View.GONE
                newsRecyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            displayNews()
        } else {
            checkPermission()
        }
    }
}

