package com.thiishy.movlist.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thiishy.movlist.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("URL_FILME")

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(if (url.isNullOrBlank()) "https://youtube.com" else url)

        binding.fabVoltar.setOnClickListener {
            finish()
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}