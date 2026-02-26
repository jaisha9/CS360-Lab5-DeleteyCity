package com.example.lab5_starter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class SwipeListView extends ListView {

    public interface OnSwipeListener {
        void onSwipeRight(int position);
    }

    private GestureDetector gestureDetector;
    private OnSwipeListener onSwipeListener;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_MIN_VELOCITY = 100;

    public SwipeListView(Context context) {
        super(context);
        init(context);
    }

    public SwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        if (e1 == null || e2 == null) return false;

                        float deltaX = e2.getX() - e1.getX();
                        float deltaY = e2.getY() - e1.getY();

                        if (deltaX > SWIPE_MIN_DISTANCE
                                && Math.abs(deltaY) < SWIPE_MAX_OFF_PATH
                                && velocityX > SWIPE_MIN_VELOCITY) {

                            int position = pointToPosition((int) e1.getX(), (int) e1.getY());
                            if (position != INVALID_POSITION && onSwipeListener != null) {
                                onSwipeListener.onSwipeRight(position);
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    public void setOnSwipeListener(OnSwipeListener listener) {
        this.onSwipeListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Feed the gesture detector first, before ListView consumes the event
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}