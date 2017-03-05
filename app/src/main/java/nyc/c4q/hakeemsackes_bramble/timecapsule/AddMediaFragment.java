package nyc.c4q.hakeemsackes_bramble.timecapsule;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import nyc.c4q.hakeemsackes_bramble.timecapsule.hakeem.CameraActivity;

/**
 * Created by catwong on 3/4/17.
 */

public class AddMediaFragment extends Fragment {

    private View mRoot;
    private ImageView ib_camera;

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
        return mRoot;
    }

    private void setViews(){
        ib_camera = (ImageView) mRoot.findViewById(R.id.ib_camera);
    }

    private void clickCamera(){
        ib_camera.setOnClickListener(new View.OnClickListener() {
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

}
