package com.timecapsule.app.loginactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.timecapsule.app.R;

/**
 * Created by catwong on 3/2/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login Information";
    ImageView iv_sign_in;
    ImageView iv_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_credentials, new SignInFragment())
                    .commit();
        }
    }

    private void setViews() {
        iv_sign_in = (ImageView) findViewById(R.id.iv_login_signin);
        iv_sign_up = (ImageView) findViewById(R.id.iv_login_signup);
    }

    private void setSignInClickView() {
        iv_sign_in.setImageResource(R.drawable.signin_8);
        iv_sign_up.setImageResource(R.drawable.signup8_uc);
    }

    private void setSignUpClickView() {
        iv_sign_up.setImageResource(R.drawable.signup_8);
        iv_sign_in.setImageResource(R.drawable.signin8_uc);
    }

    private void setSignIn() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_credentials, new SignInFragment())
                .commit();
    }

    private void setSignUp() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_credentials, new SignUpFragment())
                .commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_login_signin:
                setSignIn();
                setSignInClickView();
                break;
            case R.id.iv_login_signup:
                setSignUp();
                setSignUpClickView();
                break;
        }

    }


}




