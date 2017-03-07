package nyc.c4q.hakeemsackes_bramble.timecapsule.profilefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nyc.c4q.hakeemsackes_bramble.timecapsule.AddMediaFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

public class ProfileFragment extends Fragment {

    private View mRoot;
    private Button bt_edit_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_profile, parent, false);
        setViews();
        clickEditProfile();
        return mRoot;
    }

    public void setViews(){
        bt_edit_profile = (Button) mRoot.findViewById(R.id.bt_edit_profile);
    }

    public void clickEditProfile(){
        bt_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditProfile();
            }
        });
    }

    public void setEditProfile(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new EditProfileFragment())
                .commit();
    }
}
