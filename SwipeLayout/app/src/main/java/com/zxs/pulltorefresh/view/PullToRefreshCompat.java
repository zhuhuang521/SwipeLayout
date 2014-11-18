package com.zxs.pulltorefresh.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.zxs.swipelayout.R;

/**
 * 下拉刷新支持库
 * Created by xuesong.zhu on 2014/10/29.
 */
public class PullToRefreshCompat {
    private ListView mListView;
    private GridView mGridView;
    private Context mContext;

    private LayoutInflater inflater;

    private RefreshHeadAnimView mHeadAnimView;
    private RefreshFooterAnimView mFooterAnimView;
    private int mViewType=0;
    private float mDownY =-1;

    //刷新的状态 0 默认状态 1 正在下拉 2 请求数据中
    private int mRefreshStatus=0;
    public PullToRefreshCompat(Context context , ListView view){
        this.mContext = context;
        mViewType=0;
        mListView = view;
        initCompat();
    }
    public PullToRefreshCompat(Context context,GridView view){
        this.mContext = context;
        mViewType=1;
        mGridView = view;
        initCompat();
    }
    /**
     * 初始化数据
     * init compat
     * */
    private void initCompat(){
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headView=inflater.inflate(R.layout.headview_layout,null);
        if(mViewType==0){
            mListView.setOnTouchListener(new ListViewOnTouchListener());
            mHeadAnimView=(RefreshHeadAnimView)headView.findViewById(R.id.animView);
            mListView.addHeaderView(headView);
            mFooterAnimView = new RefreshFooterAnimView(mContext);
            mListView.addFooterView(mFooterAnimView);
        }
        else
        if(mViewType==1){

        }

    }

    /**
     * 获得第一个可见的位置
     * */
    private int getFirstPosition(){
       switch (mViewType){
           case 0:
               return mListView.getFirstVisiblePosition();
           case 1:
               return mGridView.getFirstVisiblePosition();
           default:
               return -1;
       }
    }
     /**
     * 触摸监听
     * */
    private class ListViewOnTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.v("zxs","touch action"+getFirstPosition());
            switch(event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    if(getFirstPosition()==0&&mDownY==-1){
                        mDownY=event.getY();
                        return false;
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if(getFirstPosition()==0&&mDownY==-1){
                        mDownY=event.getY();
                    }
                    if(mDownY!=-1&&(mRefreshStatus == 0||mRefreshStatus == 1)){
                        int rangeY=(int)(mDownY-event.getY());

                        if(rangeY<=0){
                            mRefreshStatus=1;
                            changeHeadViewHeight(-rangeY);
                            return true;
                        }

                    }
                    break;
                default:
                    if(mRefreshStatus==1) {
                        setHeadViewToLoading();
                    }
                    break;
            }
            return false;
        }
    }

    /**
     * 改变headRefreshView height
     * **/
    private void changeHeadViewHeight(int height){
        Log.v("zxs","range Y "+height);
        mHeadAnimView.setPullSize(height);
    }

    /**
     * 重置headView
     * */
    private void resetHeadView(){

    }

    /**
     * 更改headView为刷新loading状态
     * */
    private void setHeadViewToLoading(){
        if(mHeadAnimView!=null){
            mHeadAnimView.resetViewForRefresh();
            mHeadAnimView.invalidate();
        }
    }

    /**
     * 更改刷新状态为加载更多
     * */
    public void setBottomViewToLoadingMore(){

    }

    /**
     * 回复刷新状态为初始状态
     * */
    public void setRefreshStatusToBegin(){

    }

 }
