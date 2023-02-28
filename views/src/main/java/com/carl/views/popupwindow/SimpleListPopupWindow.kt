package com.carl.views.popupwindow

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import com.carl.utils.Utils

/**
 * @author xiongke
 * @date 2018/11/29
 */
class SimpleListPopupWindow(private val mContext: Context) : BaseListPopupWindow(mContext) {
    private var anchorView1: View? = null
    private var listArray: List<String>? = null
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClick(position: Int, itemString: String)
    }

    override fun initPopupWindow() {
        this.width = (Utils.getScreenWidth(mContext) * 0.55f).toInt()
        this.setContentWidth(this.width)
        anchorView1?.let {
            this.anchorView = it
        }
        listArray?.apply {
            this@SimpleListPopupWindow.setAdapter(ArrayAdapter(mContext, android.R.layout.simple_list_item_1, this))
            this@SimpleListPopupWindow.setOnItemClickListener { _, _, position, _ -> onItemClickCallback?.onItemClick(position, this[position]) }
        }
    }

    fun setPopupWindowParam(anchorView: View, listArray: List<String>, onItemClickCallback: OnItemClickCallback) {
        this.anchorView1 = anchorView
        this.listArray = listArray
        this.onItemClickCallback = onItemClickCallback
        this.initPopupWindow()
    }
}