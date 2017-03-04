package nyc.c4q.hakeemsackes_bramble.timecapsule.LoginActivity;

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

import nyc.c4q.hakeemsackes_bramble.timecapsule.R;
import nyc.c4q.hakeemsackes_bramble.timecapsule.FeedActivity.FeedActivity;

/**
 * Created by catwong on 3/2/17.
 */

public class SignInFragment extends Fragment {

    private static final String TAG = SignInFragment.class.getSimpleName();
    private View mRoot;
    private ImageView iv_signin_bottom;
    private EditText et_signin_email;
    private EditText et_signin_password;
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
        mRoot = inflater.inflate(R.layout.fragment_sign_in, parent, false);
        setSignIn();
        return mRoot;
    }


    private void setSignIn() {
        et_signin_email = (EditText) mRoot.findViewById(R.id.et_login_email);
        et_signin_password = (EditText) mRoot.findViewById(R.id.et_login_password);
        iv_signin_bottom = (ImageView) mRoot.findViewById(R.id.iv_signin_bottom);
        iv_signin_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignInCredentials();
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

    private void setSignInCredentials() {
        signIn(et_signin_email.getText().toString(), et_signin_password.getText().toString());
    }


    private void goSignIn() {
        Intent intent = new Intent(getActivity(), FeedActivity.class);
        SignInFragment.this.startActivity(intent);
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Signing In", Toast.LENGTH_SHORT).show();
                            goSignIn();
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed. Try Again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_signin_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_signin_email.setError("Required.");
            valid = false;
        } else {
            et_signin_email.setError(null);
        }

        String password = et_signin_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_signin_password.setError("Required.");
            valid = false;
        } else {
            et_signin_password.setError(null);
        }

        return valid;
    }

}
