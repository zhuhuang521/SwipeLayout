package com.zxs.pulltorefresh.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
        mRefreshArrow=new RefreshArrow(context);
        mRefreshArrow.radio=context.getResources().getDisplayMetrics().density*20;
        mRefreshArrow.lenght=context.getResources().getDisplayMetrics().density*20;
        mRefreshCurrent = new RefreshCurrent();
        mTitleHeight=context.getResources().getDisplayMetrics().density*48;

    }
     @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("pull to refresh ", 30, 30, paint);


        drawCurrent(canvas);
         //绘制箭头
        drawArrow(canvas);
    }

    /**
     * 绘制带有箭头的指示
     * **/
    private void drawArrow(Canvas canvas){
        canvas.drawCircle(mRefreshArrow.bottom_x,mRefreshArrow.bottom_y-mRefreshArrow.radio,mRefreshArrow.radio,mRefreshArrow.paint);
        canvas.drawLine(mRefreshArrow.first_line_begin_x,mRefreshArrow.first_line_begin_y,mRefreshArrow.first_line_end_x,
                mRefreshArrow.first_line_end_y,mRefreshArrow.arrowPaint);
        canvas.drawLine(mRefreshArrow.second_line_begin_x,mRefreshArrow.second_line_begin_y,mRefreshArrow.second_line_end_x,
                mRefreshArrow.second_line_end_y,mRefreshArrow.arrowPaint);
        canvas.drawLine(mRefreshArrow.third_line_begin_x,mRefreshArrow.third_line_begin_y,mRefreshArrow.third_line_end_x,
                mRefreshArrow.third_line_end_y,mRefreshArrow.arrowPaint);
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
        if(viewHeight==-1&&viewWidth==-1){
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
            viewWidth = MeasureSpec.getSize(widthMeasureSpec);
            mRefreshCurrent.setCurrentRectf(viewHeight);
            mRefreshCurrent.setTitleRectf(viewHeight);
            mRefreshArrow.bottom_x=viewWidth/2;
            mRefreshArrow.setPullY(viewHeight);
        }

    }

    /**
     * 松手放开
     * */
    public void resetViewForRefresh(){
        ObjectAnimator animy1=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",viewHeight,-100).setDuration(300);
        ObjectAnimator animscale=ObjectAnimator.ofFloat(mRefreshArrow,"zoomScale",1,1).setDuration(300);
        ObjectAnimator animx1=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_x",viewWidth/2,(viewHeight/10)*9).setDuration(2000);
        animx1.addUpdateListener(new MyAnimatorUpdateListener());
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.playTogether(animy1,animx1,animscale);
        animatorSet1.start();


        ObjectAnimator animy2=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",-100,0).setDuration(200);
        ObjectAnimator animy3=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",viewHeight,-100).setDuration(200);
        ObjectAnimator animy4=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",viewHeight,-100).setDuration(200);

        ObjectAnimator currentanimy1=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",viewHeight,-100).setDuration(200);
    }

    private class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            viewHeight=(int)mRefreshArrow.bottom_y;
            RefreshAnimView.this.invalidate();
           // RefreshAnimView.this.setMeasuredDimension(viewWidth,viewHeight);
        }
    }

    private class MyAnimatorListener implements Animator.AnimatorListener{
        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    /**
     * 设置顶部view的高度
     * */
    private void setAnimViewHeight(int height){
        viewHeight = height;
        mRefreshCurrent.setCurrentRectf(viewHeight);
        mRefreshCurrent.setTitleRectf(viewHeight);
        mRefreshArrow.bottom_x=viewWidth/2;
        mRefreshArrow.setBottom_y(viewHeight);
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
        Paint paint;
        Paint arrowPaint;
        float zoomScale=1.0F;
        float bottom_x,bottom_y;
        float radio;
        float lenght;

        //剪头方向 1 向下 0 向上
        float direction=1;

        //三个点控制剪头方向
        float first_line_begin_x,first_line_begin_y,first_line_end_x,first_line_end_y;
        float second_line_begin_x,second_line_begin_y,second_line_end_x,second_line_end_y;
        float third_line_begin_x,third_line_begin_y,third_line_end_x,third_line_end_y;
        public RefreshArrow(Context context){
            paint = new Paint();
            paint.setColor(0XFFFFA500);
            paint.setAntiAlias(true);

            arrowPaint = new Paint();
            arrowPaint.setColor(Color.WHITE);
            arrowPaint.setAntiAlias(true);
            arrowPaint.setStrokeWidth(context.getResources().getDisplayMetrics().density*2);
        }
        /**
         * 松手是更新view的状态
         * */

         public void setZoomScale(float scale){
             this.zoomScale=scale;
         }
         public float getZoomScale(){
             return zoomScale;
         }
         public void setBottom_y(float y){
            viewHeight=(int)y;
            second_line_begin_x=zoomScale*bottom_x;
            second_line_begin_y=(bottom_y-radio-lenght/2)*zoomScale;
            second_line_end_x=(bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
            second_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;

            third_line_begin_x=bottom_x*zoomScale;
            third_line_begin_y=(bottom_y-radio-lenght/2)*zoomScale;
            third_line_end_x=(bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
            third_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
        }
        public void setBottom_x(float x){
            bottom_x=x;
            second_line_begin_x=zoomScale*bottom_x;
            second_line_begin_y=(bottom_y-radio-lenght/2)*zoomScale;
            second_line_end_x=(bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
            second_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;

            third_line_begin_x=bottom_x*zoomScale;
            third_line_begin_y=(bottom_y-radio-lenght/2)*zoomScale;
            third_line_end_x=(bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
            third_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2)*zoomScale;
        }
        public float getBottom_y(){
            return bottom_y;
        }
        public float getBottom_x(){
            return bottom_x;
        }
         /**
         * 下拉刷新时更新view的状态
         * */
        public void setPullY(float y){
            this.bottom_y=y;

            first_line_begin_x=bottom_x;
            first_line_begin_y=bottom_y-radio-lenght/2;
            first_line_end_x=bottom_x;
            first_line_end_y=bottom_y-radio+lenght/2;

            if(bottom_y<2*mTitleHeight){
                direction=1;

                second_line_begin_x=bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                second_line_begin_y=bottom_y-radio+lenght/2-(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                second_line_end_x=bottom_x;
                second_line_end_y=bottom_y-radio+lenght/2;

                third_line_begin_x=bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                third_line_begin_y=bottom_y-radio+lenght/2-(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                third_line_end_x=bottom_x;
                third_line_end_y=bottom_y-radio+lenght/2;

            }
            else
            if(bottom_y>=2*mTitleHeight&&bottom_y<=3*mTitleHeight)
            {
                direction=0;
                float index = (y-2*mTitleHeight)/mTitleHeight;

                second_line_begin_x=bottom_x-(float)((1-index)*Math.sin(Math.toRadians((double)45))*lenght/2);
                second_line_begin_y=bottom_y-radio+lenght/2-(float)Math.sin(Math.toRadians((double)45))*lenght/2-
                        index*(float)(1-Math.sin(Math.toRadians((double)45))/2)*lenght;
                second_line_end_x=bottom_x-index*(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                second_line_end_y=bottom_y-radio+lenght/2-index*(float)(1-Math.sin(Math.toRadians((double)45))/2)*lenght;

                third_line_begin_x=bottom_x+(float)((1-index)*Math.sin(Math.toRadians((double)45))*lenght/2);
                third_line_begin_y=bottom_y-radio+lenght/2-(float)Math.sin(Math.toRadians((double)45))*lenght/2-
                        index*(float)(1-Math.sin(Math.toRadians((double)45))/2)*lenght;
                third_line_end_x=bottom_x+index*(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                third_line_end_y=bottom_y-radio+lenght/2-index*(float)(1-Math.sin(Math.toRadians((double)45))/2)*lenght;

            }
            else{
                second_line_begin_x=bottom_x;
                second_line_begin_y=bottom_y-radio-lenght/2;
                second_line_end_x=bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2;
                second_line_end_y=bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2;

                third_line_begin_x=bottom_x;
                third_line_begin_y=bottom_y-radio-lenght/2;
                third_line_end_x=bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2;;
                third_line_end_y=bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2;
            }
        }

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
