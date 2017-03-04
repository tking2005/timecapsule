package nyc.c4q.hakeemsackes_bramble.timecapsule.FeedActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import nyc.c4q.hakeemsackes_bramble.timecapsule.FeedActivity.controller.FeedAdapter;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = (RecyclerView) findViewById(R.id.rv_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new FeedAdapter());
    }
}
