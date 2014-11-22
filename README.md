SwipeLayout
SwipeLayout 主要实现了下拉刷新的封装，可以自定义下拉效果，只要继承BaseView实现相应的方法。

基本使用方法为:
1：布局中定义响应的ListView
2: 程序代码中：
   ListView listView=(ListView)findViewById(R.id.pullListView);
   PullToRefreshCompat Compat = new PullToRefreshCompat(this,listView);
   可以实现响应的下拉刷新：
3：实现相应的回掉接口
  下来刷新提供了接口
  public interface PullToRefreshInterface {
    public void beginRefresh();
    public void loadMore();
    public void titlePosition(int y);
	public void onScrollStateChanged(AbsListView view, int scrollState);
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}

在代码中完成：
  Compat.setRefreshListener(new PullToRefreshInterface()……);
  实现下拉状态相应的回掉。
  下拉刷新
===========
