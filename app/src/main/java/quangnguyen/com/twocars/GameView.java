package quangnguyen.com.twocars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameView extends View {
    float left_RightCar, right_RightCar, left_LeftCar, right_LeftCar, top_Car, bottom_Car;
    float nitroLeft_lenght, nitroRight_lenght, nitro_length;
    float spaceToCar, dx_Mouvement;
    public static float dy_Mouvement;
    int score; // score

    boolean setup;
    boolean isMoveLeft_RightCar;
    boolean isMoveRight_RightCar;
    boolean isMoveLeft_LeftCar;
    boolean isMoveRight_LeftCar;

    boolean isRotateRight_RightCar;
    boolean isRotateLeft_RightCar;
    boolean isRotateRight_LeftCar;
    boolean isRotateLeft_LeftCar;

    boolean end_game = false;

    Bitmap Line_img, LeftCar_img, RightCar_img, Rect_Left_img, Circle_Left_img, Rect_Right_img, Circle_Right_img, Nitro_Left_img, Nitro_Right_img;

    static MediaPlayer eatSoundLeft;
    static MediaPlayer eatSoundRight;

    Objects[] objects_left = new Objects[3];
    Objects[] objects_right = new Objects[3];
    Objects leftNitro = new Objects();
    Objects rightNitro = new Objects();

    float largeur_objet;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Finger.setup();
        setup = true;
        Line_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.line)).getBitmap();
        LeftCar_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.redcar)).getBitmap();
        RightCar_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.bluecar)).getBitmap();
        Rect_Left_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.redsquare)).getBitmap();
        Circle_Left_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.redcircle)).getBitmap();
        Rect_Right_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.bluesquare)).getBitmap();
        Circle_Right_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.bluecircle)).getBitmap();
        Nitro_Left_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.nitrored)).getBitmap();
        Nitro_Right_img = ((BitmapDrawable) getResources().getDrawable(R.drawable.nitroblue)).getBitmap();
        eatSoundLeft = MediaPlayer.create(context, R.raw.coin);
        eatSoundLeft.setVolume(50, 50);
        eatSoundRight = MediaPlayer.create(context, R.raw.coin);
        eatSoundRight.setVolume(50, 50);
    }

    // délégation de la gestion des touches à Finger.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Finger.onTouchEvent(this, event);
        return true;
    }

    private boolean RandomForm() {
        int n = Math.abs(new Random().nextInt() % 2); // Gives n such that 0 <= n < 1
        return (n==0);
    }

    private void run_setup(Canvas canvas) {
        dx_Mouvement = Parametres.dx_Mouvement;
        dy_Mouvement = Math.max(dy_Mouvement, 0); // for continue play if restart
        spaceToCar = canvas.getWidth() / 25;
        score = 0;
        largeur_objet = canvas.getWidth() / 4 - 4 * spaceToCar;
        nitroLeft_lenght = 0;
        nitroRight_lenght = 0;
        // initialize objects
        for (int i = 0; i < objects_left.length; i++) {
            // object of each side
            objects_left[i] = new Objects();
            objects_right[i] = new Objects();

            // Random side of objects
            int n = Math.abs(new Random().nextInt() % 2); // Gives n such that 0 <= n < 1

            // Note: easy for 3 first wave object
            if (n == 0) // left side of each big side
            {
                objects_left[i].left = 2 * spaceToCar;
                objects_left[i].right = objects_left[i].left + largeur_objet;

                objects_right[i].left = (canvas.getWidth() / 2) + 2 * spaceToCar;
                objects_right[i].right = objects_right[i].left + largeur_objet;
            } else { // right side of each big side
                objects_left[i].left = canvas.getWidth() / 4 + 2 * spaceToCar;
                objects_left[i].right = objects_left[i].left + largeur_objet;

                objects_right[i].left = (canvas.getWidth() / 4) * 3 + 2 * spaceToCar;
                objects_right[i].right = objects_right[i].left + largeur_objet;
            }

            // random form circle-rectangle of each side
            objects_left[i].isCircle = RandomForm();
            objects_right[i].isCircle = RandomForm();
        }

        // position of left side's object
        objects_left[0].top = -100;
        objects_left[0].bottom = objects_left[0].top + largeur_objet;
        objects_left[1].top = -100 - 2 * canvas.getHeight() / 5;
        objects_left[1].bottom = objects_left[1].top + largeur_objet;
        objects_left[2].top = -100 - 4 * canvas.getHeight() / 5;
        objects_left[2].bottom = objects_left[2].top + largeur_objet;

        // position of right side's object
        objects_right[0].top = objects_left[0].top - 100;
        objects_right[0].bottom = objects_right[0].top + largeur_objet;
        objects_right[1].top = objects_left[1].top - 100;
        objects_right[1].bottom = objects_right[1].top + largeur_objet;
        objects_right[2].top = objects_left[2].top - 100;
        objects_right[2].bottom = objects_right[2].top + largeur_objet;

        // coord similar of 2 cars
        top_Car = (canvas.getHeight() - 2 * canvas.getHeight() / 9);
        bottom_Car = top_Car + 1.5f*largeur_objet;

        // coord of left car
        left_LeftCar = 2 * spaceToCar;
        right_LeftCar = (canvas.getWidth() / 4 - 2 * spaceToCar);

        // coord of right car
        left_RightCar = ((canvas.getWidth() / 4) * 3 + 2 * spaceToCar);
        right_RightCar = (canvas.getWidth() - 2 * spaceToCar);

        nitro_length = (bottom_Car-top_Car)/5;
    }

    private void draw_lines(Canvas canvas) {
        Paint paint = new Paint();

        // draw lines
        Rect LineLeft = new Rect();
        LineLeft.set((int) (canvas.getWidth() / 4 - 5), 0, (int) (canvas.getWidth() / 4 + 5), (int) (canvas.getHeight()));
        canvas.drawBitmap(Line_img, null, LineLeft, paint);

        Rect LineMid = new Rect();
        LineMid.set((int) (canvas.getWidth() / 2 - 10), 0, (int) (canvas.getWidth() / 2 + 10), (int) (canvas.getHeight()));
        canvas.drawBitmap(Line_img, null, LineMid, paint);

        Rect LineRight = new Rect();
        LineRight.set((int) (canvas.getWidth() / 4 * 3 - 5), 0, (int) (canvas.getWidth() / 4 * 3 + 5), (int) (canvas.getHeight()));
        canvas.drawBitmap(Line_img, null, LineRight, paint);
    }

    private void draw_object(Canvas canvas, int i) {
        Paint paint = new Paint();
        Rect rect_left = new Rect((int) objects_left[i].left, (int) objects_left[i].top, (int) objects_left[i].right, (int) objects_left[i].bottom);
        Rect rect_right = new Rect((int) objects_right[i].left, (int) objects_right[i].top, (int) objects_right[i].right, (int) objects_right[i].bottom);
        // draw circle or rectangle of left side
        if (objects_left[i].active) {
            if (objects_left[i].isCircle) {
                canvas.drawBitmap(Circle_Left_img, null, rect_left, paint);
                // fin jeu si le LeftCar n'attrape pas le circle
                if (objects_left[i].top > bottom_Car) {
                    if (!end_game) EndGame();
                }
            } else canvas.drawBitmap(Rect_Left_img, null, rect_left, paint);
        }

        // draw circle or rectangle of right side
        if (objects_right[i].active) {
            if (objects_right[i].isCircle) {
                canvas.drawBitmap(Circle_Right_img, null, rect_right, paint);
                // fin jeu si le RightCar n'attrape pas le circle
                if (objects_right[i].top > bottom_Car) {
                    if (!end_game) EndGame();
                }
            } else canvas.drawBitmap(Rect_Right_img, null, rect_right, paint);
        }
    }

    private boolean isImpact_LeftSide(int i) {
        return objects_left[i].contains(left_LeftCar, (top_Car + bottom_Car) / 2) ||      // contain
                objects_left[i].contains((left_LeftCar + right_LeftCar) / 2, top_Car) ||  // les milieux points
                objects_left[i].contains(right_LeftCar, (top_Car + bottom_Car) / 2) ||    // de chaque côté.
                objects_left[i].contains(left_LeftCar, top_Car) ||                        // contain
                objects_left[i].contains(left_LeftCar, bottom_Car) ||                     // les points
                objects_left[i].contains(right_LeftCar, top_Car) ||                       // du coin
                objects_left[i].contains(right_LeftCar, bottom_Car);                      // de chaque côté.
    }

    private boolean isImpact_RightSide(int i) {
        return objects_right[i].contains(left_RightCar, (top_Car + bottom_Car) / 2) ||      // contain
                objects_right[i].contains((left_RightCar + right_RightCar) / 2, top_Car) || // les milieux points
                objects_right[i].contains(right_RightCar, (top_Car + bottom_Car) / 2) ||    // de chaque côté.
                objects_right[i].contains(left_RightCar, top_Car) ||                        // contain
                objects_right[i].contains(left_RightCar, bottom_Car) ||                     // les points
                objects_right[i].contains(right_RightCar, top_Car) ||                       // du coin
                objects_right[i].contains(right_RightCar, bottom_Car);                      // de chaque côté.
    }

    private void EndGame() {
        leftNitro.active = false;
        rightNitro.active = false;
        end_game = true;
        MainActivity.background_media.release();
        if (MainActivity.isPlaySound) {
            eatSoundRight.release();
            eatSoundLeft.release();
        }
        Context context = getContext();
        Intent intent = new Intent(context, GameOverActivity.class);
        intent.putExtra("score", score);
        context.startActivity(intent);
        dy_Mouvement = 0;
        dx_Mouvement = 0;
    }

    private void ReUpdate_Object(Canvas canvas, int i) {
        // re-initialise la position d'objets à gauche si il est passer
        if (objects_left[i].top >= canvas.getHeight()) {
            if (i == 0) {
                objects_left[0].top = objects_left[2].top - 2 * canvas.getHeight() / 5;
                objects_left[0].bottom = objects_left[2].bottom - 2 * canvas.getHeight() / 5;
            } else {
                objects_left[i].top = objects_left[i - 1].top - 2 * canvas.getHeight() / 5;
                objects_left[i].bottom = objects_left[i - 1].bottom - 2 * canvas.getHeight() / 5;
            }

            // Random side
            int n = Math.abs(new Random().nextInt() % 2); // Gives n such that 0 <= n < 1
            if (n == 0) // relocation in left side
            {
                objects_left[i].left = 2 * spaceToCar;
                objects_left[i].right = canvas.getWidth() / 4 - 2 * spaceToCar;
            } else {    // relocation in right side
                objects_left[i].left = canvas.getWidth() / 4 + 2 * spaceToCar;
                objects_left[i].right = canvas.getWidth() / 2 - 2 * spaceToCar;
            }
            objects_left[i].isCircle = RandomForm();
            objects_left[i].active = true;
            objects_left[i].counted = false;
        }

        // re-initialise la position d'objets à droite si il est passer
        if (objects_right[i].top >= canvas.getHeight()) {
            if (i == 0) {
                objects_right[0].top = objects_right[2].top - 2 * canvas.getHeight() / 5;
                objects_right[0].bottom = objects_right[2].bottom - 2 * canvas.getHeight() / 5;
            } else {
                objects_right[i].top = objects_right[i - 1].top - 2 * canvas.getHeight() / 5;
                objects_right[i].bottom = objects_right[i - 1].bottom - 2 * canvas.getHeight() / 5;
            }

            // Random side
            int n = Math.abs(new Random().nextInt() % 2); // Gives n such that 0 <= n < 1
            if (n == 0) // relocation in left side
            {
                objects_right[i].left = (canvas.getWidth() / 2) + 2 * spaceToCar;
                objects_right[i].right = (canvas.getWidth() / 4) * 3 - 2 * spaceToCar;
            } else {    // relocation in right side
                objects_right[i].left = (canvas.getWidth() / 4) * 3 + 2 * spaceToCar;
                objects_right[i].right = canvas.getWidth() - 2 * spaceToCar;
            }
            objects_right[i].isCircle = RandomForm();
            objects_right[i].active = true;
            objects_right[i].counted = false;
        }
    }

    protected void onDraw(Canvas canvas) {
        if (setup) {
            run_setup(canvas);
            setup = false;
        }
        draw_lines(canvas);
        // handle event of objets
        for (int i = 0; i < objects_left.length; i++) {
            Move_Object(i);
            Impact_Object(i,canvas);
            draw_object(canvas, i);
            if (!end_game) {
                this.postInvalidate();
                ReUpdate_Object(canvas, i);
            }
        }
        ChangeSide_Car(canvas);
        Move_Car(canvas);
        Draw_Cars(canvas);
        UpdateScore();
        if (!end_game) this.postInvalidate();
    }

    private void Move_Object(int i) {
        // Mouvement d'objects left
        objects_left[i].top += dy_Mouvement;
        objects_left[i].bottom = objects_left[i].top + largeur_objet;

        // Mouvement d'objects right
        objects_right[i].top += dy_Mouvement;
        objects_right[i].bottom = objects_right[i].top + largeur_objet;
    }

    private void Impact_Object(int i,Canvas canvas) {
        // impact of object on left side
        if (isImpact_LeftSide(i)) {
            if (objects_left[i].isCircle) {
                objects_left[i].active = false;
                if (!objects_left[i].counted) {
                    if (MainActivity.isPlaySound) eatSoundLeft.start();
                    score += 1;
                    dy_Mouvement += 0.1;
                    nitro_length = Math.min(nitro_length+3, 3*(canvas.getHeight()-bottom_Car)/5);
                    objects_left[i].counted = true;
                }
            } else {
                if (!end_game) {
                    EndGame();
                    return;
                }
            }
        }

        // impact of object on right side
        if (isImpact_RightSide(i)) {
            if (objects_right[i].isCircle) {
                objects_right[i].active = false;
                if (!objects_right[i].counted) {
                    if (MainActivity.isPlaySound) eatSoundRight.start();
                    score += 1;
                    dy_Mouvement += 0.1;
                    nitro_length = Math.min(nitro_length+3, 3*(canvas.getHeight()-bottom_Car)/5);
                    objects_right[i].counted = true;
                }
            } else {
                if (!end_game) EndGame();
            }
        }
    }

    private void ChangeSide_Car(Canvas canvas) {
        // change side of car for each touch
        for (Finger f : Finger.fingers) {
            if (f.active) {
                if (f.x > canvas.getWidth() / 2) {
                    // Event mouvement de RightCar
                    if (left_RightCar > ((canvas.getWidth() / 4) * 3)) {
                        isMoveLeft_RightCar = true;
                        isRotateLeft_RightCar = true;
                        isMoveRight_RightCar = false;
                        isRotateRight_RightCar = false;
                    } else {
                        isMoveLeft_RightCar = false;
                        isRotateLeft_RightCar = false;
                        isMoveRight_RightCar = true;
                        isRotateRight_RightCar = true;
                    }
                } else {
                    // Event mouvement de LeftCar
                    if (left_LeftCar > (canvas.getWidth() / 4)) {
                        isMoveLeft_LeftCar = true;
                        isRotateLeft_LeftCar = true;
                        isMoveRight_LeftCar = false;
                        isRotateRight_LeftCar = false;
                    } else {
                        isMoveLeft_LeftCar = false;
                        isRotateLeft_LeftCar = false;
                        isMoveRight_LeftCar = true;
                        isRotateRight_LeftCar = true;
                    }
                }
                f.active = false;
            }
        }
    }

    private void Move_Car(Canvas canvas) {
        // coord of nitro left car and move the nitro
        nitroLeft_lenght+=5;
        leftNitro.left = left_LeftCar + (right_LeftCar - left_LeftCar) / 3;
        leftNitro.right = left_LeftCar + ((right_LeftCar - left_LeftCar) / 3) * 2;
        leftNitro.top = bottom_Car;
        leftNitro.bottom = leftNitro.top + Math.min(nitroLeft_lenght,nitro_length);

        // coord of nitro right car move the nitro
        nitroRight_lenght+=5;
        rightNitro.left = left_RightCar + (right_RightCar - left_RightCar) / 3;
        rightNitro.right = left_RightCar + ((right_RightCar - left_RightCar) / 3) * 2;
        rightNitro.top = bottom_Car;
        rightNitro.bottom = rightNitro.top + Math.min(nitroRight_lenght,nitro_length);

        // Mouvement de RightCar
        if (isMoveLeft_RightCar) {
            left_RightCar = Math.max(left_RightCar - dx_Mouvement, canvas.getWidth() / 2 + 2 * spaceToCar);
            right_RightCar = Math.max(right_RightCar - dx_Mouvement, canvas.getWidth() / 4 * 3 - 2 * spaceToCar);
            isRotateLeft_RightCar = true;
            rightNitro.active = false;
            nitroRight_lenght = 0;
            if (left_RightCar == canvas.getWidth() / 2 + 2 * spaceToCar) {
                isMoveLeft_RightCar = false;
                isRotateLeft_RightCar = false;
                rightNitro.active = true;
            }
        }
        if (isMoveRight_RightCar) {
            left_RightCar = Math.min(left_RightCar + dx_Mouvement, 2 * spaceToCar + (canvas.getWidth() / 4) * 3);
            right_RightCar = Math.min(right_RightCar + dx_Mouvement, canvas.getWidth() - 2 * spaceToCar);
            isRotateRight_RightCar = true;
            rightNitro.active = false;
            nitroRight_lenght = 0;
            if (right_RightCar == canvas.getWidth() - 2 * spaceToCar) {
                isMoveRight_RightCar = false;
                isRotateRight_RightCar = false;
                rightNitro.active = true;
            }
        }

        // Mouvement de LeftCar
        if (isMoveLeft_LeftCar) {
            left_LeftCar = Math.max(left_LeftCar - dx_Mouvement, 2 * spaceToCar);
            right_LeftCar = Math.max(right_LeftCar - dx_Mouvement, canvas.getWidth() / 4 - 2 * spaceToCar);
            isRotateLeft_LeftCar = true;
            leftNitro.active = false;
            nitroLeft_lenght = 0;
            if (left_LeftCar == 2 * spaceToCar) {
                isMoveLeft_LeftCar = false;
                isRotateLeft_LeftCar = false;
                leftNitro.active = true;
            }
        }
        if (isMoveRight_LeftCar) {
            left_LeftCar = Math.min(left_LeftCar + dx_Mouvement, canvas.getWidth() / 4 + 2 * spaceToCar);
            right_LeftCar = Math.min(right_LeftCar + dx_Mouvement, canvas.getWidth() / 2 - 2 * spaceToCar);
            isRotateRight_LeftCar = true;
            leftNitro.active = false;
            nitroLeft_lenght = 0;
            if (left_LeftCar == canvas.getWidth() / 4 + 2 * spaceToCar) {
                isMoveRight_LeftCar = false;
                isRotateRight_LeftCar = false;
                leftNitro.active = true;
            }
        }
    }

    private void UpdateScore() {
        RelativeLayout r = (RelativeLayout) this.getParent();
        TextView Score = (TextView) r.findViewById(R.id.Score_game);
        Score.setText(String.valueOf(score));
    }

    private void Draw_Cars(Canvas canvas) {
        Paint paint = new Paint();

        // left car
        Rect LeftCar = new Rect();
        LeftCar.set((int) left_LeftCar, (int) top_Car, (int) right_LeftCar, (int) bottom_Car);
        // nitro behind left card
        Rect NitroLeft = new Rect();
        NitroLeft.set((int) leftNitro.left, (int) leftNitro.top, (int) leftNitro.right, (int) leftNitro.bottom);
        if (isRotateRight_LeftCar) {
            canvas.save();
            canvas.rotate(35, (left_LeftCar + right_LeftCar) / 2, (top_Car + bottom_Car) / 2);
            canvas.drawBitmap(LeftCar_img, null, LeftCar, paint);
            canvas.restore();
        } else if (isRotateLeft_LeftCar) {
            canvas.save();
            canvas.rotate(-35, (left_LeftCar + right_LeftCar) / 2, (top_Car + bottom_Car) / 2);
            canvas.drawBitmap(LeftCar_img, null, LeftCar, paint);
            canvas.restore();
        } else {
            canvas.drawBitmap(LeftCar_img, null, LeftCar, paint);
            if (leftNitro.active)
                canvas.drawBitmap(Nitro_Left_img, null, NitroLeft, paint);
        }

        // right car
        Rect RightCar = new Rect();
        RightCar.set((int) left_RightCar, (int) top_Car, (int) right_RightCar, (int) bottom_Car);
        // nitro behind right car
        Rect NitroRight = new Rect();
        NitroRight.set((int) rightNitro.left, (int) rightNitro.top, (int) rightNitro.right, (int) rightNitro.bottom);
        if (isRotateRight_RightCar) {
            canvas.save();
            canvas.rotate(35, (left_RightCar + right_RightCar) / 2, (top_Car + bottom_Car) / 2);
            canvas.drawBitmap(RightCar_img, null, RightCar, paint);
            canvas.restore();
        } else if (isRotateLeft_RightCar) {
            canvas.save();
            canvas.rotate(-35, (left_RightCar + right_RightCar) / 2, (top_Car + bottom_Car) / 2);
            canvas.drawBitmap(RightCar_img, null, RightCar, paint);
            canvas.restore();
        } else {
            canvas.drawBitmap(RightCar_img, null, RightCar, paint);
            if (rightNitro.active)
                canvas.drawBitmap(Nitro_Right_img, null, NitroRight, paint);
        }
    }
}
