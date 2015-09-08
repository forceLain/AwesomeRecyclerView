package com.forcelain.android.awesomerecyclerview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class AwesomeLayoutManager extends RecyclerView.LayoutManager {

    public AwesomeLayoutManager(Context context) {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        fill(recycler);
    }

    private void fill(RecyclerView.Recycler recycler) {
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            int pos = getPosition(view);
            if (pos == RecyclerView.NO_POSITION) {
                recycler.recycleView(view);
            }
        }

        int pos = 0;
        int topOffset = 0;
        boolean fillDown = true;
        int height = getHeight();

        while (fillDown){
            View view = recycler.getViewForPosition(pos);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
            layoutDecorated(view, 0, topOffset, decoratedMeasuredWidth, topOffset + decoratedMeasuredHeight);
            topOffset = getDecoratedBottom(view);
            fillDown = (topOffset <= height);
            pos++;
        }

    }
}
