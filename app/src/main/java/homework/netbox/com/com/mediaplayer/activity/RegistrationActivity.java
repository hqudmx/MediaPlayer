package homework.netbox.com.com.mediaplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import homework.netbox.com.R;

public class RegistrationActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_registration);
    }
}
