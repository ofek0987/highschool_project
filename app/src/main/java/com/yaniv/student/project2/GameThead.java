package com.yaniv.student.project2;


import android.os.Handler;

public class GameThead implements Runnable {
   private Handler handler;
   private Boolean isRun , isEnded = false;
    public GameThead(Handler handler)
    {
        this.handler = handler;
        isRun = true;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Boolean getRun() {
        return isRun;
    }

    public void setRun(Boolean run) {
        isRun = run;
    }

    public Boolean getEnded() {
        return isEnded;
    }

    public void setEnded(Boolean ended) {
        isEnded = ended;
    }

    @Override
    public void run() {
        while (!isEnded)
        {
            if(isRun) {
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
