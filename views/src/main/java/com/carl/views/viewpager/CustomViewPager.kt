package com.carl.views.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author Xiong Ke
 * @date 2017/12/29
 */

open class CustomViewPager @JvmOverloads constructor(private val mContext: Context, var attrs: AttributeSet? = null)
    : ViewPager(mContext, attrs) {
    private var noScroll = false//是否可以不滑动
    private var smoothScroll = true//是否平滑滚动，false

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    fun setSmoothScroll(smoothScroll: Boolean) {
        this.smoothScroll = smoothScroll
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return if (noScroll) {
                false
            } else {
                super.onTouchEvent(ev)
            }
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return if (noScroll) {
                false
            } else {
                super.onInterceptTouchEvent(ev)
            }
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, smoothScroll)
    }
}
