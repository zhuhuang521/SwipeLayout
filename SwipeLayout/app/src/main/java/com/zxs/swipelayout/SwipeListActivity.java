package com.zxs.swipelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zxs.com.zxs.adapter.SwipeAdapter;

/**
 * Created by zxs on 14/10/26.
 */
public class SwipeListActivity extends Activity{

    private ListView mSwipeListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeListview=(ListView)findViewById(R.id.swipe_listview);
    }

    private class MySwipeAdapter extends SwipeAdapter{
        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getSwipeView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }

}
