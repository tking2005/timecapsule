package nyc.c4q.hakeemsackes_bramble.timecapsule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by catwong on 3/2/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login Information";
    ImageView iv_sign_in;
    ImageView iv_sign_up;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
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
                break;
            case R.id.iv_login_signup:
                setSignUp();
                break;
        }

    }


}




