package nyc.c4q.hakeemsackes_bramble.timecapsule.FeedActivity;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import nyc.c4q.hakeemsackes_bramble.timecapsule.FeedFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.ProfileFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

public class FeedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setBottomNav();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, new FeedFragment())
                    .commit();
        }

    }

    private void setBottomNav(){
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_profile:
                        setProfileFragment();
                        break;
                    case R.id.action_feed:
                        setFeedFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void setProfileFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }

    private void setFeedFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new FeedFragment())
                .commit();
    }
}
