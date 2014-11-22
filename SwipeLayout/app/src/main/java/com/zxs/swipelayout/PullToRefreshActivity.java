package com.zxs.swipelayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zxs.pulltorefresh.view.PullToRefreshCompat;

/**
 * Created by zxs on 14/10/26.
 */
public class PullToRefreshActivity extends Activity {
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulltorefreshlayout);
        inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListView listView=(ListView)findViewById(R.id.pullListView);
        PullToRefreshCompat Compat = new PullToRefreshCompat(this,listView);
        listView.setAdapter(new MyAdapter());
    }

    /**
     * pullToRefresh Adapter
     * */
    private class MyAdapter extends BaseAdapter {
        private ViewHolder viewHolder;
        @Override
        public int getCount() {
            return 20;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView=inflater.inflate(R.layout.item_swipe_layout,null);
                viewHolder = new ViewHolder();
                viewHolder.bgtext=(TextView)convertView.findViewById(R.id.tv_bg);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.bgtext.setText("Position "+position);
            return convertView;
        }
        private class ViewHolder{
            TextView bgtext;
        }
    }
 }
