package homework.netbox.com.com.mediaplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import homework.netbox.com.com.mediaplayer.bean.MediaBean;
import homework.netbox.com.com.mediaplayer.utils.MediaUtils;

public class mediaplayerService extends Service {

    public MediaPlayer player;
    private Uri playeruri;// 播放的歌曲的uri
    private int currentposition = 0;
    Context mContext;

    public int getPosition() {
        return Sonposition;
    }

    public void setPosition(int position) {
        this.Sonposition = position;
    }

    public ArrayList<MediaBean> getBean() {
        return bean;
    }

    public void setBean(ArrayList<MediaBean> bean) {
        this.bean = bean;
    }

    private int Sonposition = 0;//歌曲位置
    private ArrayList<MediaBean> bean;
    MediaUtils mediaUtils;
    Handler handler;
    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                if (player.isPlaying()) {
                    if (player.getDuration() - 1000 <= player.getCurrentPosition()) {
                        Log.i("TAG", "ABAB");
                        try {
                            next();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mediaUtils = new MediaUtils(mContext);

        player = new MediaPlayer();
        bean = mediaUtils.getMedia();
        thread.start();

    }

    public void setCurrentposition(int currentposition) {
        this.currentposition = currentposition;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public mediaplayerService getService() {

            return mediaplayerService.this;
        }
    }

    public void start() {
        try {
            player.reset();// 从新设置要播放的音乐
            player = MediaPlayer.create(this, playeruri);
            player.seekTo(currentposition);
            player.start();// 播放音乐
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public void pause() {
        player.pause();
    }

    public Uri getPlayeruri() {
        return playeruri;
    }

    public void setPlayeruri(Uri playeruri) {
        this.playeruri = playeruri;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //player.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public void next() throws IOException {
        player.setLooping(true);
        if(player.isLooping()){
            Log.i("CDX","avabababab");
        }else if (Sonposition == bean.size() - 1) {
            setPosition(0);
            setPosition(Sonposition + 1);
            currentposition = 0;
            setPlayeruri(Uri.parse(bean.get(Sonposition).mData));
            start();
        } else {
            setPosition(Sonposition + 1);
            currentposition = 0;
            setPlayeruri(Uri.parse(bean.get(Sonposition).mData));
            start();
        }
    }

    public void last() throws IOException {
        if (Sonposition == 0) {
            setPosition(bean.size() - 1);
            currentposition = 0;
            setPlayeruri(Uri.parse(bean.get(Sonposition).mData));
            start();
        } else {
            setPosition(Sonposition - 1);
            currentposition = 0;
            setPlayeruri(Uri.parse(bean.get(Sonposition).mData));
            start();

        }
    }



}
