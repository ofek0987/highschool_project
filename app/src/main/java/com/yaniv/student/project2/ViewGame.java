package com.yaniv.student.project2;


import android.content.Context;
import android.content.Intent;
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
import android.os.Handler;
import android.os.Message;

import android.provider.ContactsContract;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;


public class ViewGame extends View
{
    Handler handler;
    Thread thread;
    boolean isFalling = true , IsOutside = false , isSet = true;
    Context context;
    int points = 0 , life = 3;

    double  R_a  ,Y_a  , X_a  , adderY_a = 0   , X_b  , adderX_b  , R_b  , Y_b , FallAx = 1 , WidthC;
    Paint  pp = new Paint() ;
    Bitmap putin , trump , heart;


    GameThead Gm;

    public ViewGame(Context context)
    {
        super(context);

        this.context=context;
        putin = BitmapFactory.decodeResource(getResources() , R.drawable.putin);
        heart = BitmapFactory.decodeResource(getResources() , R.drawable.heart);





        handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {

                invalidate();
                return true;
            }
        });
     Gm = new GameThead(handler);
      thread =   new Thread(Gm);

    }
    public void StartMovment()
    {
        thread.start();
    }
   public void StopMovment()
    {
          Gm.setRun(false);
    }

    public void ContinueMovment()
    {
        Gm.setRun(true);

    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
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
        if(isSet) {

            WidthC = canvas.getWidth();
            heart = Bitmap.createScaledBitmap(heart , (int)WidthC / 20 , (int)WidthC / 20 , false);
            R_a = R_b = canvas.getHeight() / 15;
            X_b =R_b + 1;
            Y_a = R_a + 1;
            X_a = canvas.getWidth() / 2;

            adderX_b = 0.00925 * canvas.getWidth();


            trump = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.trump) , (int) R_a *2 ,(int) R_a *2 , false );

            RoundedBitmapDrawable Rtrump = RoundedBitmapDrawableFactory.create(getResources() , trump);
            Rtrump.setCornerRadius((float)R_a);
            trump = drawableToBitmap(Rtrump);
            isSet = false;
        }


        FallAx  = FallAx - 0.0001 * (FallAx - 0.3);
        R_b = R_b - 0.0003 * (R_b - 0.03 * canvas.getHeight());
        if(adderX_b > 0) {
            adderX_b = adderX_b + 0.0001 * (0.05 * canvas.getWidth() - adderX_b);
        }
        else {
            adderX_b = adderX_b - 0.0001 * (0.05 * canvas.getWidth() + adderX_b);
        }

        Y_b = canvas.getHeight() - R_b;



        if(X_b + R_b >= canvas.getWidth()  || X_b - R_b <= 0)
        {
            adderX_b = -1 * adderX_b;
        }
        if(Y_a + R_a >= canvas.getHeight())
        {

            isFalling = false;
            life--;
        }
        if( Y_a - R_a <= 0)
        {
            isFalling = true;
        }
        double k = Math.sqrt((double)canvas.getHeight() / 1680) / FallAx;
        if(isFalling)
        {

            adderY_a =k * Math.sqrt(Math.abs(Y_a -  R_a))   ;
        }
        else {

            adderY_a = k * -1 *  Math.sqrt(Math.abs(Y_a -  R_a)) ;

        }




        Y_a += adderY_a;
        X_b += adderX_b;






        double d = (X_a - X_b)*(X_a - X_b) + (Y_a - Y_b)*(Y_a - Y_b);
        d = Math.sqrt(d);
        if(d  <= R_a + R_b)
        {
            if(IsOutside)
            {
                points++;
            }
            IsOutside = false;

            isFalling = false;





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
        else if (life == 2)
        {
            canvas.drawBitmap(heart , (float) (16 * WidthC / 20 ), 10 , new Paint());
            canvas.drawBitmap(heart , (float) (18 * WidthC / 20 ), 10 , new Paint());
        }
        else  if (life == 1)
        {
            canvas.drawBitmap(heart , (float) (18 * WidthC / 20 ), 10 , new Paint());
        }
        else {
            Gm.setEnded(true);
            Bitmap over = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.game_over) , canvas.getWidth() / 3 , canvas.getHeight()/ 5 , false);
            canvas.drawBitmap(over , (float) canvas.getWidth() / 3 ,2 * canvas.getHeight() / 5 , new Paint() );
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pp.setTextSize((float) 0.05 * canvas.getHeight());

        String Spoints = "Points : " +  String.valueOf(points);
       canvas.drawText(Spoints , 10 , (float)  canvas.getHeight() / 17 , pp);

        RoundedBitmapDrawable Rputin = RoundedBitmapDrawableFactory.create(getResources() , Bitmap.createScaledBitmap(putin , (int) R_b * 2 , (int) R_b * 2 , false));
        Rputin.setCornerRadius((float)R_b);
        canvas.drawBitmap(drawableToBitmap(Rputin) ,(float) (X_b - R_b), (float)(Y_b - R_b)  , new Paint());
        canvas.drawBitmap(trump , (float)(X_a - R_a) , (float)(Y_a - R_a) , new Paint());


    }


    public void Add_X_a (double a) {
        if(X_a + R_a + 10 < WidthC && a > 0) {
            X_a += a;
        }

         else if(X_a - R_a - 10 > 0 && a < 0)
        {
            X_a += a;
        }

}



}
