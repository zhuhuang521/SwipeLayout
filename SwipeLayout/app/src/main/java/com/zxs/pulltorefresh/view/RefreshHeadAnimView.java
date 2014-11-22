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
import android.view.View;
import android.widget.LinearLayout;
/**
 * 下拉刷新或者上拉加载更多的过度动画
 * Created by xuesong.zhu on 2014/10/28.
 */
public class RefreshHeadAnimView extends View{

    private Paint paint;

    private Paint textPaint;

    private int viewHeight = -1;
    private int viewWidth = -1;

    private RefreshCurrent mRefreshCurrent;
    private RefreshArrow mRefreshArrow;
    public float mTitleHeight;

    private int AnimStatus=0;
    public RefreshHeadAnimView(Context context) {
        this(context,null);
    }

    public RefreshHeadAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeadAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    /**
     * 初始化数据
     * */
    private void initData(Context context){
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(context.getResources().getDisplayMetrics().density*16);
        textPaint.setColor(0xff33b5e5);
        paint =  new Paint();
        paint.setColor(Color.GRAY);
        mRefreshArrow=new RefreshArrow(context);
        mRefreshArrow.radio=context.getResources().getDisplayMetrics().density*20;
        mRefreshArrow.lenght=context.getResources().getDisplayMetrics().density*20;
        mRefreshArrow.radiodefault=mRefreshArrow.radio;
        mRefreshArrow.lenghtdefault=mRefreshArrow.lenght;
        mRefreshCurrent = new RefreshCurrent();
        mTitleHeight=context.getResources().getDisplayMetrics().density*48;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurrent(canvas);
        //绘制箭头
        drawArrow(canvas);
        if(AnimStatus==1){
            drawRefreshText(canvas);
        }
    }

    /**
     * 绘制提示文字
     * */
    private void drawRefreshText(Canvas canvas){
        canvas.drawText("正在刷新数据……",viewWidth/2,mTitleHeight/2,textPaint);
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

        paint.setColor(Color.WHITE);
        if(viewHeight >= mTitleHeight){
            canvas.drawOval(mRefreshCurrent.currentRectf,paint);
        }
        canvas.drawRect(mRefreshCurrent.titleRectf,paint);
        canvas.drawColor(Color.WHITE);

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

     * */
    public void resetViewForRefresh(){
        ValueAnimator heightanim=ValueAnimator.ofInt(viewHeight,(int)mTitleHeight).setDuration(300);
        heightanim.addUpdateListener(new MyViewHeightUpdateListener());
        heightanim.addListener(new MyAnimatorListener(1));
        heightanim.start();
        ObjectAnimator animy1=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",viewHeight,-100).setDuration(300);
        ObjectAnimator animscale=ObjectAnimator.ofFloat(mRefreshArrow,"zoomScale",1,0.5f).setDuration(300);
        ObjectAnimator animx1=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_x",viewWidth/2,viewWidth/10*9).setDuration(2400);
        animx1.addUpdateListener(new MyAnimatorUpdateListener());
        AnimatorSet animatorSet1=new AnimatorSet();
        animatorSet1.playTogether(animy1,animx1,animscale);
        animatorSet1.start();

        ObjectAnimator animy2=ObjectAnimator.ofFloat(mRefreshArrow,"bottom_y",-100,mTitleHeight,5,mTitleHeight,20,mTitleHeight,30,mTitleHeight).setDuration(1700);
        animy2.addUpdateListener(new MyAnimatorUpdateListener());
        animy2.setStartDelay(300);
        animy2.start();
    }
    private class MyViewHeightUpdateListener implements  ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            RefreshHeadAnimView.this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (Integer) animation.getAnimatedValue()));
        }
    }

    private class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //viewHeight=(int)mRefreshArrow.bottom_y;
            RefreshHeadAnimView.this.invalidate();
            // RefreshAnimView.this.setMeasuredDimension(viewWidth,viewHeight);
        }
    }

    private class MyAnimatorListener implements Animator.AnimatorListener{
        private int flag;
        public MyAnimatorListener(int flag){
            this.flag=flag;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(flag==1){
                AnimStatus=1;
            }
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
    public void setPullSize(int size){
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size));
        //this.measure(viewWidth,size);
        viewHeight = size;
        mRefreshCurrent.setCurrentRectf(viewHeight);
        mRefreshCurrent.setTitleRectf(viewHeight);
        mRefreshArrow.bottom_x=viewWidth/2;
        mRefreshArrow.setPullY(viewHeight);
        this.invalidate();

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

        float radiodefault;
        float lenghtdefault;
        //剪头方向 1 向下 0 向上
        float direction=1;

        //三个点控制剪头方向
        float first_line_begin_x,first_line_begin_y,first_line_end_x,first_line_end_y;
        float second_line_begin_x,second_line_begin_y,second_line_end_x,second_line_end_y;
        float third_line_begin_x,third_line_begin_y,third_line_end_x,third_line_end_y;
        public RefreshArrow(Context context){
            paint = new Paint();
            paint.setColor(0XFF1ba9ba);
            paint.setAntiAlias(true);
            arrowPaint = new Paint();
            arrowPaint.setColor(Color.WHITE);
            arrowPaint.setAntiAlias(true);
            arrowPaint.setStrokeWidth(context.getResources().getDisplayMetrics().density);
        }
        /**
         * 松手是更新view的状态
         * */

        public void setZoomScale(float scale){
            this.zoomScale=scale;
            radio=radiodefault*zoomScale;
            lenght=lenghtdefault*zoomScale;

        }
        public float getZoomScale(){
            return zoomScale;
        }
        public void setBottom_y(float y){
            viewHeight=(int)y;
            bottom_y=y;

            first_line_begin_x=bottom_x;
            first_line_begin_y=bottom_y-radio-lenght/2;
            first_line_end_x=bottom_x;
            first_line_end_y=bottom_y-radio+lenght/2;

            second_line_begin_x=bottom_x;
            second_line_begin_y=(bottom_y-radio-lenght/2);
            second_line_end_x=(bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2);
            second_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2);

            third_line_begin_x=bottom_x;
            third_line_begin_y=(bottom_y-radio-lenght/2);
            third_line_end_x=(bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2);
            third_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2);
        }
        public void setBottom_x(float x){
            bottom_x=x;
            first_line_begin_x=bottom_x;
            first_line_begin_y=bottom_y-radio-lenght/2;
            first_line_end_x=bottom_x;
            first_line_end_y=bottom_y-radio+lenght/2;

            second_line_begin_x=bottom_x;
            second_line_begin_y=(bottom_y-radio-lenght/2);
            second_line_end_x=(bottom_x-(float)Math.sin(Math.toRadians((double)45))*lenght/2);
            second_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2);

            third_line_begin_x=bottom_x;
            third_line_begin_y=(bottom_y-radio-lenght/2);
            third_line_end_x=(bottom_x+(float)Math.sin(Math.toRadians((double)45))*lenght/2);
            third_line_end_y=(bottom_y-radio-lenght/2+(float)Math.sin(Math.toRadians((double)45))*lenght/2);
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
            if(zoomScale<1){
                setZoomScale(1f);
            }
            AnimStatus=0;
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

    /**
     * 初始化到默认状态
     * */
    public void resetHeadView(){
        ValueAnimator heightanim=ValueAnimator.ofInt(viewHeight,0).setDuration(300);
        heightanim.addUpdateListener(new MyViewHeightUpdateListener());
        heightanim.addListener(new MyAnimatorListener(1));
        heightanim.start();
    }
}
