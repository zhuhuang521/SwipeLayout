package com.zxs.pulltorefresh.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by zxs on 14/11/22.
 */
public class RefreshWeixiAnimView extends BaseHeadView {

    private WeixinEye mEye;

    private int mViewHeight=-1,mViewWidth=-1;

    private int mDrawHeight;

    private int mStatus_1_lenght,mStatus_2_lenght,mStatus_3_lenght;
    private int mStatusHeight;
    private Context mContext;

    public RefreshWeixiAnimView(Context context) {
        this(context, null);
    }

    public RefreshWeixiAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshWeixiAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        this.setBackgroundColor(Color.BLACK);
        mDrawHeight=(int)context.getResources().getDisplayMetrics().density*70;
        mStatus_1_lenght=(int)context.getResources().getDisplayMetrics().density*10;
        mStatus_2_lenght=(int)context.getResources().getDisplayMetrics().density*10;
        mStatus_3_lenght=(int)context.getResources().getDisplayMetrics().density*10;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mEye==null||mViewHeight<=mDrawHeight){
            return;
        }

        canvas.drawArc(mEye.smallRectF,-170,20,false,mEye.rectPaint);
        canvas.drawArc(mEye.smallRectF,-130,35,false,mEye.rectPaint);
        if(mEye.status==1){
            drawCircle(canvas);
        }
        if(mEye.status==2){
            drawCircle(canvas);
            canvas.drawArc(mEye.topRectF,-90,360,false,mEye.circlePaint);
            canvas.drawArc(mEye.bottomRectF,-90,360,false,mEye.circlePaint);
        }

    }
    /**
     * 绘制圆圈
     * */
    private void drawCircle(Canvas canvas){
            canvas.drawArc(mEye.circleReftF,-90,360,false,mEye.circlePaint);

    }
     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mViewWidth==-1&&mViewHeight==-1){
            mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
            mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
            mEye = new WeixinEye();
        }
    }

    @Override
    public void setPullSize(int size) {
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size));
        if(mEye == null){
            mEye = new WeixinEye();
        }
        mViewHeight=size;
        mEye.setHeight(size);
        this.invalidate();
    }

    @Override
    public void resetHeadView() {
        ValueAnimator heightanim=ValueAnimator.ofInt(mViewHeight,0).setDuration(300);
        heightanim.addUpdateListener(new MyViewHeightUpdateListener());
        heightanim.addListener(new MyAnimatorListener(1));
        heightanim.start();
    }

    @Override
    public void resetViewForRefresh() {
        ValueAnimator heightanim=ValueAnimator.ofInt(mViewHeight,mStatusHeight).setDuration(300);
        heightanim.addUpdateListener(new MyViewHeightUpdateListener());
        heightanim.addListener(new MyAnimatorListener(1));
        heightanim.start();
    }

    /**
     * 微信顶部眼睛
     * */
    private class WeixinEye{
        private int minRectSize;
        private int dif;
        public WeixinEye(){
            minRectSize=(int)mContext.getResources().getDisplayMetrics().density*10;
            rectPaint=new Paint();
            rectPaint.setAntiAlias(true);
            rectPaint.setColor(Color.WHITE);
            rectPaint.setAlpha(alpha);
            rectPaint.setStyle(Paint.Style.STROKE);
            smallRectF=new RectF();
            circleReftF=new RectF();
            circlePaint=new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.WHITE);
            circlePaint.setAlpha(alpha);
            circlePaint.setStyle(Paint.Style.STROKE);
            topRectF=new RectF();
            bottomRectF=new RectF();
            setRectF(0);
            mTitleHeight=(mDrawHeight+mStatus_1_lenght+mStatus_2_lenght+mStatus_3_lenght+50)/2;
            mStatusHeight=mDrawHeight+mStatus_1_lenght+mStatus_2_lenght+mStatus_3_lenght;
        }

        public void setRectF(int height){
                if(height>=mDrawHeight&&height<mDrawHeight+mStatus_1_lenght) {
                    dif = height - mDrawHeight;
                    smallRectF.set(mViewWidth / 2 - minRectSize - dif, height / 2 - dif - minRectSize,
                            mViewWidth / 2 + minRectSize + dif, height / 2 + dif + minRectSize);
                    rectPaint.setStrokeWidth((height-mDrawHeight));
                }
                if(height>=mDrawHeight+mStatus_1_lenght){
                    smallRectF.set(mViewWidth / 2 - minRectSize - dif, height / 2 - dif - minRectSize,
                            mViewWidth / 2 + minRectSize + dif, height / 2 + dif + minRectSize);
                }
                if(height>=mDrawHeight+mStatus_1_lenght&&height<=mDrawHeight+mStatus_1_lenght+mStatus_2_lenght){
                    int paintdif=(int)(rectPaint.getStrokeWidth());
                    circleReftF.set(mViewWidth / 2 - minRectSize - dif - paintdif, height / 2 - dif - minRectSize - paintdif,
                            mViewWidth / 2 + minRectSize + dif + paintdif, height / 2 + dif + minRectSize + paintdif);
                    circlePaint.setStrokeWidth((height-mDrawHeight-mStatus_1_lenght)*0.2f);
                }
                if(height>=mDrawHeight+mStatus_1_lenght+mStatus_2_lenght){
                    int paintdif=(int)(rectPaint.getStrokeWidth());
                    circleReftF.set(mViewWidth / 2 - minRectSize - dif-paintdif, height / 2 - dif - minRectSize-paintdif,
                            mViewWidth / 2 + minRectSize + dif+paintdif, height / 2 + dif + minRectSize+paintdif);
                    if(height<mDrawHeight+mStatus_1_lenght+mStatus_2_lenght+mStatus_3_lenght){

                    }
                    int left=mViewWidth / 2 - 2*(minRectSize + dif + paintdif);
                    int right=mViewWidth / 2 + 2*(minRectSize + dif + paintdif);
                    int top=mViewHeight/2-4*(minRectSize + dif + paintdif);
                    int bottom=mViewHeight/2+4*(minRectSize + dif + paintdif);
                    topRectF.set(left,height / 2 - dif - minRectSize-paintdif-20,right,bottom);
                    bottomRectF.set(left,top,right,height / 2 + dif + minRectSize+paintdif+20);
                }


        }

        /**
         * 获得交汇点的旋转度数
         * */
        private int getAngle(){
            return 0;
        }
         public void setHeight(int height){
            setRectF(height);
            if(height<mDrawHeight+mStatus_1_lenght){
                status=0;
            }
            else
            if(height>=mDrawHeight+mStatus_1_lenght&&height<mDrawHeight+mStatus_1_lenght+mStatus_2_lenght){
                status=1;

            }
            else {
                status=2;
            }
            Log.v("zxs","y height  "+smallRectF+"   "+rectPaint.getStrokeWidth());
        }
        int alpha=125;
        int status=0;
        RectF smallRectF;
        RectF circleReftF;
        Paint rectPaint;
        Paint circlePaint;

        RectF topRectF,bottomRectF;

    }

    private class MyViewHeightUpdateListener implements  ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            RefreshWeixiAnimView.this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (Integer) animation.getAnimatedValue()));
            mEye.setHeight((Integer) animation.getAnimatedValue());
        }
    }

    private class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //viewHeight=(int)mRefreshArrow.bottom_y;
            RefreshWeixiAnimView.this.invalidate();
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
 }
