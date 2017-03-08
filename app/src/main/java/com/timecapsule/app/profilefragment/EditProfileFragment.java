package com.timecapsule.app.profilefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.timecapsule.app.profilefragment.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.timecapsule.app.R;

/**
 * Created by catwong on 3/6/17.
 */

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfile";
    private static final String REQUIRED = "Required";
    private View mRoot;
    private TextView tv_cancel;
    private TextView tv_done;
    private TextView tv_edit_profile;
    private TextView tv_change_profile_photo;
    private ImageView iv_name;
    private ImageView iv_username;
    private ImageView iv_email;
    private EditText et_name;
    private EditText et_username;
    private EditText et_email;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_edit_profile, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setViews();
        clickCancel();
        clickDone();
        return mRoot;
    }

    public void setViews() {
        tv_cancel = (TextView) mRoot.findViewById(R.id.tv_edit_profile_cancel);
        tv_done = (TextView) mRoot.findViewById(R.id.tv_edit_profile_done);
        tv_edit_profile = (TextView) mRoot.findViewById(R.id.tv_edit_profile_top);
        tv_change_profile_photo = (TextView) mRoot.findViewById(R.id.tv_edit_profile_change_photo);
        iv_name = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_name);
        iv_username = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_username);
        iv_email = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_email);
        et_name = (EditText) mRoot.findViewById(R.id.et_edit_profile_name);
        et_username = (EditText) mRoot.findViewById(R.id.et_edit_profile_username);
        et_email = (EditText) mRoot.findViewById(R.id.et_edit_profile_email);
    }

    public void clickCancel() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancel();
            }
        });
    }

    public void setCancel() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }

    public void clickDone() {
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserData();
            }
        });
    }

    public void setDone() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }

    private void setUserData() {
        final String name = et_name.getText().toString();
        final String username = et_username.getText().toString();
        final String email = et_email.getText().toString();

        if (TextUtils.isEmpty(name)) {
            et_name.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(username)) {
            et_username.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(email)) {
            et_email.setError(REQUIRED);
            return;
        }


        writeNewUser(name, username, email);

//        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
//
//                        // [START_EXCLUDE]
//                        if (user == null) {
//                            // User is null, error out
//                            writeNewUser(name, username, email);
//                        } else {
//                            // Write new post
//                            writeNewUser(name, username, email);
//                        }
//
//                        // Finish this Activity, back to the stream
////                        finish();
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END single_value_read]
//    }
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Write new user
        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());
    }

    private void writeNewUser(String name, String username, String email) {
        User user = new User(name, username, email);
        mDatabase.child("users").child(username).setValue(user);
    }

}
