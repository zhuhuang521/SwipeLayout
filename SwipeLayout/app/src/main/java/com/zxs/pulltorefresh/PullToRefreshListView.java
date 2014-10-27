package com.zxs.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by zxs on 14/10/26.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener{

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PullToRefreshListView(Context context) {
        this(context,null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * init headView and footView
     * */
    private void initView(){

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * refresh mode
     * */
    public class RefreshMode{
        public static final int BothMode=0;
        public static final int PullMode=1;
        public static final int TapMode=2;
    }
 }
