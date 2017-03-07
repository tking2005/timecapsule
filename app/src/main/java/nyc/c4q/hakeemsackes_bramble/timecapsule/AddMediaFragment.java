package nyc.c4q.hakeemsackes_bramble.timecapsule;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nyc.c4q.hakeemsackes_bramble.timecapsule.audioactivity.AudioFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.audioactivity.AudioFragment2;
import nyc.c4q.hakeemsackes_bramble.timecapsule.cameraactivity.CameraActivity;
import nyc.c4q.hakeemsackes_bramble.timecapsule.feedactivity.FeedFragment;

/**
 * Created by catwong on 3/4/17.
 */

public class AddMediaFragment extends Fragment {

    private View mRoot;
    private ImageView iv_camera;
    private ImageView iv_audio;

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
                goToCamera();
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



}
