package com.zxs.com.zxs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zxs.swipelayout.SwipeLayout;

/**
 * Created by zxs on 14/10/26.
 */
public abstract class SwipeAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private LayoutInflater inflater;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
      if(convertView==null){
          SwipeLayout swipeLayout=new SwipeLayout(parent.getContext());
          viewHolder=new ViewHolder();
          viewHolder.frontView=getSwipeView(position, viewHolder.frontView,swipeLayout);
          viewHolder.bottomView=getSwipeView(position,viewHolder.bottomView,swipeLayout);
          convertView=swipeLayout;
          convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.frontView=getSwipeView(position,viewHolder.frontView,parent);
            viewHolder.bottomView=getBottomView(position,viewHolder.bottomView,parent);
        }

        return getSwipeView(position, convertView, parent);
    }

    public abstract View getSwipeView(int position,View convertView, ViewGroup parent);
    public abstract View getBottomView(int position,View convertView, ViewGroup parent);
    private class ViewHolder{
        View frontView;
        View bottomView;
    }
}
