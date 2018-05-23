package com.tryeat.tryeat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.SignService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignFragment extends Fragment {
    View view;
    LinearLayout checkPasswordLayout;
    EditText id;
    EditText password;
    EditText checkPassword;
    Button signButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_fragment,container,false);

        checkPasswordLayout = view.findViewById(R.id.check_password_layout);

        id = view.findViewById(R.id.id);
        password = view.findViewById(R.id.password);
        checkPassword = view.findViewById(R.id.check_password);

        signButton = view.findViewById(R.id.signbutton);

        useSignUp(getActivity());
        return view;
    }

    public void useSignIn(Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signButton.setText("Sign In");
                checkPasswordLayout.setVisibility(View.GONE);
            }
        });
        signButton.setOnClickListener(SignIn());
    }

    public void useSignUp(Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signButton.setText("Sign Up");
                checkPasswordLayout.setVisibility(View.VISIBLE);
            }
        });
        signButton.setOnClickListener(SignUp());
    }

    public View.OnClickListener SignIn(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(id.getText().length()==0||password.getText().length()==0)return;
                SignService.signIn(id.getText().toString(), password.getText().toString(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.SIGNIN_SUCCESS) {
                            int id = response.body().payLoadInt;
                            String token = response.body().payLoadString;
                            LoginToken.setToken(id, token);
                            Log.d("debug", "로그인 성공" + token);
                        } else if (response.code() == StatusCode.SIGNIN_FAIL) {

                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                    }
                });
            }
        };
    }

    public View.OnClickListener SignUp(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(id.getText().length()==0||password.getText().length()==0||checkPassword.getText().length()==0)return;
                if(!password.getText().toString().equals(checkPassword.getText().toString()))return;
                SignService.signUp(id.getText().toString(), password.getText().toString(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        int statusCode = response.code();
                        if (statusCode == StatusCode.SIGNUP_ID_DUPLICATION) {
                            AlertDialogBuilder.createAlert(getActivity(), "Id 가 중복됩니다...");
                        } else if (statusCode == StatusCode.SIGNUP_SUCCESS) {
                            int id = response.body().payLoadInt;
                            String token = response.body().payLoadString;
                            LoginToken.setToken(id, token);
                        } else if (statusCode == StatusCode.SIGNIN_FAIL) {
                            AlertDialogBuilder.createAlert(getActivity(), "로그인 실패했습니다...");
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {

                    }
                });
            }
        };
    }
}
