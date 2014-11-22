package com.zxs.pulltorefresh.view;

import android.content.Context;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
=======
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
<<<<<<< HEAD
import android.widget.AbsListView;
=======
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
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

<<<<<<< HEAD
    private RefreshWeixiAnimView mHeadAnimView;
    private RefreshFooterAnimView mFooterAnimView;
    private int mViewType=0;
    private float mDownY =-1;
    private boolean mCouldLoadMore=true;
    private PullToRefreshInterface pullToRefreshInterface;
    //刷新的状态 0 默认状态 1 下拉刷新中 2 下拉请求数据中 3加载更多
=======
<<<<<<< HEAD
    private RefreshHeadAnimView mHeadAnimView;
    private RefreshFooterAnimView mFooterAnimView;
    private int mViewType=0;
    private float mDownY =-1;
    private boolean mCouldLoadMore=true;
    private PullToRefreshInterface pullToRefreshInterface;
    //刷新的状态 0 默认状态 1 下拉刷新中 2 下拉请求数据中 3加载更多
=======
    private RefreshAnimView mHeadAnimView;

    private int mViewType=0;
    private float mDownY =-1;

    //刷新的状态 0 默认状态 1 正在下拉 2 请求数据中
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
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
<<<<<<< HEAD
        View headView=inflater.inflate(R.layout.list_headview_layout,null);
        View footerView=inflater.inflate(R.layout.list_footerview_layout,null);
=======
<<<<<<< HEAD
        View headView=inflater.inflate(R.layout.list_headview_layout,null);
        View footerView=inflater.inflate(R.layout.list_footerview_layout,null);
        if(mViewType==0){
            mListView.setOnTouchListener(new ListViewOnTouchListener());
            mListView.setOnScrollListener(new MyScrollListener());
            mHeadAnimView=(RefreshHeadAnimView)headView.findViewById(R.id.HeadAnimView);
            mListView.addHeaderView(headView);
            mFooterAnimView = (RefreshFooterAnimView)footerView.findViewById(R.id.footerAnimView);
            mFooterAnimView.setCompat(this);
            mListView.addFooterView(footerView);
=======
        View headView=inflater.inflate(R.layout.headview_layout,null);
>>>>>>> FETCH_HEAD
        if(mViewType==0){
            mListView.setOnTouchListener(new ListViewOnTouchListener());
            mListView.setOnScrollListener(new MyScrollListener());
            mHeadAnimView=(RefreshWeixiAnimView)headView.findViewById(R.id.HeadAnimView);
            mListView.addHeaderView(headView);
<<<<<<< HEAD
            mFooterAnimView = (RefreshFooterAnimView)footerView.findViewById(R.id.footerAnimView);
            mFooterAnimView.setCompat(this);
            mListView.addFooterView(footerView);
=======
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
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
<<<<<<< HEAD

            switch(event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    if(mRefreshStatus!=0){
                        return true;
                    }
=======
<<<<<<< HEAD

            switch(event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    if(mRefreshStatus!=0){
                        return true;
                    }
=======
            Log.v("zxs","touch action");
            switch(event.getAction()){

                case MotionEvent.ACTION_DOWN:
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
                    if(getFirstPosition()==0&&mDownY==-1){
                        mDownY=event.getY();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(getFirstPosition()==0&&mDownY==-1){
                        mDownY=event.getY();
                    }
                    if(mDownY!=-1&&(mRefreshStatus == 0||mRefreshStatus == 1)){
                        int rangeY=(int)(mDownY-event.getY());

                        if(rangeY<=0){
                            mRefreshStatus=1;
                            changeHeadViewHeight(-rangeY);
<<<<<<< HEAD
                            mRefreshStatus=1;
=======
<<<<<<< HEAD
                            mRefreshStatus=1;
=======
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
                            return true;
                        }

                    }
                    break;
                default:
                    if(mRefreshStatus==1) {
<<<<<<< HEAD
						mRefreshStatus=2;

                        int rangeY=-(int)(mDownY-event.getY());
                        Log.v("zxs","headview"+rangeY+"  "+2*mHeadAnimView.mTitleHeight);
=======
<<<<<<< HEAD
						mRefreshStatus=2;
						int rangeY=-(int)(mDownY-event.getY());
>>>>>>> FETCH_HEAD
						if(rangeY>=2*mHeadAnimView.mTitleHeight){

                        	setHeadViewToLoading();
						}
						else {
							setRefreshStatusToBegin();
						}
						mDownY=-1;
						return  true;
<<<<<<< HEAD
                    }
					mDownY=-1;
=======
                    }
					mDownY=-1;
=======
                        setHeadViewToLoading();
                    }
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
                    break;
            }
            return false;
        }
    }

    /**
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> FETCH_HEAD
     * 回调滑动距离接口
     * */
    private void callbackScrollY(){
        int y;
        View c = mListView.getChildAt(0);
        if (c == null) {
            y= 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();
        y= -top + firstVisiblePosition * c.getHeight() ;
        pullToRefreshInterface.titlePosition(y);
    }

    /**
     * 滑动监听
     * */
    private class MyScrollListener implements AbsListView.OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(pullToRefreshInterface!=null){
					pullToRefreshInterface.onScrollStateChanged(view,scrollState);
					callbackScrollY();
                }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(pullToRefreshInterface!=null){
					pullToRefreshInterface.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
					callbackScrollY();
				}
                if(mCouldLoadMore&&mRefreshStatus==0&&(firstVisibleItem+visibleItemCount+1)==totalItemCount){
                    if(pullToRefreshInterface!=null){

                        mFooterAnimView.setStatusToLoadMore();
                        pullToRefreshInterface.loadMore();
                        mRefreshStatus=3;
                        mCouldLoadMore=false;
                    }
                }
        }
    }

    /**
     * 设置是否可以加载更多
     * */
    public void setCouldLoadMore(boolean loadMore){
        this.mCouldLoadMore=loadMore;
    }

     /**
<<<<<<< HEAD
     * 改变headRefreshView height
     * **/
    private void changeHeadViewHeight(int height){
=======
     * 改变headRefreshView height
     * **/
    private void changeHeadViewHeight(int height){
=======
     * 改变headRefreshView height
     * **/
    private void changeHeadViewHeight(int height){
        Log.v("zxs","range Y "+height);
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
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
<<<<<<< HEAD
    public void setHeadViewToLoading(){
=======
<<<<<<< HEAD
    public void setHeadViewToLoading(){
        if(mHeadAnimView!=null){
            mHeadAnimView.resetViewForRefresh();
            mHeadAnimView.invalidate();
            if(pullToRefreshInterface!=null){
                pullToRefreshInterface.beginRefresh();
            }
        }
    }

    /**
     * 更改刷新状态为加载更多
     * */
    public void setBottomViewToLoadingMore(){
		if(pullToRefreshInterface!=null){
			pullToRefreshInterface.loadMore();
		}
    }

    /**
     * 回复刷新状态为初始状态
     * */
    public void setRefreshStatusToBegin(){
        if(mRefreshStatus==3){
            mFooterAnimView.setStatusToDefault();
			mCouldLoadMore=true;
        }
        else {
            mHeadAnimView.resetHeadView();
            mRefreshStatus = 0;
        }
		mListView.setPadding(mListView.getPaddingLeft(),(int)(mContext.getResources().getDisplayMetrics().density*48)
				,mListView.getPaddingRight(),mListView.getPaddingBottom());
    }

    public void setRefreshListener(PullToRefreshInterface listener){
        this.pullToRefreshInterface=listener;
    }

    /**
     * 设置状态
     * */
    public void setRefreshStatus(int status){
        this.mRefreshStatus=status;
    }

	public boolean isLoading(){
		if(mRefreshStatus!=0){
			return false;
		}
		return true;
	}

	/**
	 * 设置为load失败
	 * **/
	public void setLoadFailed(){
		mFooterAnimView.setLoadError("加载错误,点击重新加载");
	}
   }
=======
    private void setHeadViewToLoading(){
>>>>>>> FETCH_HEAD
        if(mHeadAnimView!=null){
            mHeadAnimView.resetViewForRefresh();
            mHeadAnimView.invalidate();
            if(pullToRefreshInterface!=null){
                pullToRefreshInterface.beginRefresh();
            }
        }
    }

    /**
     * 更改刷新状态为加载更多
     * */
    public void setBottomViewToLoadingMore(){
		if(pullToRefreshInterface!=null){
			pullToRefreshInterface.loadMore();
		}
    }

    /**
     * 回复刷新状态为初始状态
     * */
    public void setRefreshStatusToBegin(){
        if(mRefreshStatus==3){
            mFooterAnimView.setStatusToDefault();
			mCouldLoadMore=true;
        }
        else {
            mHeadAnimView.resetHeadView();
            mRefreshStatus = 0;
        }

    }

    public void setRefreshListener(PullToRefreshInterface listener){
        this.pullToRefreshInterface=listener;
    }

    /**
     * 设置状态
     * */
    public void setRefreshStatus(int status){
        this.mRefreshStatus=status;
    }

	public boolean isLoading(){
		if(mRefreshStatus!=0){
			return false;
		}
		return true;
	}

<<<<<<< HEAD
	/**
	 * 设置为load失败
	 * **/
	public void setLoadFailed(){
		mFooterAnimView.setLoadError("加载错误,点击重新加载");
	}
   }
=======
 }
>>>>>>> 6ac5c55ba351bde0e7c86aeb0d6f56f29def8fa5
>>>>>>> FETCH_HEAD
