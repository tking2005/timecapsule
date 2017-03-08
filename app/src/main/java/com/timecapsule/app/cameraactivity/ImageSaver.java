package com.timecapsule.app.cameraactivity;

import android.media.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by hakeemsackes-bramble on 3/7/17.
 */

public class ImageSaver implements Runnable {

    private final Image mImage;
    private File mImageFile;

    public ImageSaver(Image mImage, File mImageFile) {
        this.mImage = mImage;
        this.mImageFile = mImageFile;
    }


    @Override
    public void run() {
        ByteBuffer byteBuffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mImageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            mImage.close();
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
