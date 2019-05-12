package com.yaniv.student.project2;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import android.provider.ContactsContract;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class ViewGame extends View
{
    private Handler handler;
    private Thread thread;
    private AudioThread audioThread;
    private boolean isFalling = true , IsOutside = false , isSet = false , isRun = true , isToBack = false;
    private Context context;
    private int points = 0 , life = 3 , touch = 0 , musicVol , fxVol;
    private MediaPlayer pingpong , blyat , sonof;
    private double  R_a  ,Y_a  , X_a  , adderY_a = 0   , X_b  , adderX_b  , R_b  , Y_b , FallAx , WidthC , HightC;
    private Paint  pp = new Paint() ;
    private Bitmap putin , trump , heart;
    private SharedPreferences sharedPreferences;


    GameThead Gm;

    public ViewGame(Context context , int musicVol , int fxVol)
    {
        super(context);
        sharedPreferences = context.getSharedPreferences("bestS" , Context.MODE_PRIVATE);
        this.musicVol = musicVol;
        this.fxVol = fxVol;
        this.context=context;
        putin = BitmapFactory.decodeResource(getResources() , R.drawable.putin);
        heart = BitmapFactory.decodeResource(getResources() , R.drawable.heart);
        audioThread = new AudioThread(MediaPlayer.create(context , R.raw.gamesong) , musicVol , true);
        new Thread(audioThread).start();

        pingpong = MediaPlayer.create(context , R.raw.pingpong);
        blyat = MediaPlayer.create(context , R.raw.blyat);
        sonof = MediaPlayer.create(context , R.raw.sonof);

        handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {

                invalidate(); //makes the whole game move(calling onDraw)
                return true;
            }
        });
     Gm = new GameThead(handler);
      thread =   new Thread(Gm);

    }

    public void startMovement() //starts the movement of the game
    {
        thread.start();
        isRun = true;
    }
   public void stopMovement() //pause the game
    {
          Gm.setRun(false);
          isRun = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touch++;
        if(touch >= 2)
        {
            touch = 0;        //pause the game if the user clicked twice of the screen

            Gm.setRun(!Gm.getRun());
            isRun = !isRun;
        }
        if(Gm.getEnded() ) //if the user lost
        {

          if(event.getX() >= WidthC * 3 / 4 && event.getY() >= HightC / 8 && event.getY() <= HightC * 2 / 8 )
          {
              isSet = false;
              isRun = true;
              pp.setColor(Color.BLACK);
              Gm = new GameThead(handler);
              thread =   new Thread(Gm);    //if user pressed the 'start over' button this restarts the game
              life = 3;
              points = 0;
              audioThread = new AudioThread(MediaPlayer.create(context , R.raw.gamesong) , musicVol , true);
              new Thread(audioThread).start();
              thread.start();
              touch = 0;

          }
            if(event.getX() <= WidthC / 4 && event.getY() >= HightC / 8 && event.getY() <= HightC * 2 / 8 )
            {
                audioThread.ShutTheFuckUp();
                pp.setColor(Color.BLACK);  //if user pressed the 'back' button this  returns the user to the main activity
                isToBack = true;

            }
        }
        if(event.getX() <= WidthC / 4 && event.getY() >= HightC / 8 && event.getY() <= HightC * 2 / 8 && !isRun )
        {
            Gm.setEnded(true);
            audioThread.ShutTheFuckUp(); //if user pressed the 'back' button and the game is paused  this  returns the user to the main activity
            pp.setColor(Color.BLACK);
            isToBack = true;


        }





        return super.onTouchEvent(event);

    }

    public void continueMovement() //continues the game
    {
        Gm.setRun(true);
        isRun = true;

    }


    public static Bitmap drawableToBitmap (Drawable drawable) {   //A function that converts drawable To Bitmap , used in the process of making circular images
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }







    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isSet) {   //set all the things that require the canvas
            FallAx = 1;
            HightC = canvas.getHeight();
            WidthC = canvas.getWidth();
            heart = Bitmap.createScaledBitmap(heart, (int) WidthC / 20, (int) WidthC / 20, false);
            R_a = R_b = canvas.getHeight() / 15;
            X_b = R_b + 1;
            Y_a = R_a + 1;
            X_a = canvas.getWidth() / 2;

            adderX_b = 0.00925 * canvas.getWidth();


            trump = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.trump), (int) R_a * 2, (int) R_a * 2, false);

            RoundedBitmapDrawable Rtrump = RoundedBitmapDrawableFactory.create(getResources(), trump);
            Rtrump.setCornerRadius((float) R_a); //makes trump circular
            trump = drawableToBitmap(Rtrump);
            isSet = true;
        }


            FallAx = FallAx - 0.0001 * (FallAx - 0.3);
            R_b = R_b - 0.0003 * (R_b - 0.03 * canvas.getHeight());
            if (adderX_b > 0) {                                               //makes the game harder over time(faster and putin become smaller)
                adderX_b = adderX_b + 0.0001 * (0.05 * canvas.getWidth() - adderX_b);
            } else {
                adderX_b = adderX_b - 0.0001 * (0.05 * canvas.getWidth() + adderX_b);
            }

            Y_b = canvas.getHeight() - R_b; //makes putin sit down on the ground



        if(X_b + R_b >= canvas.getWidth()  || X_b - R_b <= 0) //putin bumps into the walls
        {
            adderX_b = -1 * adderX_b;
        }
        if(Y_a + R_a >= canvas.getHeight())  //trump bumps into the ground
        {

            isFalling = false;
            new Thread(new AudioThread(sonof , fxVol , false)).start();

            life--;
        }
        if( Y_a - R_a <= 0) //Trump bumps into the ceiling
        {
            isFalling = true;
        }
        double k = Math.sqrt((double)canvas.getHeight() / 1680) / FallAx;  //Ensures that the fall velocity will be uniform for different devices
        if(isFalling)
        {

            adderY_a =k * Math.sqrt(Math.abs(Y_a -  R_a))   ;   //demonstrate free falling according to newton equations
        }
        else {

            adderY_a = k * -1 *  Math.sqrt(Math.abs(Y_a -  R_a)) ;

        }




           Y_a += adderY_a;
           X_b += adderX_b; //"moves" trump and putin






        double d = (X_a - X_b)*(X_a - X_b) + (Y_a - Y_b)*(Y_a - Y_b);
        d = Math.sqrt(d); //calculating the distance between the center of trump and the center of putin
        if(d  <= R_a + R_b) //Trump and Putin collides
        {
            if(IsOutside)
            {
                points++; //add point and make sure that only one point will be added for one collide

            }
            IsOutside = false;

            isFalling = false;
            new Thread(new AudioThread(pingpong , fxVol , false)).start(); //playing fx
            new Thread(new AudioThread(blyat , fxVol , false)).start();






        }
        else {
            IsOutside = true;
        }

        if(life == 3)
        {

            canvas.drawBitmap(heart , (float) (14 * WidthC / 20), 10 , new Paint());
            canvas.drawBitmap(heart , (float) (16 * WidthC / 20 ), 10 , new Paint());
            canvas.drawBitmap(heart , (float) (18 * WidthC / 20 ), 10 , new Paint());
        }
        else if (life == 2)                                                 //hearts drawing according to the life value
        {
            canvas.drawBitmap(heart , (float) (16 * WidthC / 20 ), 10 , new Paint());
            canvas.drawBitmap(heart , (float) (18 * WidthC / 20 ), 10 , new Paint());
        }
        else  if (life == 1)
        {
            canvas.drawBitmap(heart , (float) (18 * WidthC / 20 ), 10 , new Paint());
        }
        else {
            Gm.setEnded(true);  //life value is 0 the player has lost
            audioThread.ShutTheFuckUp();
            Bitmap over = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.game_over) , canvas.getWidth() / 3 , canvas.getHeight()/ 5 , false);
            Bitmap startover = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.startover) , canvas.getWidth() / 4 , canvas.getHeight() / 8 , false);
            Bitmap back = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.back) , canvas.getWidth() / 4 , canvas.getHeight() / 8 , false);
            canvas.drawBitmap(back , 0 , canvas.getHeight()  / 8 , new Paint()) ; //drawing the buttons
            canvas.drawBitmap(startover , canvas.getWidth() * 3 / 4 , canvas.getHeight()  / 8 , new Paint()) ;
            canvas.drawBitmap(over , (float) canvas.getWidth() / 3 ,2 * canvas.getHeight() / 5 , new Paint() );

        }
        if(!isRun)
        { //the game has paused , drawing the "back" button

            Bitmap back = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.back) , canvas.getWidth() / 4 , canvas.getHeight() / 8 , false);
            canvas.drawBitmap(back , 0 , canvas.getHeight()  / 8 , new Paint()) ;

        }

        pp.setTextSize((float) 0.05 * canvas.getHeight());
        if(points > sharedPreferences.getInt("bestS" , 0))
        {
            sharedPreferences.edit().putInt("bestS" , points).apply(); //if the point value is higher then the saved value the points value will be saved and the text that indicate the points value become red
            pp.setColor(Color.RED);
        }


        String Spoints = "Points : " +  String.valueOf(points);
       canvas.drawText(Spoints , 10 , (float)  canvas.getHeight() / 17 , pp);

        RoundedBitmapDrawable Rputin = RoundedBitmapDrawableFactory.create(getResources() , Bitmap.createScaledBitmap(putin , (int) R_b * 2 , (int) R_b * 2 , false));
        Rputin.setCornerRadius((float)R_b);  // makes putin circular
        canvas.drawBitmap(drawableToBitmap(Rputin) ,(float) (X_b - R_b), (float)(Y_b - R_b)  , new Paint());
        canvas.drawBitmap(trump , (float)(X_a - R_a) , (float)(Y_a - R_a) , new Paint()); //drawing trump and putin



    }


    public void Add_X_a (double a) {

        if(X_a + R_a + 10 < WidthC && a > 0 && isRun) {
            X_a +=  a;
        }

         else if(X_a - R_a - 10 > 0 && a < 0 && isRun) //function that used in order to apply the sensors change
        {
            X_a +=  a;
        }

}

    public boolean isToBack() {
        return isToBack;
    } //says if the user wants to return to the main activity
    public void setSongVol(int vol) {audioThread.setVolume(vol);}//set the music volume
}
