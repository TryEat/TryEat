package com.tryeat.tryeat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.SignService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SigninFragment extends Fragment {
    View view;
    EditText id;
    EditText password;
    Button signButton;
    TextView signin_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signin_fragment,container,false);
        id = view.findViewById(R.id.id);
        password = view.findViewById(R.id.password);

        signButton = view.findViewById(R.id.signinbutton);
        signin_btn =  view.findViewById(R.id.signup_btn);
        signin_btn.setOnClickListener(toSignup());
        signButton.setOnClickListener(SignIn());
        return view;
    }

    public View.OnClickListener SignIn(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(id.getText().length()==0||password.getText().length()==0)
                {
                    AlertDialogBuilder.createAlert(getActivity(), "빈칸이 존재합니다.");
                    return;
                }
                SignService.signIn(id.getText().toString(), password.getText().toString(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == StatusCode.SIGNIN_SUCCESS) {
                            int id = response.body().payLoadInt;
                            String token = response.body().payLoadString;
                            LoginToken.setToken(id, token);
                            Log.d("debug", "로그인 성공" + token);
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        } else if (response.code() == StatusCode.SIGNIN_FAIL) {
                            AlertDialogBuilder.createAlert(getActivity(), "ID나 Password가 틀립니다.");
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Log.d("debug",t.toString());
                    }
                });
            }
        };
    }

    public View.OnClickListener toSignup(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.frament_place, new SignFragment(), "signup").addToBackStack(null).commit();
            }
        };
    };
}
