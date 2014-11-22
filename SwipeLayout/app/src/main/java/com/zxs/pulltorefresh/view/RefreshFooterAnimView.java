package com.zxs.pulltorefresh.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zxs on 14/11/19.
 */
public class RefreshFooterAnimView extends View{
    private String mLoadMoreString="努力加载中…";
    private Paint mTextPaint;
    private int mLoadViewHeight;
    private int mViewHeight=-1,mViewWidth=-1;
    private PullToRefreshCompat pullToRefreshCompat;
	private boolean mLoadError=false;
	private String mLoadErrorText;
    public RefreshFooterAnimView(Context context) {
        this(context, null);
    }

    public RefreshFooterAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshFooterAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLoadViewHeight=(int)(context.getResources().getDisplayMetrics().density*48);
        mTextPaint=new Paint();
        mTextPaint.setColor(0XFF1ba9ba);
        mTextPaint.setTextSize(context.getResources().getDisplayMetrics().density*16);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
    }
    public void setCompat(PullToRefreshCompat arg0){
        this.pullToRefreshCompat=arg0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		if(!mLoadError){
       	 canvas.drawText(mLoadMoreString,mViewWidth/2,mLoadViewHeight/2,mTextPaint);
		}else {
			canvas.drawText(mLoadErrorText,mViewWidth/2,mLoadViewHeight/2,mTextPaint);
		}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mViewHeight==-1&&mViewWidth==-1){
            mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
            mViewWidth = MeasureSpec.getSize(widthMeasureSpec);

        }

    }

    /**
     * 设置状态为初始状态
     * */
    public void setStatusToDefault(){
        ValueAnimator animator=ValueAnimator.ofInt(this.getHeight(),0).setDuration(300);
        animator.addUpdateListener(new MyAnimUpdataListener());
        animator.addListener(new MyAnimListener(1));
        animator.start();
    }

    /**
     * 更改状态为加载更多
     * **/
    public void setStatusToLoadMore(){
        ValueAnimator animator=ValueAnimator.ofInt(0,mLoadViewHeight).setDuration(100);
        animator.addUpdateListener(new MyAnimUpdataListener());
        animator.addListener(new MyAnimListener(0));
        animator.start();
    }

    /**
     * 界面变化中的监听
     * */
    private class MyAnimUpdataListener implements ValueAnimator.AnimatorUpdateListener{


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            RefreshFooterAnimView.this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (Integer) animation.getAnimatedValue()));
            RefreshFooterAnimView.this.invalidate();
        }
    }

    /**
     * 界面状态变化的监听
     * */
     private class MyAnimListener implements Animator.AnimatorListener{
        private int action;
        public MyAnimListener(int action){
            this.action=action;
        }
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(action==1){
                pullToRefreshCompat.setRefreshStatus(0);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }


    /**
     * 设置加载更多的文案
     * **/
    public void setLoadMoreText(String text){
        mLoadMoreString=text;
        this.invalidate();
    }

	/**
	 * 设置为加载错误
	 * */
	public void setLoadError(String error){
		mLoadError=true;
		mLoadErrorText=error;
		this.invalidate();
		this.setOnClickListener(new ErrorClick());
	}

	/**
	 * 设置加载正常
	 * */
	public void setLoadNormal(){
		mLoadError=false;
		this.invalidate();
		this.setOnClickListener(null);
	}

	public class ErrorClick implements  OnClickListener{
		public void onClick(View v) {
			pullToRefreshCompat.setBottomViewToLoadingMore();
		}
	}
  }
