package com.zxs.swipelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findViewById(R.id.btn_swipe).setOnClickListener(new MyClickListener());
        findViewById(R.id.btn_pulltorefresh).setOnClickListener(new MyClickListener());
        findViewById(R.id.btn_swipelist).setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_swipe:
                    Intent gotoSwipe=new Intent();
                    gotoSwipe.setClass(MyActivity.this,SwipeActivity.class);
                    startActivity(gotoSwipe);
                    break;
                case R.id.btn_pulltorefresh:
                    Intent putorefresh=new Intent();
                    putorefresh.setClass(MyActivity.this,PullToRefreshActivity.class);
                    startActivity(putorefresh);
                    break;
                case R.id.btn_swipelist:
                    Intent swipelist=new Intent();
                    swipelist.setClass(MyActivity.this,SwipeListActivity.class);
                    startActivity(swipelist);
                    break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
