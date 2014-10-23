package com.zxs.swipelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zxs on 14/10/23.
 */
public class SwipeLayout extends FrameLayout{

    private ViewDragHelper mViewDragHelper;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper=ViewDragHelper.create(this,1.0f,new ViewDragCallback());
    }


    /**
     * ViewDragHelper callback
     * */
    private class ViewDragCallback extends ViewDragHelper.Callback{
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.v("zxs","horizontal touch "+left+"   "+dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - child.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }


        public ViewDragCallback() {
            super();
        }

        @Override
        public boolean tryCaptureView(View view, int i) {

            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action= MotionEventCompat.getActionMasked(ev);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
            mViewDragHelper.cancel();
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);

        return true;
    }
}
