package com.carl.views.divideritem

import androidx.annotation.ColorInt


/**
 * @author xiongke
 * @date 2018/8/9
 */
class SideLine constructor(@ColorInt var color: Int, var widthPx: Int, var startPaddingPx: Int = 0, var endPaddingPx: Int = 0) {
    constructor(@ColorInt color: Int, widthPx: Int) : this(color, widthPx, 0, 0)
}
