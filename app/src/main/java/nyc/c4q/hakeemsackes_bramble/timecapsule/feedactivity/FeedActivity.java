package nyc.c4q.hakeemsackes_bramble.timecapsule.feedactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;

import nyc.c4q.hakeemsackes_bramble.timecapsule.AddMediaFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.NotificationsFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.profilefragment.ProfileFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;
import nyc.c4q.hakeemsackes_bramble.timecapsule.SearchFragment;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class FeedActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_LOCATION = 201;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        requestLocationPermission();
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

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
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
                }
                break;

            case REQUEST_LOCATION:
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We can now safely use the API we requested access to
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                } else {
                    // Permission was denied or request was cancelled
                }
                break;
        }

    }

}
