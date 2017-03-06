package nyc.c4q.hakeemsackes_bramble.timecapsule.feedactivity;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import nyc.c4q.hakeemsackes_bramble.timecapsule.AddMediaFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.NotificationsFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.ProfileFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;
import nyc.c4q.hakeemsackes_bramble.timecapsule.SearchFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.cameraactivity.CameraActivity;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class FeedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        requestAudioPermission();
        setBottomNav();
        setBottomNavButtons();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, new FeedFragment())
                    .commit();
        }

    }

    private void setBottomNav() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void setBottomNavButtons() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        setFeedFragment();
                        break;
                    case R.id.action_search:
                        setSearchFragment();
                        break;
                    case R.id.action_add:
                        setAddMediaFragment();
                        break;
                    case R.id.action_notifications:
                        setNotificationsFragment();
                        break;
                    case R.id.action_profile:
                        setProfileFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void setFeedFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new FeedFragment())
                .commit();
    }

    private void setSearchFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new SearchFragment())
                .commit();
    }

    private void setAddMediaFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new AddMediaFragment())
                .commit();
    }

    private void setNotificationsFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new NotificationsFragment())
                .commit();
    }


    private void setProfileFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(FeedActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

//                    if (StoragePermission && RecordPermission) {
//                        Toast.makeText(getApplicationContext(), "Permission Granted",
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Permission Denied",
//                                Toast.LENGTH_LONG).show();
//                    }
                }
                break;
        }

    }

}
