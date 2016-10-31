package homework.netbox.com.com.mediaplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import homework.netbox.com.R;

public class LoginActivity extends Activity implements View.OnClickListener {
    Button RegistrationButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        init();
    }

    /**
     * 控件初始化以及添加监听事件
     */
    private void init() {
        RegistrationButton = (Button) findViewById(R.id.id_login_registration);
        RegistrationButton.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.id_login_goLogin);
        loginButton.setOnClickListener(this);

    }

    /**
     * 封装跳转方法
     * @param cla
     */
    public void sActivity(Class cla) {
        Intent intent = new Intent(LoginActivity.this, cla);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_login_registration:
                sActivity(RegistrationActivity.class);
                finish();
                break;
            case R.id.id_login_goLogin:
                sActivity(MainMedia.class);
                finish();
                break;
        }
    }
}
