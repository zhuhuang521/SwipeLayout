package com.zxs.pulltorefresh;

/**
 * Created by zxs on 14/11/19.
 */
public interface PullToRefreshInterface {
    public void beginRefresh();
    public void loadMore();
    public void titlePosition(int y);
}
