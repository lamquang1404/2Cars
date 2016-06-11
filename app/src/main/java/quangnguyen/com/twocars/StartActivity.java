package quangnguyen.com.twocars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // start game
        final ImageButton play_button = (ImageButton) findViewById(R.id.play_button);
        play_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        play_button.setScaleX(1.5f);
                        play_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        play_button.setScaleX(1f);
                        play_button.setScaleY(1f);
                        View view = LayoutInflater.from(getApplication()).inflate(R.layout.activity_main, null);
                        GameView gameView = (GameView) view.findViewById(R.id.GameView);
                        gameView.dy_Mouvement = Parametres.dy_Mouvement;
                        if (MainActivity.isPlayBackgroundSound)
                            MainActivity.background_media.start();
                        finish();
                    }
                }
                return true;
            }
        });

        // how to play
        final ImageButton how_to_play_button = (ImageButton) findViewById(R.id.HowToPlay);
        how_to_play_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        how_to_play_button.setScaleX(1.5f);
                        how_to_play_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        how_to_play_button.setScaleX(1f);
                        how_to_play_button.setScaleY(1f);
                        Intent howtoplay = new Intent(StartActivity.this, HowToPlayActivity.class);
                        startActivity(howtoplay);
                    }
                }
                return true;
            }
        });

        // game music
        final ImageButton Button_BackgroundSound = (ImageButton) findViewById(R.id.BackgroundSound);
        Button_BackgroundSound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button_BackgroundSound.setScaleX(1.5f);
                        Button_BackgroundSound.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Button_BackgroundSound.setScaleX(1f);
                        Button_BackgroundSound.setScaleY(1f);
                        if (MainActivity.isPlayBackgroundSound) {
                            MainActivity.isPlayBackgroundSound = false;
                            Button_BackgroundSound.setBackgroundResource(R.drawable.icon_background_sound_off);
                        } else {
                            MainActivity.isPlayBackgroundSound = true;
                            Button_BackgroundSound.setBackgroundResource(R.drawable.icon_background_sound);
                        }
                    }
                }
                return true;
            }
        });

        // object's sound
        final ImageButton Button_Sound = (ImageButton) findViewById(R.id.Sound);
        Button_Sound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button_Sound.setScaleX(1.5f);
                        Button_Sound.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Button_Sound.setScaleX(1f);
                        Button_Sound.setScaleY(1f);
                        if (MainActivity.isPlaySound) {
                            MainActivity.isPlaySound = false;
                            Button_Sound.setBackgroundResource(R.drawable.icon_sound_off);
                        } else {
                            MainActivity.isPlaySound = true;
                            Button_Sound.setBackgroundResource(R.drawable.icon_sound);
                        }

                    }
                }
                return true;
            }
        });

        // about us
        final ImageButton about_us_button = (ImageButton) findViewById(R.id.AboutUs);
        about_us_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        about_us_button.setScaleX(1.5f);
                        about_us_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        about_us_button.setScaleX(1f);
                        about_us_button.setScaleY(1f);
                        Intent aboutus = new Intent(StartActivity.this, AboutUsActivity.class);
                        startActivity(aboutus);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}