package com.zxs.swipelayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zxs.com.zxs.adapter.SwipeAdapter;

/**
 * Created by zxs on 14/10/26.
 */
public class SwipeListActivity extends Activity{

    private ListView mSwipeListview;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipelistlayout);
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSwipeListview=(ListView)findViewById(R.id.swipe_listview);
        MySwipeAdapter adapter=new MySwipeAdapter();
        mSwipeListview.setAdapter(adapter);
    }

    private class MySwipeAdapter extends SwipeAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getSwipeView(int position, View convertView, ViewGroup parent) {
            return inflater.inflate(R.layout.swipelayout,null);
        }

        @Override
        public View getBottomView(int position, View convertView, ViewGroup parent) {
            return inflater.inflate(R.layout.item_swipe_bottom_layout,null);
        }
    }

}
