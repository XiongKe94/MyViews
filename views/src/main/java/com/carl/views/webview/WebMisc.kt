package com.carl.views.webview

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import java.io.File

/**
 * @author xiongke
 * @date 2020/10/23
 */
open class WebChromeClientCallBack {
    open fun onReceivedTitle(title: String) = Unit
    open fun onProgressChanged(newProgress: Int) = Unit
}

open class WebViewClientCallBack {
    open fun shouldUrlLoadingCall(url: String) = false
    open fun onPageStart() = Unit
    open fun onPageSuccess() = Unit
    open fun onPageError() = Unit
}

@SuppressLint("SetJavaScriptEnabled")
fun CustomWebView.initWebSettings(): WebSettings {
    val mContext = this.context
    this.isHorizontalScrollBarEnabled = false
    this.isVerticalScrollBarEnabled = false
    return this.settings.apply {
        this.javaScriptCanOpenWindowsAutomatically = true
        this.pluginState = WebSettings.PluginState.ON_DEMAND
        this.cacheMode = WebSettings.LOAD_NO_CACHE
        this.javaScriptEnabled = true
        this.setSupportZoom(true)
        this.builtInZoomControls = false
        this.savePassword = false
        this.cacheMode = WebSettings.LOAD_DEFAULT
        this.setRenderPriority(WebSettings.RenderPriority.HIGH)
        this.textZoom = 100
        this.databaseEnabled = true
        this.setAppCacheEnabled(true)
        this.loadsImagesAutomatically = true
        this.setSupportMultipleWindows(false)
        this.blockNetworkImage = false
        this.allowFileAccess = true
        this.allowFileAccessFromFileURLs = false
        this.allowUniversalAccessFromFileURLs = false
        this.javaScriptCanOpenWindowsAutomatically = true
        this.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        this.loadWithOverviewMode = true
        this.useWideViewPort = true
        this.domStorageEnabled = true
        this.setNeedInitialFocus(true)
        this.defaultTextEncodingName = "utf-8"
        this.defaultFontSize = 16
        this.minimumFontSize = 12
        this.setGeolocationEnabled(true)
        val dir = File(mContext.cacheDir, "WebView").absolutePath
        this.setGeolocationDatabasePath(dir)
        this.databasePath = dir
        this.setAppCachePath(dir)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        //this.userAgentString = "${this.userAgentString} ${BuildConfig.FolderFileName}/${BuildConfig.VERSION_NAME}"
    }
}