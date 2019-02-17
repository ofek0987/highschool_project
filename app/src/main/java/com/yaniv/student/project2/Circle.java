package com.yaniv.student.project2;


import android.content.Context;
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


public class Circle extends View implements SensorEventListener
{
    Handler handler;
    boolean isFalling = true , IsOutside = false;
    Context context;
    int points = 0;

    double  R_a = 100 ,Y_a = R_a + 1 , X_a = R_a + 1 , adderY_a = 0, adderX_a = 2  , X_b = 250 , adderX_b = 10 , R_b = 100 , Y_b = 900 , FallAx = 1;
    Paint p = new Paint() , pp = new Paint() ;
    Bitmap putin , trump;



    public Circle(Context context)
    {
        super(context);
        this.context=context;
        putin = BitmapFactory.decodeResource(getResources() , R.drawable.putin);
        trump = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources() , R.drawable.trump) , (int) R_a *2 ,(int) R_a *2 , false );

        RoundedBitmapDrawable Rtrump = RoundedBitmapDrawableFactory.create(getResources() , trump);
        Rtrump.setCornerRadius((float)R_a);
        trump = drawableToBitmap(Rtrump);




        handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {

                invalidate();
                return true;
            }
        });



    }
    public void StartMovment()
    {
        new Thread(new GameThead(handler)).start();
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
        Y_b = canvas.getHeight() - R_b;


        if(X_a + R_a >= canvas.getWidth()  || X_a - R_a <= 0) // פגיעה בצידי המסך
        {
            adderX_a = -1 * adderX_a;
        }
        if(X_b + R_b >= canvas.getWidth()  || X_b - R_b <= 0)
        {
            adderX_b = -1 * adderX_b;
        }
        if(Y_a + R_a >= canvas.getHeight()) // פגיעה בתחתית המסך
        {

            isFalling = false;
        }
        if( Y_a - R_a <= 0) // פגיעה בתקרת המסך
        {
            isFalling = true;
        }
        if(isFalling) // הדמיית נפילה
        {
            adderY_a = Math.sqrt(Math.abs(Y_a -  R_a)) / FallAx ;     // FallAx ;
        }
        else {

            adderY_a = -1 * Math.sqrt(Math.abs(Y_a -  R_a)) / FallAx ;

        }



        X_a += adderX_a; // תאוצה \ מהירות
        Y_a += adderY_a;
        X_b += adderX_b;



        double d = (X_a - X_b)*(X_a - X_b) + (Y_a - Y_b)*(Y_a - Y_b);
        d = Math.sqrt(d);
        if(d  <= R_a + R_b) // פגיעה של הדמויות
        {
            if(IsOutside)
            {
                points++;
            }
            IsOutside = false;

            isFalling = false;
            adderX_a += (X_a - X_b) * Math.abs( adderX_b) / 300;




        }
        else {
            IsOutside = true;
        }
        FallAx  = FallAx - 0.0001 * (FallAx - 0.3); // העלת דרגת קושי עם הזמן
        R_b = R_b - 0.0005 * (R_b - 50);
        if(adderX_b > 0) {
            adderX_b = adderX_b + 0.0001 * (30 - adderX_b);
        }
        else {
            adderX_b = adderX_b - 0.0001 * (30 + adderX_b);
        }

        p.setColor(Color.RED);
        pp.setTextSize(50); // גודל אותיות ניקוד
        //canvas.drawCircle((float)X_a , (float)Y_a , (float)R_a , new Paint()); // ציור
        //canvas.drawCircle((float)X_b , (float)Y_b , (float)R_b , p);
        String Spoints = "Points : " +  String.valueOf(points);
        canvas.drawText(Spoints , 450 , 100 , pp);
        RoundedBitmapDrawable Rputin = RoundedBitmapDrawableFactory.create(getResources() , Bitmap.createScaledBitmap(putin , (int) R_b * 2 , (int) R_b * 2 , false));
        Rputin.setCornerRadius((float)R_b);
        canvas.drawBitmap(drawableToBitmap(Rputin) ,(float) (X_b - R_b), (float)(Y_b - R_b)  , new Paint());

        canvas.drawBitmap(trump , (float)(X_a - R_a) , (float)(Y_a - R_a) , new Paint());


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double  deltax = sensorEvent.values[0];
           double  deltay = sensorEvent.values[1];
           // double  deltaz = sensorEvent.values[2];
            X_a += deltax;
            Y_a += deltay;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
