package quangnguyen.com.twocars;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

public class MainActivity extends Activity {
    public static MediaPlayer background_media;
    public static boolean isPlayBackgroundSound = true;
    public static boolean isPlaySound = true;
    public static boolean restart=false;
    public static Activity FirstActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstActivity = this; // for close this activity from another activity

        // play background music random
        int n = Math.abs(new Random().nextInt() % 3);
        if (n == 0)
            background_media = MediaPlayer.create(MainActivity.this, R.raw.spectre);
        else if (n == 1)
            background_media = MediaPlayer.create(MainActivity.this, R.raw.fade);
        else background_media = MediaPlayer.create(MainActivity.this, R.raw.force);
        background_media.setLooping(true);
        background_media.setVolume(100, 100);

        final GameView gameView = (GameView) findViewById(R.id.GameView);
        if(!restart) {
            // start activity_start for the first time
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
        }
        else {
            // restart
            gameView.dy_Mouvement = Parametres.dy_Mouvement;
            if (isPlayBackgroundSound) MainActivity.background_media.start();
        }

        // pause game
        final ImageButton pause_button = (ImageButton) findViewById(R.id.pause_button);
        pause_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        pause_button.setScaleX(1.5f);
                        pause_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        pause_button.setScaleX(1f);
                        pause_button.setScaleY(1f);
                        Intent intent = new Intent(MainActivity.this, PauseActivity.class);
                        intent.putExtra("dy", gameView.dy_Mouvement);
                        gameView.dy_Mouvement = 0;
                        startActivity(intent);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        MainActivity.background_media.pause();
        Intent intent = new Intent(MainActivity.this,PauseActivity.class);
        GameView gameView = (GameView) MainActivity.this.findViewById(R.id.GameView);
        intent.putExtra("dy",gameView.dy_Mouvement);
        gameView.dy_Mouvement = 0;
        startActivity(intent);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
