package com.timecapsule.app.profilefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.timecapsule.app.R;

public class ProfileFragment extends Fragment {

    private View mRoot;
    private Button bt_edit_profile;
    private ImageView iv_add_friend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_profile, parent, false);
        setViews();
        clickEditProfile();
        clickAddFriend();
        return mRoot;
    }



    public void setViews() {
        bt_edit_profile = (Button) mRoot.findViewById(R.id.bt_edit_profile);
        iv_add_friend = (ImageView) mRoot.findViewById(R.id.iv_add_friend);
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
