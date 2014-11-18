package com.zxs.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.zxs.pulltorefresh.view.RefreshHeadAnimView;
import com.zxs.swipelayout.R;

/**
 * Created by zxs on 14/10/26.
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener{
    private LayoutInflater inflater;

    private View mHeadView;

    private LinearLayout mHeadLayout;
    private RefreshHeadAnimView animView;
    private View mFooterView;

    private float mDownY;

    private Scroller mScroller;
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mScroller = new Scroller(context,new DecelerateInterpolator());
        this.setOnScrollListener(this);
        initView();
    }

    /**
     * init headView and footView
     * */
    private void initView(){
        mHeadView=inflater.inflate(R.layout.headview_layout,null);
        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        this.addHeaderView(mHeadView);
       // mHeadLayout=(LinearLayout)mHeadView.findViewById(R.id.headText);
        //mHeadLayout.setLayoutParams(params);
        animView=(RefreshHeadAnimView)mHeadView.findViewById(R.id.animView);

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(getFirstVisiblePosition()==0){
                    float lastY = mDownY-ev.getY();
                    if(lastY<0){
                        changeRefreshViewHeight(-lastY);
                        return true;
                    }
                }

                break;
            default:
                //mScroller.startScroll(0,mHeadLayout.getHeight(),0,-mHeadLayout.getHeight(),300);
                resetHeadView();
                this.invalidate();
                break;
        }


        return super.onTouchEvent(ev);
    }

    /**
     * 重置headView
     * */
    private void resetHeadView(){
        if(animView!=null){
            animView.resetViewForRefresh();
        }
    }
    /**
     * change refresh view height
     * @param y 触摸动作
     * */
    private void changeRefreshViewHeight(float y){
       // Log.v("zxs", "touch y " + y);
       //mHeadLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)y));
       animView.setPullSize((int)y);
    }
     @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
           // Log.v("zxs","computeScroll dexy"+mScroller.getCurrY()+"   "+mScroller.getStartY());
            //mHeadLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,mScroller.getCurrY()>1?mScroller.getCurrY():1));
        }
        super.computeScroll();
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
