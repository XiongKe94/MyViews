package com.carl.views.textview

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatTextView
import java.util.*

/**
 * @author xiongke
 * @date 2020/10/27
 */
class CountDownNumberView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val INT_REGEX = "%1$01.0f" //不保留小数，整数
        private const val FLOAT_REGEX = "%1$01.2f" //保留2位小数
    }

    enum class NumberType(val regex: String) {
        Long(INT_REGEX),
        Float(FLOAT_REGEX)
    }

    private var numberType = NumberType.Long

    fun showNumberWithAnimation(number: Long,
                                intervalNumber: Long = 0L,
                                isAnim: Boolean = true,
                                isOrderDesc: Boolean = false) {
        this.numberType = NumberType.Long
        if (!isAnim || number <= 0L || intervalNumber == 0L || intervalNumber > number ||
                number.toString().length >= 8) {
            setMyTextL(number)
            return
        }
        if (isOrderDesc) {
            showAnim((number + intervalNumber).toFloat(), number.toFloat())
        } else {
            showAnim((number - intervalNumber).toFloat(), number.toFloat())
        }
    }

    fun showNumberWithAnimation(number: Float,
                                intervalNumber: Float = 0f,
                                isAnim: Boolean = true,
                                isOrderDesc: Boolean = false) {
        this.numberType = NumberType.Float
        if (!isAnim || number <= 0f || intervalNumber == 0f || intervalNumber > number) {
            setMyTextF(number)
            return
        }
        if (isOrderDesc) {
            showAnim(number + intervalNumber, number)
        } else {
            showAnim(number - intervalNumber, number)
        }
    }

    private fun showAnim(startNumber: Float, endNumber: Float) {
        ObjectAnimator.ofFloat(this, "number", startNumber, endNumber).apply {
            this.duration = 900
            this.interpolator = AccelerateDecelerateInterpolator()
            this.start()
        }
    }

    @Keep
    private fun setNumber(numberF: Float) {
        setMyTextF(numberF)
    }

    private fun setMyTextF(numberF: Float) {
        this.text = if (numberF != 0f) {
            String.format(Locale.ENGLISH, numberType.regex, numberF)
        } else {
            "0"
        }
    }

    private fun setMyTextL(numberF: Long) {
        this.text = if (numberF != 0L) {
            numberF.toString()
        } else {
            "0"
        }
    }
}