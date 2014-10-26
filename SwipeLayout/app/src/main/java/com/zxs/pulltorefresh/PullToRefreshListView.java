package com.zxs.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zxs on 14/10/26.
 */
public class PullToRefreshListView extends ListView{
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
