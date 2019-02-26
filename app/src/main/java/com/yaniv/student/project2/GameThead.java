package com.yaniv.student.project2;


import android.os.Handler;

public class GameThead implements Runnable {
    Handler handler;
    Boolean isRun;
    public GameThead(Handler handler)
    {
        this.handler = handler;
        isRun = true;
    }



    @Override
    public void run() {
        while (isRun)
        {

            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
