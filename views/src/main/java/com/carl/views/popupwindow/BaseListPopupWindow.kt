package com.carl.views.popupwindow

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.ListPopupWindow

/**
 * @author xiongke
 * @date 2018/11/29
 */
abstract class BaseListPopupWindow : ListPopupWindow {
    constructor(mContext: Context) : super(mContext) {
        this.initial()
    }

    constructor(mContext: Context, attrs: AttributeSet?) : super(mContext, attrs) {
        this.initial()
    }

    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr) {
        this.initial()
    }

    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(mContext, attrs, defStyleAttr, defStyleRes) {
        this.initial()
    }

    private fun initial() {
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.anchorView = anchorView
        this.isModal = true
    }

    protected abstract fun initPopupWindow()
}