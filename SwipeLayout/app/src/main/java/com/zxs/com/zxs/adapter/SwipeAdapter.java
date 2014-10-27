package com.zxs.com.zxs.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

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
          viewHolder.bottomView=getBottomView(position,viewHolder.bottomView,swipeLayout);
          FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT);
          params.gravity= Gravity.RIGHT;
          swipeLayout.addView(viewHolder.bottomView,params);
          FrameLayout.LayoutParams frontparams =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
          swipeLayout.addView(viewHolder.frontView,frontparams);
          convertView=swipeLayout;
          convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.frontView=getSwipeView(position,viewHolder.frontView,parent);
            viewHolder.bottomView=getBottomView(position,viewHolder.bottomView,parent);
        }

        return convertView;
    }

    public abstract View getSwipeView(int position,View convertView, ViewGroup parent);
    public abstract View getBottomView(int position,View convertView, ViewGroup parent);
    private class ViewHolder{
        View frontView;
        View bottomView;
    }
}
