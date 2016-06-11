package quangnguyen.com.twocars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class PauseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        MainActivity.background_media.pause();
        final ImageButton resume_button = (ImageButton) findViewById(R.id.resume_button);
        resume_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        resume_button.setScaleX(1.5f);
                        resume_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        resume_button.setScaleX(1f);
                        resume_button.setScaleY(1f);
                        onBackPressed();
                    }
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        // resume game
        View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_main, null);
        GameView gameView = (GameView) view.findViewById(R.id.GameView);
        Intent intent = getIntent();
        Bundle values = intent.getExtras();
        gameView.dy_Mouvement = values.getFloat("dy");
        if (MainActivity.isPlayBackgroundSound) MainActivity.background_media.start();
        finish();
    }
}
