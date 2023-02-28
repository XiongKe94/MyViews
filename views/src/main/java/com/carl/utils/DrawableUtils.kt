package com.carl.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

/**
 * @author xiongke
 * @date 2018/7/14
 */
object DrawableUtils {
    fun getVectorDrawable(mContext: Context, @DrawableRes vectorRid: Int): VectorDrawableCompat {
        return VectorDrawableCompat.create(mContext.resources, vectorRid, mContext.theme)!!
    }

    fun getDyeDrawable(drawable: Drawable, @ColorInt dyeColor: Int): Drawable {
        return DrawableCompat.wrap(drawable.mutate()).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DrawableCompat.setTint(this, dyeColor)
            } else {
                this.setColorFilter(dyeColor, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    /**
     * Return a tint drawable
     * SVG图片使用 DrawableUtils.getVectorDrawable(Context,int)
     * @param drawable
     * @param colorInt
     * @return
     */
    fun tintImageViewSvgXmlDrawable(imageView: ImageView, drawable: Drawable, @ColorInt colorInt: Int) {
        drawable.apply {
            imageView.setImageDrawable(this)
            getDyeDrawable(this, colorInt)
        }
    }
}