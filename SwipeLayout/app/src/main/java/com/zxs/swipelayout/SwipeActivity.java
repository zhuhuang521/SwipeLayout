package com.zxs.swipelayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by zxs on 14/10/26.
 */
public class SwipeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.swipelayout);
        SwipeLayout swipeLayout=new SwipeLayout(this);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View frontView=inflater.inflate(R.layout.swipelayout,null);
        TextView textView=new TextView(this);
        textView.setText("bottom");
        textView.setBackgroundColor(Color.RED);
        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams params1 =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 200);
        params1.gravity= Gravity.RIGHT;
        swipeLayout.addView(textView,params1);
        swipeLayout.addView(frontView,params);
        setContentView(swipeLayout);
    }
}
