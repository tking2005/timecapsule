package nyc.c4q.hakeemsackes_bramble.timecapsule.feedactivity;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import nyc.c4q.hakeemsackes_bramble.timecapsule.FeedFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.GoogleMapsActivity;

import nyc.c4q.hakeemsackes_bramble.timecapsule.AddMediaFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.NotificationsFragment;

import nyc.c4q.hakeemsackes_bramble.timecapsule.ProfileFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;
import nyc.c4q.hakeemsackes_bramble.timecapsule.SearchFragment;

public class FeedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setBottomNav();
        setBottomNavButtons();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, new FeedFragment())
                    .commit();
        }

    }

    private void setBottomNav(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void setBottomNavButtons(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_feed:
                        setFeedFragment();
                        break;
                    case R.id.action_search:

                        goToMap();

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


    private void goToMap() {
        Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
        startActivity(intent);
    }

    private void setProfileFragment(){


    private void setFeedFragment(){

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new FeedFragment())
                .commit();
    }

    private void setSearchFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new SearchFragment())
                .commit();
    }

    private void setAddMediaFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new AddMediaFragment())
                .commit();
    }

    private void setNotificationsFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new NotificationsFragment())
                .commit();
    }


    private void setProfileFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }

}
