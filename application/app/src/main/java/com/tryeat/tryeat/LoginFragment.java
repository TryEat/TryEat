package com.tryeat.tryeat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.UserService;
import com.tryeat.team.tryeat_service.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    View view;

    EditText id;
    EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment,container,false);

        id = view.findViewById(R.id.id);
        password = view.findViewById(R.id.password);

        Button signInButton = (Button)view.findViewById(R.id.signinbutton);
        signInButton.setOnClickListener(SignIn());
        return view;
    }

    public View.OnClickListener SignIn(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UserService.signIn(id.getText().toString(), password.getText().toString(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if(response.isSuccessful()){
                            if(response.code() == StatusCode.SIGNIN_SUCCESS){

                            }
                            else if (response.code() == StatusCode.SIGNIN_FAIL){

                            }
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
