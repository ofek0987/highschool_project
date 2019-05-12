package com.yaniv.student.project2;

import android.media.MediaPlayer;
import android.util.Log;

public class AudioThread implements  Runnable {       //audio player thread

    private  boolean playLooping;
    private  MediaPlayer mediaPlayer;
    private  float log1;
    public AudioThread(MediaPlayer mediaPlayer , int Volume , boolean playLooping)
    {
        this.playLooping = playLooping;

      this.mediaPlayer = mediaPlayer;


        try {
             log1=(float)(Math.log(101-Volume)/Math.log(101)); //try/catch for safety
        }
        catch (Exception ex)
        {
            log1=(float)(Math.log(101-100)/Math.log(101));
        }
        finally {
            this.mediaPlayer.setVolume(1 - log1, 1 - log1); //setting volume
        }


    }
    @Override
    public void run() {
        do {
            try{
                if(!mediaPlayer.isPlaying())
                    mediaPlayer.start(); //playing
            }
            catch (Exception e)
            {
                    Log.d("Exception", e.getMessage());
            }
        }
        while (playLooping); //if playLooping is true the audio will be played in loops


    }
    public void ShutTheFuckUp()
    {
        playLooping = false;    //stop playing
        mediaPlayer.stop();
    }
    public void setVolume(int volume)
    {
        try {
            log1=(float)(Math.log(101-volume)/Math.log(101)); //setting volume function
        }
        catch (Exception ex)
        {
            Log.d("Exception", ex.getMessage());
            log1=(float)(Math.log(101-100)/Math.log(101));
        }
        finally {
            this.mediaPlayer.setVolume(1 - log1, 1 - log1);
        }

    }
}
