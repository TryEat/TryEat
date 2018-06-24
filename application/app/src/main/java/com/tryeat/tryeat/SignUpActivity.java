package com.tryeat.tryeat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.SignService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout checkPasswordLayout;
    private EditText id;
    private EditText password;
    private EditText checkPassword;
    private TextView signinButton;
    private Button signupButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.check_password);

        signupButton = findViewById(R.id.signup_button);
        signinButton = findViewById(R.id.signin_bnt);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }

    private void registrar() {
        if (id.getText().length() == 0 || password.getText().length() == 0 || checkPassword.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "빈칸이 존재합니다.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.getText().toString().equals(checkPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "비밀번호'와 '비밀번호 확인'이 다릅니다...", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("회원가입 중...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        SignService.signUp(id.getText().toString(), password.getText().toString(), new Callback<Status>() {
                            @Override
                            public void onResponse(Call<Status> call, Response<Status> response) {
                                int statusCode = response.code();
                                switch (statusCode) {
                                    case 409:
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Id 가 중복됩니다...", Toast.LENGTH_LONG).show();
                                        break;
                                    case 201:
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "가입에 성공했습니다...", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                                        break;
                                    case 401:
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "가입에 실패했습니다...", Toast.LENGTH_LONG).show();
                                        break;
                                }

                            }

                            @Override
                            public void onFailure(Call<Status> call, Throwable t) {

                            }
                        });
                    }
                }, 500
        );
    }
}
