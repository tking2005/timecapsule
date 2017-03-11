package com.timecapsule.app.profilefragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.controller.ProfileCapsulesCreatedAdapter;


public class ProfileFragment extends Fragment {

    private View mRoot;
    private String MY_PREF = "MY_PREF";
    private String NAME_KEY = "nameKey";
    private String EMAIL_KEY = "emailKey";
    private String USERNAME_KEY = "usernameKey";
    private String PROFILE_PHOTO_KEY = "profilePhotoKey";
    private Button bt_edit_profile;
    private ImageView iv_add_friend;
    private ImageView iv_profile_photo;
    private TextView tv_profile_username;
    private TextView tv_profile_name;
    private SharedPreferences sharedPreferences;
    private ImageView profile;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_profile, parent, false);
        sharedPreferences = getActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        setViews();
        setSharedPrefs();
        clickEditProfile();
        clickAddFriend();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) mRoot.findViewById(R.id.rv_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ProfileCapsulesCreatedAdapter());
    }


    public void setViews() {
        bt_edit_profile = (Button) mRoot.findViewById(R.id.bt_edit_profile);
        iv_add_friend = (ImageView) mRoot.findViewById(R.id.iv_add_friend);
        iv_profile_photo = (ImageView) mRoot.findViewById(R.id.iv_profile_photo);
        tv_profile_username = (TextView) mRoot.findViewById(R.id.tv_profile_username);
        tv_profile_name = (TextView) mRoot.findViewById(R.id.tv_profile_name);
        profile = (ImageView) mRoot.findViewById(R.id.test_photo);

        Picasso.with(getActivity())
                .load(R.drawable.profile_cat) //extract as User instance method
                .transform(new CropCircleTransformation())
                .resize(125,125)
                .into(iv_profile_photo);
    }

    public void setSharedPrefs() {
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        tv_profile_username.setText(username);
        String name = sharedPreferences.getString(NAME_KEY, "");
        tv_profile_name.setText(name);
        String photo = sharedPreferences.getString(PROFILE_PHOTO_KEY, "");
        iv_profile_photo.setImageDrawable(Drawable.createFromPath(photo));
    }

    public void clickEditProfile() {
        bt_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditProfile();
            }
        });
    }

    public void setEditProfile() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new EditProfileFragment())
                .commit();
    }

    private void clickAddFriend() {
        iv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddFriend();
            }
        });
    }

    public void setAddFriend() {
        String appLinkUrl;
        String previewImageUrl;

        appLinkUrl = "https://fb.me/1777539359241152";
        previewImageUrl = "https://sarahasousa.files.wordpress.com/2014/11/time-capsule.gif";

        AppInviteContent content = new AppInviteContent.Builder()
                .setApplinkUrl(appLinkUrl)
                .setPreviewImageUrl(previewImageUrl)
                .build();
        AppInviteDialog.show(getActivity(), content);

    }

}
