package com.tryeat.tryeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.SignService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private boolean saveLoginData;
    private String id;
    private String pwd;

    EditText idText;
    EditText passwordText;
    Button signButton;
    TextView signup_btn;
    CheckBox checkBox;

    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        idText = findViewById(R.id.id);
        passwordText = findViewById(R.id.password);

        checkBox = findViewById(R.id.checkbox);

        signButton = findViewById(R.id.signinbutton);
        signup_btn = findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    save();
                }
                signIn();
            }
        });

        if(saveLoginData){
            idText.setText(id);
            passwordText.setText(pwd);
            checkBox.setChecked(saveLoginData);
            signIn();
        }
    }

    private void save() {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", passwordText.getText().toString().trim());

        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }

    private void signIn() {
        if (idText.getText().length() == 0 || passwordText.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "빈칸이 존재합니다.", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("로그인 중...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        SignService.signIn(idText.getText().toString(), passwordText.getText().toString(), new Callback<Status>() {
                            @Override
                            public void onResponse(Call<Status> call, Response<Status> response) {
                                if (response.code() == StatusCode.SIGNIN_SUCCESS) {
                                    int id = response.body().payLoadInt;
                                    String token = response.body().payLoadString;
                                    LoginToken.setToken(id, token);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();
                                } else if (response.code() == StatusCode.SIGNIN_FAIL) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Status> call, Throwable t) {
                                Log.d("debug", t.toString());
                            }
                        });
                    }
                }, 500
        );

    }
}