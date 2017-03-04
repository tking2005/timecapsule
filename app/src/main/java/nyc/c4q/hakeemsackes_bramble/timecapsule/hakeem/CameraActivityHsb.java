package nyc.c4q.hakeemsackes_bramble.timecapsule.hakeem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

/**
 * Created by hakeemsackes-bramble on 2/28/17.
 */

public class CameraActivityHsb extends AppCompatActivity {
    CameraManager cameraManager;
    TextureView textureView;
    TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            cameraSetup(width, height);
            try {
                openCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_hsb);
        textureView = (TextureView) findViewById(R.id.camera_view);
        Log.d("stuff on create", "onCreate: ");
        cameraSetup(textureView.getWidth(), textureView.getHeight());

    }

    private void openCamera() throws Exception {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            Log.d("damn", "openCamera: ");
            return;
        }
        cameraManager.openCamera(mCameraId, camDeviceStateCallBack, null);

    }

    CameraDevice cameraDevice;
    CameraDevice.StateCallback camDeviceStateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            //Toast.makeText(getApplicationContext(), "Camera Device Opened", Toast.LENGTH_SHORT).show();
            createCamPrevSession();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice = null;
            camera.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice = null;
            camera.close();

        }
    };
    private CaptureRequest mPreviewCapturereq;
    CaptureRequest.Builder mPreviewCaptureReqBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraCaptureSession.CaptureCallback msessionCaptureCallback
            = new CaptureCallback() {
        @Override
        public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }
    };
    private Size mPrevSize;
    private String mCameraId;


    @Override
    protected void onResume() {
        super.onResume();

        if (textureView.isAvailable()) {

        } else {
            textureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void cameraSetup(int width, int height) {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            Log.d("camera setup", "cameraSetup: try");
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
//                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
//                        CameraCharacteristics.LENS_FACING_FRONT) {
//                    continue;
//                }
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                mPrevSize = getPreferredPreviewSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Size getPreferredPreviewSize(Size[] mapSizes, int width, int height) {
        List<Size> collectorSizes = new ArrayList<>();
        for (Size sizeOption : mapSizes) {
            if (width > height) {
                if (sizeOption.getWidth() > width && sizeOption.getHeight() > height) {
                    collectorSizes.add(sizeOption);
                }
            } else {
                if (sizeOption.getWidth() > height && sizeOption.getHeight() > width) {
                    collectorSizes.add(sizeOption);
                }
            }
        }
        if (collectorSizes.size() > 0) {
            return Collections.min(collectorSizes, new Comparator<Size>() {
                @Override
                public int compare(Size o1, Size o2) {
                    return Long.signum(o1.getWidth() * o1.getHeight() - o2.getWidth() * o2.getHeight());
                }
            });
        }
        return mapSizes[0];
    }

    private void createCamPrevSession() {
        try {
            SurfaceTexture surfaceT = textureView.getSurfaceTexture();
            surfaceT.setDefaultBufferSize(mPrevSize.getWidth(), mPrevSize.getHeight());
            Surface previewSurface = new Surface(surfaceT);
            mPreviewCaptureReqBuilder = cameraDevice.createCaptureRequest((CameraDevice.TEMPLATE_PREVIEW));
            mPreviewCaptureReqBuilder.addTarget(previewSurface);
            cameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    if (cameraDevice == null) {
                        return;
                    }
                    try {
                        mPreviewCapturereq = mPreviewCaptureReqBuilder.build();
                        mCameraCaptureSession = session;
                        mCameraCaptureSession.setRepeatingRequest(
                                mPreviewCapturereq,
                                msessionCaptureCallback,
                                null
                        );
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                    Toast.makeText(getApplicationContext(), "create camera session failed", Toast.LENGTH_SHORT).show();
                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}

