package com.zxs.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zxs.swipelayout.R;

/**
 * Created by zxs on 14/10/26.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener{
    private LayoutInflater inflater;

    private View mHeadView;
    private View mFooterView;
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    /**
     * init headView and footView
     * */
    private void initView(){
        mHeadView=inflater.inflate(R.layout.headview_layout,null);
        this.addHeaderView(mHeadView);

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                changeRefreshViewHeight(ev);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        return super.onTouchEvent(ev);
    }

    /**
     * change refresh view height
     * @param ev 触摸动作
     * */
    private void changeRefreshViewHeight(MotionEvent ev){
        int y=(int)ev.getHistoricalY(0);
        Log.v("zxs","touch y "+y);
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
