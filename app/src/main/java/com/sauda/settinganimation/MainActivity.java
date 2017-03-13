package com.sauda.settinganimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private GestureDetectorCompat mDetector;
    private RelativeLayout mainLAyout;

    private static final String DEBUG_TAG = "Gestures";

    public static final long DEFAULT_ANIMATION_DURATION = 1000L;
    private static final float DRAG_RATE = .5f;
    private static final int INVALID_POINTER = -1;



    private boolean isEnable;
    int dragDistance = 0;
    private float mInitialDownY;
    private float mInitialMotionY;
    private int mActivePointerId = INVALID_POINTER;


    private boolean mIsBeingDragged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /*void SwipListener() {

        final View swipeTarget = swipeRefreshLayout.getChildAt(0);

        swipeTarget.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                dragDistance = swipeTarget.getTop() - swipeRefreshLayout.getTop();
                return true;
            }
        });
    }*/

    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        mainLAyout = (RelativeLayout) findViewById(R.id.mainLAyout);
        mDetector = new GestureDetectorCompat(this,onSwipeListener );
        // swipeRefreshLayout.setVisibility(View.INVISIBLE);
        //swipeRefreshLayout.setRefreshing(false);

       // SwipListener();
    }


    OnSwipeListener onSwipeListener = new OnSwipeListener() {

        @Override
        public boolean onSwipe(Direction direction) {

            if(direction == Direction.left|| direction == Direction.right) {
                // Do something COOL like animation or whatever you want
                // Refer to your view if needed using a global reference
                return true;
            }
            else if(direction == Direction.up)
            {
                startAnimationFling();



                return true;

            }else if(direction == Direction.down)
            {
                Log.d(DEBUG_TAG,"onDown: " );

               startAnimation();

                //startAnimationFling();

                // Do something COOL like animation or whatever you want
                // Refer to your view if needed using a global reference
                return true;
            }

            return super.onSwipe(direction);
        }};
    @Override
    public boolean onTouchEvent(MotionEvent ev){
        /*final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                mActivePointerId = ev.getPointerId(0);

                break;

            case MotionEvent.ACTION_MOVE: {

                pointerIndex = ev.findPointerIndex(mActivePointerId);

                final float y = ev.getY(pointerIndex);
                startDragging(y);

                if (mIsBeingDragged) {
                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                    if (overscrollTop > 0) {
                        startAnimation();
                    } else {
                        return false;
                    }
                }
                mActivePointerId = ev.getPointerId(pointerIndex);

                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    Log.e(DEBUG_TAG,
                            "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                pointerIndex = ev.findPointerIndex(mActivePointerId);

                startAnimationFling();
                break;

            case MotionEvent.ACTION_UP: {

                if (mIsBeingDragged) {
                    pointerIndex = ev.findPointerIndex(mActivePointerId);

                    final float y = ev.getY(pointerIndex);
                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                    mIsBeingDragged = false;

                    startAnimationFling();

                    //finishSpinner(overscrollTop);
                    *//*Finish spinning*//*
                }
                mActivePointerId = INVALID_POINTER;

                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                return false;
        }*/
        this.mDetector.onTouchEvent(ev);
        return true;
    }
    @SuppressLint("NewApi")
    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if ( !mIsBeingDragged) {
            mInitialMotionY = mInitialDownY ;
            mIsBeingDragged = true;
            startAnimation();
        }
    }





   /* class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            isEnable = true;
            startAnimation();
            //image.animate().rotationX(10).start();
            invalidateOptionsMenu();
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            isEnable = false;
            startAnimationFling();
            invalidateOptionsMenu();
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());

            return true;
        }
    }*/

    private void startAnimation() {

        if(image.animate()!= null) image.animate().cancel();
        image.clearAnimation();

        ValueAnimator positionAnimator = ValueAnimator.ofFloat(0, -200);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                image.setTranslationX(value);
            }
        });

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(image, "rotation", 0, 180f);

        AnimatorSet animatorSet = new AnimatorSet();


        animatorSet.play(positionAnimator).with(rotationAnimator);

        animatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorSet.start();
    }
    private void startAnimationFling() {
        if(image.animate()!= null) image.animate().cancel();
        image.clearAnimation();


        ValueAnimator positionAnimator = ValueAnimator.ofFloat(0, 200);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                image.setTranslationX(value);


            }
        });


        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(image, "rotation", 0f, 180f);
       /* positionAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                animation.removeListener(this);
                animation.setDuration(0);
                ((ValueAnimator) animation).reverse();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });*/
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(image, "alpha", 1f, 0f);

        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                animation.removeListener(this);
                animation.setDuration(0);
                ((ValueAnimator) animation).reverse();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(1000);


        //image.setAnimation(fadeIn);

       final AnimatorSet animatorSet = new AnimatorSet();

       // animatorSet.play(positionAnimator).with(rotationAnimator).before(alphaAnimator);
        animatorSet.playTogether(positionAnimator,rotationAnimator,alphaAnimator);


        animatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
        animatorSet.start();

    }
}
