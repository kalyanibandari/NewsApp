package com.example.newsapp.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R


class WebViewActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val myWebView = findViewById<WebView>(R.id.webView)
        progressBar = findViewById(R.id.webViewProgressBar)
        val url = intent.getStringExtra("url")
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = WebViewClient()
        myWebView.setWebViewClient(myWebClient())
        myWebView.loadUrl(url)
    }

    inner class myWebClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar?.visibility = View.GONE
        }
    }
}