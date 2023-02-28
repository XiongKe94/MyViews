package com.carl.views.divideritem

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

abstract class DividerItemDecoration : RecyclerView.ItemDecoration() {
    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    abstract fun getDivider(itemPosition: Int): Divider

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //left, top, right, bottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val itemPosition = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            val divider = getDivider(itemPosition)
            divider.leftSideLine?.let {
                val leftLineWidthPx = it.widthPx
                if (leftLineWidthPx > 0) {
                    val startPaddingPx = it.startPaddingPx
                    val endPaddingPx = it.endPaddingPx
                    drawChildLeftVertical(child, c, it.color, leftLineWidthPx, startPaddingPx, endPaddingPx)
                }
            }
            divider.topSideLine?.let {
                val topLineWidthPx = it.widthPx
                if (topLineWidthPx > 0) {
                    val startPaddingPx = it.startPaddingPx
                    val endPaddingPx = it.endPaddingPx
                    drawChildTopHorizontal(child, c, it.color, topLineWidthPx, startPaddingPx, endPaddingPx)
                }
            }
            divider.rightSideLine?.let {
                val rightLineWidthPx = it.widthPx
                if (rightLineWidthPx > 0) {
                    val startPaddingPx = it.startPaddingPx
                    val endPaddingPx = it.endPaddingPx
                    drawChildRightVertical(child, c, it.color, rightLineWidthPx, startPaddingPx, endPaddingPx)
                }
            }
            divider.bottomSideLine?.let {
                val bottomLineWidthPx = it.widthPx
                if (bottomLineWidthPx > 0) {
                    val startPaddingPx = it.startPaddingPx
                    val endPaddingPx = it.endPaddingPx
                    drawChildBottomHorizontal(child, c, it.color, bottomLineWidthPx, startPaddingPx, endPaddingPx)
                }
            }
        }
    }

    private fun drawChildBottomHorizontal(child: View, c: Canvas, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        val leftPadding = if (startPaddingPx <= 0) {
            //padding<0当作==0处理
            //上下左右默认分割线的两头都出头一个分割线的宽度，避免十字交叉的时候，交叉点是空白
            -lineWidthPx
        } else {
            startPaddingPx
        }
        val rightPadding = if (endPaddingPx > 0) {
            -endPaddingPx
        } else {
            lineWidthPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val top = child.bottom + params.bottomMargin
        val bottom = top + lineWidthPx
        mPaint.color = color

        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

    }

    private fun drawChildTopHorizontal(child: View, c: Canvas, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        val leftPadding = if (startPaddingPx <= 0) {
            -lineWidthPx
        } else {
            startPaddingPx
        }
        val rightPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val bottom = child.top - params.topMargin
        val top = bottom - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

    }

    private fun drawChildLeftVertical(child: View, c: Canvas, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        val topPadding = if (startPaddingPx <= 0) {
            -lineWidthPx
        } else {
            startPaddingPx
        }
        val bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val right = child.left - params.leftMargin
        val left = right - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildRightVertical(child: View, c: Canvas, @ColorInt color: Int, lineWidthPx: Int, startPaddingPx: Int, endPaddingPx: Int) {
        val topPadding = if (startPaddingPx <= 0) {
            -lineWidthPx
        } else {
            startPaddingPx
        }
        val bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
                .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val left = child.right + params.rightMargin
        val right = left + lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val divider = getDivider(itemPosition)
        val left = divider.leftSideLine?.widthPx ?: 0
        val top = divider.topSideLine?.widthPx ?: 0
        val right = divider.rightSideLine?.widthPx ?: 0
        val bottom = divider.bottomSideLine?.widthPx ?: 0
        outRect.set(left, top, right, bottom)
    }
}