package com.yaniv.student.project2;

import android.os.Handler;

public class toBackListenerThread implements Runnable {
    private Handler handler;
    Boolean toStop = false;
    public toBackListenerThread(Handler handler)
    {
        this.handler = handler;
    }
    @Override
    public void run() {
        while (!toStop)
        {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void toStop()
    {
        toStop = true;
    }

}
