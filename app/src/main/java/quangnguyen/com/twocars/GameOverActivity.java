package quangnguyen.com.twocars;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameOverActivity extends Activity {
    SharedPreferences best_score_prefs;
    MediaPlayer failSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Sound when lose
        failSound = MediaPlayer.create(GameOverActivity.this, R.raw.fail);
        failSound.setVolume(100, 100);

        // update icon background sound
        final ImageButton Button_BackgroundSound = (ImageButton) findViewById(R.id.BackgroundSound_gameOver);
        if (MainActivity.isPlayBackgroundSound) {
            failSound.start();
            Button_BackgroundSound.setBackgroundResource(R.drawable.icon_background_sound);
        } else Button_BackgroundSound.setBackgroundResource(R.drawable.icon_background_sound_off);
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
                            failSound.release();
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

        // update icon sound
        final ImageButton Button_Sound = (ImageButton) findViewById(R.id.Sound_gameOver);
        if (MainActivity.isPlaySound)
            Button_Sound.setBackgroundResource(R.drawable.icon_sound);
        else Button_Sound.setBackgroundResource(R.drawable.icon_sound_off);
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


        // update score
        final Intent intent = getIntent();
        Bundle values = intent.getExtras();
        int points = values.getInt("score");
        TextView Score = (TextView) findViewById(R.id.Score);
        Score.setText(points + "");

        // update best score
        best_score_prefs = getSharedPreferences("BestScore", Context.MODE_PRIVATE);
        if (best_score_prefs.contains("Score")) {
            int best_score = Integer.valueOf(best_score_prefs.getString("Score", ""));
            if (points > best_score) {
                // save to best score
                SharedPreferences.Editor editor = best_score_prefs.edit();
                editor.putString("Score", points + "");
                editor.commit();
                TextView BestScore = (TextView) findViewById(R.id.BestScore);
                BestScore.setText(points + "*");
            } else {
                TextView BestScore = (TextView) findViewById(R.id.BestScore);
                BestScore.setText(best_score + "");
            }
        } else {
            // save to best score if it isn't have best score
            SharedPreferences.Editor editor = best_score_prefs.edit();
            editor.putString("Score", points + "");
            editor.commit();
            TextView BestScore = (TextView) findViewById(R.id.BestScore);
            BestScore.setText(points + "*");
        }

        // restart game
        final ImageButton restart_button = (ImageButton) findViewById(R.id.restart_button);
        restart_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        restart_button.setScaleX(1.5f);
                        restart_button.setScaleY(1.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        restart_button.setScaleX(1f);
                        restart_button.setScaleY(1f);
                        if (MainActivity.isPlayBackgroundSound)
                            failSound.release();
                        MainActivity.FirstActivity.finish();
                        MainActivity.restart = true;
                        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                        startActivity(intent);
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
                        Intent howtoplay = new Intent(GameOverActivity.this, HowToPlayActivity.class);
                        startActivity(howtoplay);
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
                        Intent aboutus = new Intent(GameOverActivity.this, AboutUsActivity.class);
                        startActivity(aboutus);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        failSound.release();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
