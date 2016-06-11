package quangnguyen.com.twocars;

import android.graphics.Rect;

/**
 * Created by Thanh Nguyen on 28-May-16.
 */
public class Objects {
    public boolean active;
    public boolean isCircle;
    public boolean counted;
    public float top;
    public float bottom;
    public float left;
    public float right;

    public Objects() {
        active = true;
        isCircle=false;
        counted = false;
        top = 0;
        bottom =0;
        left =0;
        right =0;
    }

    public boolean contains(float x, float y){
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        return rect.contains((int)x,(int)y);
    }
}
