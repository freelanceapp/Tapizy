package ibt.com.tapizy.utils.move_listener;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MultiTouchListener implements OnTouchListener {

    private float mPrevX;
    private float mPrevY;
    float lastX;

    public Activity mainActivity;

    public MultiTouchListener(Activity mainActivity1) {
        mainActivity = mainActivity1;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                v.scrollBy((int) (event.getX() - lastX), 0);
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                v.scrollTo(0, 0);
        }
        return false;
    }

/*
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currX, currY;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mPrevX = event.getX();
                mPrevY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                currX = event.getRawX();
                currY = event.getRawY();

                MarginLayoutParams marginParams = new MarginLayoutParams(view.getLayoutParams());
                marginParams.setMargins((int) (currX - mPrevX), (int) (currY - mPrevY), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                view.setLayoutParams(layoutParams);
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
*/
}
