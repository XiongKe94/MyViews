package com.carl.views.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class TagView extends FrameLayout{
    public TagView(Context context) {
        super(context);
    }
    public View getTagView() {
        return getChildAt(0);
    }
}
