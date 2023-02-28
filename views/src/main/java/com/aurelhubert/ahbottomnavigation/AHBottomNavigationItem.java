package com.aurelhubert.ahbottomnavigation;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * AHBottomNavigationItem
 * The item is display in the AHBottomNavigation layout
 */
public class AHBottomNavigationItem {
    private String title = "";
    private Drawable checkDrawable;
    private Drawable unCheckDrawable;
    @ColorInt
    private int color = Color.GRAY;

    public AHBottomNavigationItem(@NonNull String title, @Nullable Drawable checkDrawable) {
        this.title = title;
        this.checkDrawable = checkDrawable;
        this.unCheckDrawable = checkDrawable;
    }

    public AHBottomNavigationItem(@NonNull String title, @Nullable Drawable checkDrawable, @ColorInt int bgAnimColor) {
        this.title = title;
        this.checkDrawable = checkDrawable;
        this.unCheckDrawable = checkDrawable;
        this.color = bgAnimColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public void setCheckDrawable(Drawable checkDrawable) {
        this.checkDrawable = checkDrawable;
    }

    public void setUnCheckDrawable(Drawable unCheckDrawable) {
        this.unCheckDrawable = unCheckDrawable;
    }

    public Drawable getUnCheckDrawable() {
        return unCheckDrawable;
    }

    public Drawable getCheckDrawable() {
        return checkDrawable;
    }
}
