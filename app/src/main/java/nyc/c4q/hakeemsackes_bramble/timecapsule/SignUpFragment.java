package nyc.c4q.hakeemsackes_bramble.timecapsule;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by catwong on 3/2/17.
 */

public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getSimpleName();
    private View mRoot;
    private ImageView iv_signup_bottom;
    private EditText et_signup_email;
    private EditText et_signup_password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_sign_up, parent, false);
        setSignUp();
        return mRoot;
    }

    private void setSignUp() {
        et_signup_email = (EditText) mRoot.findViewById(R.id.et_signup_email);
        et_signup_password = (EditText) mRoot.findViewById(R.id.et_signup_password);
        iv_signup_bottom = (ImageView) mRoot.findViewById(R.id.iv_signup_bottom);
        iv_signup_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(et_signup_email.getText().toString(), et_signup_password.getText().toString());
                goSignUp();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void goSignUp() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        SignUpFragment.this.startActivity(intent);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        Toast.makeText(getActivity(), "New Account Created", Toast.LENGTH_SHORT).show();
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_signup_email.getText().toString();

        if (TextUtils.isEmpty(email)) {
            et_signup_email.setError("Required.");
            valid = false;
        } else {
            et_signup_email.setError(null);
        }

        String password = et_signup_password.getText().toString();

        if (TextUtils.isEmpty(password)) {
            et_signup_password.setError("Required.");
            valid = false;
        } else {
            et_signup_password.setError(null);
        }

        return valid;
    }


}
