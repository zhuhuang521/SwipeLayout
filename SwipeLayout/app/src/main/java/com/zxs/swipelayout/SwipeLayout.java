package com.zxs.swipelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
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
    private View mFrontView;
    private View mBottomView;

    private float xOffset;
    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.v("zxs","created");
        mViewDragHelper=ViewDragHelper.create(this,1.0f,new ViewDragCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initChildView();
    }

    /**
     * 初始化子view
     * */
    private void initChildView(){
        mFrontView=getChildAt(1);
        mBottomView=getChildAt(0);
    }


    /**
     * ViewDragHelper callback
     * */
    private class ViewDragCallback extends ViewDragHelper.Callback{


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //Log.v("zxs","view changed "+left+"   "+top+"   "+dx+"   "+dy);
            xOffset=Math.abs((float)left)/mBottomView.getWidth();
            Log.v("zxs","view changed "+xOffset);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            final int newLeft =Math.min(Math.max(-mBottomView.getWidth(), left), 0);
            Log.v("zxs","clampViewPositionHorizontal"+newLeft);
            return newLeft;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //slidViewTo();

            if(xOffset>=0.5){
                mViewDragHelper.settleCapturedViewAt(-mBottomView.getWidth(), 0);
            }else{
                mViewDragHelper.settleCapturedViewAt(0, 0);
            }
            invalidate();
        }

        public ViewDragCallback() {
            super();
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            if(mFrontView==null||mBottomView==null){
                initChildView();
            }
            return view==mFrontView;
        }


    }

    /**
     * 自动滑动view到某个位置
     * */

    private boolean slidViewTo(){

        mViewDragHelper.smoothSlideViewTo(mFrontView,0,0);

        return true;
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        //mViewDragHelper.smoothSlideViewTo(mFrontView,0,0);
       if(mViewDragHelper.continueSettling(true)){

            ViewCompat.postInvalidateOnAnimation(this);
       }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action= MotionEventCompat.getActionMasked(ev);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
            mViewDragHelper.cancel();
            return false;
        }
        Log.v("zxs","intercept view");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
