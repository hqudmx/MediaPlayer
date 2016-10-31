package homework.netbox.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import homework.netbox.com.com.mediaplayer.activity.LoginActivity;
import homework.netbox.com.com.mediaplayer.activity.MainMedia;

public class MainActivity extends Activity {

    ImageView ima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        /*Handler handler = new Handler();

        Runnable updateThread = new Runnable() {

            public void run() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
                finish();
            }
        };
 /*       handler.postDelayed(updateThread, 1000);*/

        ima = (ImageView) findViewById(R.id.main_ima);

        Animation an = new AlphaAnimation(0.1f, 1f);
        an.setDuration(1500);
        ima.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent
                        intent = new Intent();
                intent.setClass(MainActivity.this,
                        MainMedia.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
                finish();

            }
        });


    }
}
