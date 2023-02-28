package com.carl.views.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.carl.views.R;
import com.carl.utils.Utils;

public class TagFlowLayout<T> extends FlowLayout {
    private TagAdapter<T> mTagAdapter;
    private int mMaxLines = 0;
    private int itemSpace = 0;
    private int lineSpace = 0;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int defaultMargin = Utils.dip2px(context, 5);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mMaxLines = ta.getInt(R.styleable.TagFlowLayout_max_lines, Integer.MAX_VALUE);
        itemSpace = ta.getDimensionPixelSize(R.styleable.TagFlowLayout_item_space, defaultMargin);
        lineSpace = ta.getDimensionPixelSize(R.styleable.TagFlowLayout_line_space, defaultMargin);
        ta.recycle();
        setMaxLines(mMaxLines);
    }

    public void setAdapter(TagAdapter<T> adapter) {
        mTagAdapter = adapter;
        changeAdapter();
    }

    private void changeAdapter() {
        Context mContext = getContext();
        removeAllViews();
        for (int i = 0; i < mTagAdapter.getCount(); i++) {
            T t = mTagAdapter.getItem(i);
            View tagView = mTagAdapter.getView(i, t);
            TagView tagViewContainer = new TagView(mContext);
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.
                        WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                if (mMaxLines == 1) {
                    lp.setMargins(itemSpace, 0, itemSpace, 0);
                } else {
                    lp.setMargins(itemSpace, 0, itemSpace, lineSpace);
                }
                tagViewContainer.setLayoutParams(lp);
            }
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);
        }
    }

    public TagAdapter<T> getAdapter() {
        return mTagAdapter;
    }

}
