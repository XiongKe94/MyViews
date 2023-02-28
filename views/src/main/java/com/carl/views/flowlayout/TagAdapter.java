package com.carl.views.flowlayout;

import android.view.View;

import java.util.List;

public abstract class TagAdapter<T> {
    private List<T> mTagDatas;

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(int position, T t);
}
