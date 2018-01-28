package com.chandilsachin.notely.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {


    public ScrollAwareFABBehavior() {
        super();
    }

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mXonTouchDown;
    private int mXonTouchUp;

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, FloatingActionButton child, MotionEvent ev) {


        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mXonTouchDown = (int) ev.getY();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            mXonTouchUp = (int) ev.getY();
        }
        if(mXonTouchDown > mXonTouchUp)
            child.hide();
        else
            child.show();

        return super.onInterceptTouchEvent(parent, child, ev);
    }
}
