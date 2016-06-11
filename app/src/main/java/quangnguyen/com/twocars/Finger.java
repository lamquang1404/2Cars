package quangnguyen.com.twocars;

import android.view.MotionEvent;

class Finger {
    float x;
    float y;
    boolean active;

    static int finger_max_nb = 20;

    static public Finger [] fingers;

    static void setup() {
        fingers = new Finger[finger_max_nb]; // here to be ready for event
        for (int f=0; f<fingers.length; f++)
            fingers[f] = new Finger();
    }

    static public void onTouchEvent(GameView mother, MotionEvent event) {
        final int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                /* Only one touch event is stored in the MotionEvent. Extract
                 * the pointer identifier of this touch from the first index
                 * within the MotionEvent object. */
                int id = event.getPointerId(0);
                fingers[id].x = event.getX(0);
                fingers[id].y = event.getY(0);
                fingers[id].active = true;

                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                /* A non-primary pointer has gone down, after an event for the
                 * primary pointer (ACTION_DOWN) has already been received.
                 * The MotionEvent object contains multiple pointers. Need to
                 * extract the index at which the data for this particular event
                 * is stored. */
                int index = event.getActionIndex();
                int id = event.getPointerId(index);
                fingers[id].x = event.getX(index);
                fingers[id].y = event.getY(index);
                fingers[id].active = true;
                break;
            }
        }
    }

    public Finger() {
        x = 0;
        y = 0;
        active = false;
    }
}
