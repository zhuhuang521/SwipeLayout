package com.zxs.pulltorefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 下拉刷新或者上拉加载更多的过度动画
 * Created by xuesong.zhu on 2014/10/28.
 */
public class RefreshAnimView extends View{

    private Paint paint;

    private int viewHeight = -1;
    private int viewWidth = -1;

    private RefreshCurrent mRefreshCurrent;
    private RefreshArrow mRefreshArrow;
    private float mTitleHeight;
    public RefreshAnimView(Context context) {
        this(context,null);
    }

    public RefreshAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    /**
     * 初始化数据
     * */
    private void initData(Context context){
        paint =  new Paint();
        paint.setColor(Color.GRAY);
        mRefreshArrow=new RefreshArrow();
        mRefreshCurrent = new RefreshCurrent();
        mTitleHeight=context.getResources().getDisplayMetrics().density*48;

    }
     @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("pull to refresh ",30,30,paint);
         Log.v("zxs","ondraw");

         //绘制箭头
         drawArrow(canvas);
         drawCurrent(canvas);
    }

    /**
     * 绘制带有箭头的指示
     * **/
    private void drawArrow(Canvas canvas){

    }

    /**
     * 绘制背景
     * */
    private void drawCurrent(Canvas canvas){

        paint.setColor(Color.YELLOW);
        if(viewHeight >= mTitleHeight){
           // canvas.drawRect(mRefreshCurrent.currentRectf,paint);
            canvas.drawOval(mRefreshCurrent.currentRectf,paint);
        }
       // paint.setColor(Color.RED);
        canvas.drawRect(mRefreshCurrent.titleRectf,paint);

    }
      @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mRefreshCurrent.setCurrentRectf(viewHeight);
        mRefreshCurrent.setTitleRectf(viewHeight);
    }

    /**
     * 下拉刷新的大小
     *@param size 下拉刷新的距离
     * */
    public void setPullSize(float size){

    }

    /**
     * 箭头数据的定义
     * */
    public class RefreshArrow{
        float zoomScale;
        float bottom_x,bottom_y;
        float radio;
        float direction;
    }

    /**
     * 背景数据
     * */
    public class RefreshCurrent{

        RectF titleRectf;
        RectF currentRectf;

        public RefreshCurrent(){
            titleRectf = new RectF();
            titleRectf.set(0,0,viewWidth,0);
            currentRectf=new RectF();
            currentRectf.set(0,mTitleHeight,viewWidth,mTitleHeight+1);
        }

        public void setTitleRectf(int height){
            titleRectf.set(0,0,viewWidth,height>mTitleHeight?mTitleHeight:height);
        }
        public void setCurrentRectf(int height){
            if(height>mTitleHeight){
                currentRectf.set(0,2*mTitleHeight-height,viewWidth,height);
            }else{
                //currentRectf.set(0,0,0,0);
            }
        }
    }
  }
