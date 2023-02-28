package com.carl.views.imageview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.carl.views.R
import com.carl.utils.DrawableUtils

/**
 * 折叠箭头
 * @author xiongke
 * @date 2020/8/24
 */
class ArrowImageView @JvmOverloads constructor(private val mContext: Context, var attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatImageView(mContext, attrs, defStyleAttr) {
    private val rightDrawable by lazy { DrawableUtils.getVectorDrawable(mContext, R.drawable.ic_arrow_right_24dp_svg) }
    private val bottomDrawable by lazy { DrawableUtils.getVectorDrawable(mContext, R.drawable.ic_arrow_below_24dp_svg) }
    private val topDrawable by lazy { DrawableUtils.getVectorDrawable(mContext, R.drawable.ic_arrow_top_24dp_svg) }
    private val defaultColor by lazy { ContextCompat.getColor(mContext, R.color.black_alpha_30) }
    @ColorInt
    private var arrowColor = -1

    enum class State(val value: Int) {
        Right(1),
        Bottom(2),
        Top(3)
    }

    init {
        initView()
    }

    private fun initView() {
        var state = State.Right
        var stateValue = -1
        attrs?.apply {
            val typedArray = mContext.obtainStyledAttributes(this, R.styleable.ArrowImageView)
            stateValue = typedArray.getInt(R.styleable.ArrowImageView_state, -1)
            arrowColor = typedArray.getColor(R.styleable.ArrowImageView_arrowColor, defaultColor)
            typedArray.recycle()
        }
        State.values().iterator().forEach {
            if (it.value == stateValue) {
                state = it
            }
        }
        setState(state)
    }

    fun setState(state: State) {
        getStateDraw(state).apply {
            this@ArrowImageView.setImageDrawable(this)
            DrawableUtils.getDyeDrawable(this, arrowColor)
        }
    }

    private fun getStateDraw(state: State) = when (state) {
        State.Bottom -> {
            bottomDrawable
        }
        State.Right -> {
            rightDrawable
        }
        State.Top -> {
            topDrawable
        }
    }
}