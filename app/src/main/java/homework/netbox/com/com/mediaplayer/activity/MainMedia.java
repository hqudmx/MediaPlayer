package homework.netbox.com.com.mediaplayer.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import homework.netbox.com.R;
import homework.netbox.com.com.mediaplayer.adapter.MediaAdpter;
import homework.netbox.com.com.mediaplayer.bean.MediaBean;
import homework.netbox.com.com.mediaplayer.service.mediaplayerService;
import homework.netbox.com.com.mediaplayer.utils.MediaUtils;

public class MainMedia extends Activity implements View.OnClickListener {
    Context mContext;
    ImageView bt_goLrc, bt_pause, bt_pre, bt_next;
    ArrayList<MediaBean> mArray;
    MediaAdpter mediaAdpter;
    ListView mListView;
    MediaBean mediaBean;
    MediaUtils mediaUtils;
    EditText editText;
    mediaplayerService myService;
    TextView tv_title, tv_name;
    int currentPoistion;

    public ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            Log.i("CDX", "onServiceConnected>>>>>>>>");
            myService = ((mediaplayerService.MyBinder) arg1).getService();
            myService.setBean(mArray);
            //myService.player = new MediaPlayer();
            Log.i("CDX", myService + ">>>>>>>>");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("CDX", "onServiceDisconnected>>>>>>>>");
            myService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_media);

        Intent intent = new Intent(this, mediaplayerService.class);
        startService(intent);

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Log.i("CDX", "MusicListActivity bindService");


        mContext = this;
        mediaUtils = new MediaUtils(mContext);
        mArray = mediaUtils.getMedia();

        mListView = (ListView) findViewById(R.id.lv_media);
        editText = (EditText) findViewById(R.id.main_search);

        tv_title = (TextView) findViewById(R.id.media_title);
        tv_name = (TextView) findViewById(R.id.media_name);

        bt_goLrc = (ImageView) findViewById(R.id.media_ima);
        bt_pause = (ImageView) findViewById(R.id.media_pause);
        bt_pre = (ImageView) findViewById(R.id.media_last);
        bt_next = (ImageView) findViewById(R.id.media_next);
        bt_goLrc.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_pre.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // mArray.clear();
                String keys = editText.getText().toString();
                Log.i("test", "----->");
                mArray = mediaUtils.queryLikeMedia(keys);
                //Log.i("hqu", "onTextChanged: ");
                mediaAdpter.SetDataChanged(mArray);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mediaAdpter = new MediaAdpter(mContext, mArray);
        mListView.setAdapter(mediaAdpter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                myService.setPlayeruri(Uri.parse(mArray.get(i).mData));
                // myService.setPlayeruri(Uri.parse(mArray.get(i).mData));
                currentPoistion = i;
                myService.setPosition(i);
                Log.i("DMX", myService.getPosition() + "");

                tv_title.setText(mArray.get(i).mTitle);
                tv_name.setText(mArray.get(i).mName);
                myService.start();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mArray = mediaUtils.getMedia();
        mediaAdpter.SetDataChanged(mArray);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.media_last:
//                if (currentPoistion == 0) {
//                    Toast.makeText(mContext, "已经是第一首了", Toast.LENGTH_SHORT).show();
//                } else {
//                    currentPoistion = currentPoistion - 1;
//                    myService.setPlayeruri(Uri.parse(mArray.get(currentPoistion).mData));
//                    myService.start();
//                }
                try {
                    myService.last();
                    tv_title.setText(myService.getBean().get(myService.getPosition()).mTitle);
                    tv_name.setText(myService.getBean().get(myService.getPosition()).mName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.media_next:

//                if (currentPoistion == mArray.size() - 1) {
//                    Toast.makeText(mContext, "已经是最后一首了", Toast.LENGTH_SHORT).show();
//                } else {
//                    currentPoistion = currentPoistion + 1;
//                    myService.setPlayeruri(Uri.parse(mArray.get(currentPoistion).mData));
//                    myService.start();
//                }
                try {
                    myService.next();
                    tv_title.setText(myService.getBean().get(myService.getPosition()).mTitle);
                    tv_name.setText(myService.getBean().get(myService.getPosition()).mName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.media_pause:
                if (myService.player.isPlaying()) {
                    myService.player.pause();
                    bt_pause.setImageResource(R.drawable.pause_start);
                } else {
                    myService.player.start();
                    bt_pause.setImageResource(R.drawable.pause_stop);
                }
                break;
            case R.id.media_ima:
                Intent intent = new Intent(mContext, LrcActivity.class);
                intent.putExtra("POISTION", currentPoistion);
                startActivity(intent);
                break;
        }

    }
}
