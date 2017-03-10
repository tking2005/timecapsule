package com.timecapsule.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timecapsule.app.audioactivity.AudioFragment2;
import com.timecapsule.app.cameraactivity.CameraActivity;

/**
 * Created by catwong on 3/4/17.
 */

public class AddMediaFragment extends Fragment {

    private View mRoot;
    private ImageView iv_camera;
    private ImageView iv_audio;
    private static final int TAKE_PICTURE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_add_media, parent, false);
        setViews();
        clickCamera();
        clickAudio();
        return mRoot;
    }

    private void setViews(){
        iv_camera = (ImageView) mRoot.findViewById(R.id.iv_camera);
        iv_audio = (ImageView) mRoot.findViewById(R.id.iv_audio);
    }

    private void clickCamera(){
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                goToCamera();
                goToNativeCamera();
            }
        });
    }

    private void goToCamera(){
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        AddMediaFragment.this.startActivity(intent);
    }

    private void clickAudio(){
        iv_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAudio();
            }
        });
    }

    private void goToAudio() {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main, new AudioFragment2())
                    .commit();
    }

    public void goToNativeCamera(){
        Intent capture = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture, TAKE_PICTURE);
    }



}
