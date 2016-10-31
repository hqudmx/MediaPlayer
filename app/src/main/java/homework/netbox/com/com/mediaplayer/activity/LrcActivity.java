package homework.netbox.com.com.mediaplayer.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import homework.netbox.com.R;
import homework.netbox.com.com.mediaplayer.bean.MediaBean;
import homework.netbox.com.com.mediaplayer.model.LrcContent;
import homework.netbox.com.com.mediaplayer.service.mediaplayerService;
import homework.netbox.com.com.mediaplayer.utils.LrcProcess;
import homework.netbox.com.com.mediaplayer.utils.LrcView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class LrcActivity extends Activity implements View.OnClickListener {

    private List<LrcContent> lrcList = new ArrayList<LrcContent>(); // 存放歌词列表对象
    private int index = 0; // 歌词检索值
    private int currentposition = 0;
    ArrayList<MediaBean> mArray;
    TextView tv_title, tv_name;
    Button btn_loop;
    Context mContext;
    private int duration;
    private Uri uri;
    private SeekBar seekbar;
    private TextView tv_stoptime;
    private TextView tv_currenttime;
    private Handler handler;
    ImageView bt_pause, bt_pre, bt_next;
    private Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                currentposition = myservice.player.getCurrentPosition();
                uri = myservice.getPlayeruri();
                LrcProcess lrcProcess = new LrcProcess();
                lrcProcess.readLRC(uri.toString());
                lrcview.setmLrcList(lrcProcess.getLrcList());
                lrcview.setIndex(getindex());
                seekbar.setMax(myservice.player.getDuration());
                seekbar.setProgress(currentposition);
                Log.i("TGA", "Uri" + uri);
                Log.i("currentposition", currentposition + "");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    private Thread thread2 = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                msg.what = 1;
                bundle.putString("currenttime", toTime(currentposition));
                bundle.putString("stoptime", toTime(myservice.player.getDuration()));
                msg.setData(bundle);
                handler.sendMessage(msg);

                // tv_currenttime.setText(currentposition/1000/60+":"+currentposition/1000%60);
                // tv_stoptime.setText(myservice.player.getDuration()/1000/60+":"+myservice.player.getDuration()/1000%60);
            }

        }
    });
    public LrcView lrcview;

    private mediaplayerService myservice;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("TAG", "LrcActivity onServiceDisconnected");
            myservice = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("TAG", "LrcActivity onServiceConnected");
            myservice = ((mediaplayerService.MyBinder) service).getService();
            thread.start();
            thread2.start();
            seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                public void onStopTrackingTouch(SeekBar seekBar) {

                    myservice.setCurrentposition(seekbar.getProgress());
                    myservice.start();
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                }
            });
            Log.i("TAG", "LrcActivitymyservice" + myservice + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lrc);


       /* SharedPreferences sharedPreferences = getSharedPreferences("Looping", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = sharedPreferences.edit();*/
//        mEditor.putInt("NORMAL", 0);//顺序播放
//        mEditor.putInt("IsLooping", 1);//单曲循环
//        mEditor.putInt("Random", 2);//随机播放

        //mEditor.putInt("flag", 0);
        //mEditor.commit();


        mContext = this;
        mArray = new ArrayList<MediaBean>();

        bt_pause = (ImageView) findViewById(R.id.ima_start);
        bt_pre = (ImageView) findViewById(R.id.ima_pre);
        bt_next = (ImageView) findViewById(R.id.ima_next);
        bt_pause.setOnClickListener(this);
        bt_pre.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_name = (TextView) findViewById(R.id.tv_name);

        lrcview = (LrcView) findViewById(R.id.lrcShowView);
        lrcview.setMovementMethod(ScrollingMovementMethod.getInstance());
        lrcview.setBackgroundColor(Color.WHITE);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        tv_currenttime = (TextView) findViewById(R.id.tv_currenttime);
        tv_stoptime = (TextView) findViewById(R.id.tv_stoptime);
        Intent intent = new Intent(LrcActivity.this, mediaplayerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.i("TAG", "LrcActivity bindService");
        btn_loop = (Button) findViewById(R.id.id_lrc_loop);


/*

        btn_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getSharedPreferences("Looping", Activity.MODE_PRIVATE);
                int flag=sharedPreferences1.getInt("flag",0);
                if(flag==0){
                    flag=flag+1;
                    mEditor.putInt("flag",flag);
                    mEditor.commit();
                }else {
                    if (flag == 1) {
                       // btn_loop.setBackground(R.drawable.bottom_start);
                        myservice.player.setLooping(true);
                        flag=flag+1;
                        mEditor.putInt("flag",flag);
                        mEditor.commit();
                        Toast.makeText(mContext,"单曲循环",Toast.LENGTH_SHORT).show();
                    } else {
                        // btn_loop.setBackground(R.drawable.bottom_start);
                        flag=flag+1;
                        mEditor.putInt("flag",flag);
                        mEditor.commit();
                       // Random r=Random();
                        //myservice.getBean().get().mData;
                        Toast.makeText(mContext,"随机播放",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
*/


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                switch (msg.what) {
                    case 1: {
                        Bundle bundle = msg.getData();
                        tv_stoptime.setText(bundle.getString("stoptime"));
                        tv_currenttime.setText(bundle.getString("currenttime"));
                        Log.i("DMX", myservice.getBean().get(myservice.getPosition()).mTitle);
                        tv_title.setText(myservice.getBean().get(myservice.getPosition()).mTitle);
                        tv_name.setText(myservice.getBean().get(myservice.getPosition()).mName);

                        break;
                    }
                }

            }
        };


    }

    public int getindex() {
        LrcProcess lrcprocess = new LrcProcess();
        lrcprocess.readLRC(uri.toString());
        List<LrcContent> lrclist = lrcprocess.getLrcList();
        for (int i = 0; i < lrclist.size() - 1; i++) {
            if (currentposition >= lrclist.get(i).getLrcTime()
                    && currentposition <= lrclist.get(i + 1).getLrcTime())
                return i;
        }

        return lrclist.size();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(conn);
    }

    public String toTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    @Override
    public void onClick(View view) {
        int position;
        switch (view.getId()) {
            case R.id.ima_pre:
//                position = myservice.getPosition();
//                //Toast.makeText(mContext, "已经是第一首了", Toast.LENGTH_SHORT).show();
//                if (position == 0) {
//                    Toast.makeText(mContext, "已经是第一首了", Toast.LENGTH_SHORT).show();
//                } else {
//                    myservice.setPosition(position - 1);
//                    myservice.setPlayeruri(Uri.parse(myservice.getBean().get(position - 1).mData));
//                    myservice.start();
//                }
                try {
                    myservice.last();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ima_next:
//                position = myservice.getPosition();
//                if (position == myservice.getBean().size() - 1) {
//                    Toast.makeText(mContext, "已经是最后一首了", Toast.LENGTH_SHORT).show();
//                } else {
//                    myservice.setPosition(position + 1);
//                    myservice.setPlayeruri(Uri.parse(myservice.getBean().get(position + 1).mData));
//                    myservice.start();
//                }
                try {
                    myservice.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ima_start:
                if (myservice.player.isPlaying()) {
                    myservice.player.pause();
                    bt_pause.setImageResource(R.drawable.bottom_start);
                } else {
                    myservice.player.start();
                    bt_pause.setImageResource(R.drawable.bottom_stop);
                }
                break;
        }

    }
}
