package com.timecapsule.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timecapsule.app.audioactivity.AudioFragment2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by catwong on 3/4/17.
 */

public class AddMediaFragment extends Fragment {

    private static final int TAKE_PICTURE = 200;
    private static final int CAPTURE_VIDEO = 201;
    private View mRoot;
    private ImageView iv_camera;
    private ImageView iv_audio;
    private ImageView iv_videocam;
    private String mCurrentPhotoPath;

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
        clickVideocam();
        return mRoot;
    }

    private void setViews() {
        iv_camera = (ImageView) mRoot.findViewById(R.id.iv_camera);
        iv_audio = (ImageView) mRoot.findViewById(R.id.iv_audio);
        iv_videocam = (ImageView) mRoot.findViewById(R.id.iv_videocam);
    }

    private void clickCamera() {
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNativeCamera();
            }
        });
    }

    public void goToNativeCamera() {
        Intent capture = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture, TAKE_PICTURE);
    }

    private void clickAudio() {
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

    public void clickVideocam(){
        iv_videocam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNativeVideo();
            }
        });

    }

    public void goToNativeVideo(){
        Intent record = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(record, CAPTURE_VIDEO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        try {
                            createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
