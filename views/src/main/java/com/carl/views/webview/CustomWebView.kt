package com.carl.views.webview

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewParent
import android.webkit.*
import android.widget.AbsListView
import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager

/**
 * 这里只是使用AgentWebView里面的webview和原生一样使用方法
 * @author xiongke
 * @date 2019/5/10
 */
class CustomWebView @JvmOverloads constructor(val mContext: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(mContext, attrs, defStyleAttr) {
    private var onScrollListener: OnScrollListener? = null
    private var webViewCallBack: WebViewClientCallBack? = null
    private var webChromeClientCallBack: WebChromeClientCallBack? = null
    private var isShowErrorBlankPage = false
    private var parentViewParent: ViewParent? = null
    private var parentColorSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isFindSwipeRefreshLayout = false
    private var isFindScrollView = false

    init {
        initCustomWebView()
    }

    companion object {
        private const val blankUrl = "about:blank"
    }

    fun setWebViewClientCallBack(webViewCallBack: WebViewClientCallBack) {
        this.webViewCallBack = webViewCallBack
    }

    fun setWebChromeClientCallBack(webChromeClientCallBack: WebChromeClientCallBack) {
        this.webChromeClientCallBack = webChromeClientCallBack
    }

    fun setOnScrollListener(listener: OnScrollListener) {
        this.onScrollListener = listener
    }

    fun setShowErrorBlankPage(isShowErrorBlankPage: Boolean) {
        this.isShowErrorBlankPage = isShowErrorBlankPage
    }

    interface OnScrollListener {
        fun onScrollUp()
        fun onScrollDown()
    }

    private fun initCustomWebView() {
        initWebSettings()
        this.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (!url.isNullOrEmpty()) {
                    webViewCallBack?.let {
                        return it.shouldUrlLoadingCall(url)
                    }
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webViewCallBack?.onPageStart()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webViewCallBack?.onPageSuccess()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                webViewCallBack?.onPageError()
                if (isShowErrorBlankPage) {
                    view?.loadUrl(blankUrl)
                }
            }
        }
        this.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                val tempTitle = title ?: return
                webChromeClientCallBack?.onReceivedTitle(tempTitle)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                webChromeClientCallBack?.onProgressChanged(newProgress)
            }
        }
    }

    override
    fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (t > oldt) {
            onScrollListener?.onScrollDown()
        } else if (t < oldt) {
            onScrollListener?.onScrollUp()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            getParentViewParent()?.requestDisallowInterceptTouchEvent(true)
            updateSwipeRefreshLayoutEnable(false)
        }
        return super.onTouchEvent(event)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (clampedX) {
            getParentViewParent()?.requestDisallowInterceptTouchEvent(false)
        } else {
            updateSwipeRefreshLayoutEnable(true)
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    override fun destroy() {
        try {
            this.stopLoading()
            this.removeAllViews()
            super.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getParentViewParent(): ViewParent? {
        if (!isFindScrollView) {
            isFindScrollView = true
            parentViewParent = findViewParentIfNeeds(this)
        }
        return parentViewParent
    }

    private fun findViewParentIfNeeds(tag: View): ViewParent? {
        val parent = tag.parent
        return if (parent is ViewPager ||
                parent is AbsListView ||
                parent is ScrollView ||
                parent is HorizontalScrollView ||
                parent is GridView) {
            parent
        } else {
            if (parent is View) {
                findViewParentIfNeeds(parent)
            } else {
                parent
            }
        }
    }

    private fun findParentSwipeRefreshLayout(tag: View): SwipeRefreshLayout? {
        return when (val parent = tag.parent) {
            is SwipeRefreshLayout -> {
                parent
            }
            is View -> {
                findParentSwipeRefreshLayout(parent)
            }
            else -> {
                null
            }
        }
    }

    private fun updateSwipeRefreshLayoutEnable(isEnable: Boolean) {
        if (!isFindSwipeRefreshLayout) {
            isFindSwipeRefreshLayout = true
            parentColorSwipeRefreshLayout = findParentSwipeRefreshLayout(this)
        }
        val view = parentColorSwipeRefreshLayout ?: return
        if (view.isEnabled != isEnable && !view.isRefreshing) {
            view.isEnabled = isEnable
        }
    }
}