package com.ken.taipeitourtestproject.screen.attractionweb

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.base.BaseFragment
import com.ken.taipeitourtestproject.databinding.FragmentAttractionWebBinding
import com.ken.taipeitourtestproject.module.ext.finishScreen

class AttractionWebFragment: BaseFragment(R.layout.fragment_attraction_web) {

    companion object {
        const val ARG_URL_KEY = "ARG_URL_KEY"
    }

    private var _binding: FragmentAttractionWebBinding? = null
    private val binding get() = _binding!!

    private val targetUrl by lazy {
        arguments?.getString(ARG_URL_KEY) ?: ""
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAttractionWebBinding.bind(view)

        binding.closeImageView.setOnClickDebounce {
            finishScreen()
        }

        binding.showUrlTextView.text = targetUrl

        binding.contentWebView.apply {
            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = false
                useWideViewPort = true
                domStorageEnabled = true
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    val isLoading = newProgress < 100
                    _binding?.loadingProgress?.isVisible = isLoading
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val requestUrl = request?.url?.toString() ?: return false
                    return try {
                        when {
                            //當使用者點擊網頁的其他連結 使用外部browser去開啟
                            requestUrl.isNotEmpty() && requestUrl != targetUrl -> {
                                openBrowser(requestUrl)
                                true
                            }
                            else -> {
                                super.shouldOverrideUrlLoading(view, request)
                            }
                        }
                    } catch (e: Exception) {
                        true
                    }
                }
            }
        }

        binding.contentWebView.loadUrl(targetUrl)
    }

    private fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
            this.data = Uri.parse(url)
            this.addCategory(Intent.CATEGORY_BROWSABLE)
        }
        requireContext().startActivity(browserIntent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}