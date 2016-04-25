package com.xll.administrator.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xll.administrator.R;
import com.xll.administrator.ui.MainActivity;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.ParamContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener{

    private EditText etName;
    private EditText etPassWord;
    private TextView tvForget;
    private TextView tvRegister;
    private Button btnLogin;
    private SharedPreferences share;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private RadioGroup gendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {

        share = getSharedPreferences(ParamContract.SHARE_NAME, MODE_PRIVATE);

        gendar= (RadioGroup) findViewById(R.id.gendar);
        gendar.setOnCheckedChangeListener(this);
        etName = (EditText) findViewById(R.id.user);
        etPassWord = (EditText) findViewById(R.id.password);
        tvForget = (TextView) findViewById(R.id.forgetPwd);
        tvRegister = (TextView) findViewById(R.id.register);
        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login(etName.getText().toString(), etPassWord.getText().toString());
                break;
            case R.id.forgetPwd:


                break;

            case R.id.register:

                break;
        }

    }

    private void login(final String name, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("password", password);
                String result = HttpUtils.hucGet(HttpUtils.URL_LOGIN, map);
                handler.obtainMessage(1,result).sendToTarget();
            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case 1:
                String result=(String) msg.obj;
                  onSuccess(result);
                  break;
              default:
                  break;
          }
        }
    };

    private void onSuccess(String result){
        try {
            JSONObject object=new JSONObject(result);
            if(object.getString("code").equals("0")){
                share.edit().putString("name", object.getString("name")).commit();
                share.edit().putInt("uid", Integer.valueOf(object.optString("uid", "-1"))).commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
            LoginActivity.this.finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.female:
                share.edit().putString("gendar", "female").commit();
                break;
            case R.id.male:
                share.edit().putString("gendar", "male").commit();
                break;
        }
    }
}


