package com.forcelain.android.awesomerecyclerview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class AwesomeLayoutManager extends RecyclerView.LayoutManager {

    private SparseArray<View> viewCache = new SparseArray<>();

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

        View anchorView = getAnchorView();
        int anchorPos = 0;
        int anchorTop = 0;
        if (anchorView != null){
            anchorPos = getPosition(anchorView);
            anchorTop = getDecoratedTop(anchorView);
        }

        viewCache.clear();

        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            int pos = getPosition(view);
            viewCache.put(pos, view);
        }

        for (int i = 0; i < viewCache.size(); i++) {
            detachView(viewCache.valueAt(i));
        }

        boolean fillUp = true;
        int pos = anchorPos - 1;
        int viewBottom = anchorTop;
        while (fillUp && pos >= 0){
            View view = viewCache.get(pos);
            if (view == null){
                view = recycler.getViewForPosition(pos);
                addView(view, 0);
                measureChildWithMargins(view, 0, 0);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
                layoutDecorated(view, 0, viewBottom - decoratedMeasuredHeight, decoratedMeasuredWidth, viewBottom);
            } else {
                attachView(view);
                viewCache.remove(pos);
            }
            viewBottom = getDecoratedTop(view);
            fillUp = (viewBottom > 0);
            pos--;
        }

        pos = anchorPos;
        boolean fillDown = true;
        int height = getHeight();
        int viewTop = anchorTop;
        int itemCount = getItemCount();

        while (fillDown && pos < itemCount){
            View view = viewCache.get(pos);
            if (view == null){
                view = recycler.getViewForPosition(pos);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
                layoutDecorated(view, 0, viewTop, decoratedMeasuredWidth, viewTop + decoratedMeasuredHeight);
            } else {
                attachView(view);
                viewCache.remove(pos);
            }
            viewTop = getDecoratedBottom(view);
            fillDown = viewTop <= height;
            pos++;
        }
    }

    private View getAnchorView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int decoratedBottom = getDecoratedBottom(view);
            if (decoratedBottom > 0){
                return view;
            }
        }
        return null;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        fill(recycler);
        return delta;
    }

    private int scrollVerticallyInternal(int dy) {
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if (childCount == 0){
            return 0;
        }

        final View topView = getChildAt(0);
        final View bottomView = getChildAt(childCount - 1);

        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getHeight()) {
            return 0;
        }

        int delta = 0;
        if (dy < 0){
            View firstView = getChildAt(0);
            int firstViewAdapterPos = getPosition(firstView);
            if (firstViewAdapterPos > 0){
                delta = dy;
            } else {
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        } else if (dy > 0){
            View lastView = getChildAt(childCount - 1);
            int lastViewAdapterPos = getPosition(lastView);
            if (lastViewAdapterPos < itemCount - 1){
                delta = dy;
            } else {
                int viewBottom = getDecoratedBottom(lastView);
                int parentBottom = getHeight();
                delta = Math.min(viewBottom - parentBottom, dy);
            }
        }
        return delta;
    }
}
