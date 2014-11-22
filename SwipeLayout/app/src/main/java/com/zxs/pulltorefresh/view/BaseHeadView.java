package com.zxs.pulltorefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zxs on 14/11/22.
 */
public abstract class BaseHeadView extends View{
    public float mTitleHeight;
    public BaseHeadView(Context context) {
        super(context);
    }

    public BaseHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setPullSize(int size);
    public abstract void resetHeadView();
    public abstract void resetViewForRefresh();
}
