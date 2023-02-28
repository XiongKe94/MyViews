package com.carl.utils

import android.content.Context

object Utils {
    @JvmStatic
    fun dip2px(mContext: Context, dpValue: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    @JvmStatic
    fun px2dip(mContext: Context, pxValue: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    @JvmStatic
    fun getScreenWidth(mContext: Context): Int {
        return mContext.resources.displayMetrics.widthPixels
    }
}
