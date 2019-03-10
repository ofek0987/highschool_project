package com.yaniv.student.project2;

import android.media.MediaPlayer;

public class AudioThread implements  Runnable {

    MediaPlayer mediaPlayer;
    float log1;
    public AudioThread(MediaPlayer mediaPlayer , int Volume)
    {

      this.mediaPlayer = mediaPlayer;


        try {
             log1=(float)(Math.log(101-Volume)/Math.log(101));
        }
        catch (Exception ex)
        {
            log1=(float)(Math.log(101-50)/Math.log(101));
        }
        finally {
            this.mediaPlayer.setVolume(1 - log1, 1 - log1);
        }


    }
    @Override
    public void run() {
        mediaPlayer.start();

    }
}
