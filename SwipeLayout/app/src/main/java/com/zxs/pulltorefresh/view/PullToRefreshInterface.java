package com.zxs.pulltorefresh.view;

import android.widget.AbsListView;

/**
 * Created by zxs on 14/11/19.
 */
public interface PullToRefreshInterface {
    public void beginRefresh();
    public void loadMore();
    public void titlePosition(int y);
	public void onScrollStateChanged(AbsListView view, int scrollState);
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
