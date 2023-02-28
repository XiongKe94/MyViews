package com.carl.views.textview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.carl.views.R

/**
 * 百分比显示文本View
 * @author xiongke
 * @Date 2023/02/23
 */
class GradualTextView @JvmOverloads constructor(val mContext: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(mContext, attrs, defStyleAttr) {
    private var mText: String = ""
    private var bgdChangeColor = 0
    private var bgdOriginColor = 0
    private var mTextSize = sp2px(18f)
    private var mTextOriginColor = 0
    private var mTextChangeColor = 0
    private var mProgress = 0
    private var mprogressMax = 0
    private var mPaint: Paint? = null
    private var measuredWidth1: Int = 0
    private var measuredHeight1: Int = 0

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.GradualTextView)
        mText = ta.getString(R.styleable.GradualTextView_text) ?: ""
        mTextSize = ta.getDimensionPixelSize(R.styleable.GradualTextView_text_size, mTextSize)
        bgdOriginColor = ta.getColor(R.styleable.GradualTextView_bgd_origin_color, bgdOriginColor)
        bgdChangeColor = ta.getColor(R.styleable.GradualTextView_bgd_change_color, bgdChangeColor)
        mTextOriginColor = ta.getColor(R.styleable.GradualTextView_text_origin_color, mTextOriginColor)
        mTextChangeColor = ta.getColor(R.styleable.GradualTextView_text_change_color, mTextChangeColor)
        mProgress = ta.getInt(R.styleable.GradualTextView_progress, mProgress)
        mprogressMax = ta.getInt(R.styleable.GradualTextView_progress_max, mprogressMax)
        ta.recycle()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.textSize = mTextSize.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        measuredWidth1 = getMeasuredWidth()
        measuredHeight1 = getMeasuredHeight()
        val widthFloat = measuredWidth1.toFloat() / mprogressMax.toFloat()
        drawText(canvas, mTextOriginColor, bgdOriginColor, 0f, measuredWidth1)
        drawText(canvas, mTextChangeColor, bgdChangeColor, widthFloat * mProgress, measuredWidth1)
    }

    private fun drawText(canvas: Canvas, color: Int, bgdColor: Int, startX: Float, endX: Int) {
        val mRect = RectF()
        mRect.top = 0f
        mRect.bottom = measuredWidth1.toFloat()
        mRect.left = startX
        mRect.right = endX.toFloat()
        mPaint!!.color = bgdColor
        canvas.drawRect(mRect, mPaint!!)
        canvas.save()
        mPaint!!.color = color
        canvas.clipRect(startX, 0f, endX.toFloat(), measuredHeight1.toFloat())
        val textWidth = mPaint!!.measureText(mText)
        canvas.drawText(mText, measuredWidth1 / 2 - textWidth / 2,
                measuredHeight1 / 2 - (mPaint!!.descent() + mPaint!!.ascent()) / 2, mPaint!!)
        canvas.restore()
    }

    fun setmText(mText: String) {
        this.mText = mText
        invalidate()
    }

    fun setmProgress(mProgress: Int) {
        this.mProgress = mProgress
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val obj = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = obj
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> result = obj
        }
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, obj) else result
        return result + paddingTop + paddingBottom
    }

    private fun measureWidth(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val obj = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = obj
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> result = obj
        }
        result = if (mode == MeasureSpec.AT_MOST) Math.min(result, obj) else result
        return result + paddingLeft + paddingRight
    }

    private fun sp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, resources.displayMetrics).toInt()
    }
}