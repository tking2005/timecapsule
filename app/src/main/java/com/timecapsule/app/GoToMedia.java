package com.timecapsule.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.timecapsule.app.addmediafragment.AudioFragment2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tarynking on 3/11/17.
 */

public class GoToMedia extends AppCompatActivity {

    private static final int TAKE_PICTURE = 200;
    private static final int CAPTURE_VIDEO = 201;
    private View mRoot;
    private String mCurrentPhotoPath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference imagesRef;
    private UploadTask uploadTask;
    private String mediaType;
    private double locationLat;
    private double locationLong;
    private String address;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imagesRef = storageReference.child("images");

        mediaType = getIntent().getExtras().getString("keyMediaType");
        locationLat = getIntent().getExtras().getDouble("keyLocationLat");
        locationLong = getIntent().getExtras().getDouble("keyLocationLong");
        address = getIntent().getExtras().getString("keyAddress");

        openMedia(mediaType);
    }

    private void openMedia(String mediaType) {
        switch (mediaType){
            case "camera":
                goToNativeCamera();
                break;
            case "video":
                goToNativeVideo();
                break;
            case "audio":
                goToAudio();
                break;
        }
    }


    private void goToNativeCamera() {
        Intent capture = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture, TAKE_PICTURE);
    }


    private void goToAudio() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new AudioFragment2())
                .commit();
    }


    public void goToNativeVideo() {
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
                            uploadImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        String firebaseReference = imageFileName.concat(".jpg");
        imagesRef = imagesRef.child(firebaseReference);
        StorageReference newImageRef = storageReference.child("images/".concat(firebaseReference));
        newImageRef.getName().equals(newImageRef.getName());
        newImageRef.getPath().equals(newImageRef.getPath());

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void uploadImage() {
        Uri file = Uri.fromFile(new File(mCurrentPhotoPath));
        StorageReference imageRef = storageReference.child("images/" + file.getLastPathSegment());
        uploadTask = imageRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }


}