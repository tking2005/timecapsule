package nyc.c4q.hakeemsackes_bramble.timecapsule.profilefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import nyc.c4q.hakeemsackes_bramble.timecapsule.AddMediaFragment;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;

/**
 * Created by catwong on 3/6/17.
 */

public class EditProfileFragment extends Fragment {

    private View mRoot;
    private TextView tv_cancel;
    private TextView tv_done;
    private TextView tv_edit_profile;
    private TextView tv_change_profile_photo;
    private ImageView iv_name;
    private ImageView iv_username;
    private ImageView iv_email;
    private EditText et_name;
    private EditText et_username;
    private EditText et_email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_edit_profile, parent, false);
        setViews();
        clickCancel();
        return mRoot;
    }

    public void setViews(){
        tv_cancel = (TextView) mRoot.findViewById(R.id.tv_edit_profile_cancel);
        tv_done = (TextView) mRoot.findViewById(R.id.tv_edit_profile_done);
        tv_edit_profile = (TextView) mRoot.findViewById(R.id.tv_edit_profile_top);
        tv_change_profile_photo = (TextView) mRoot.findViewById(R.id.tv_edit_profile_change_photo);
        iv_name = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_name);
        iv_username = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_username);
        iv_email = (ImageView) mRoot.findViewById(R.id.iv_edit_profile_email);
        et_name = (EditText) mRoot.findViewById(R.id.et_edit_profile_name);
        et_username = (EditText) mRoot.findViewById(R.id.et_edit_profile_username);
        et_email = (EditText) mRoot.findViewById(R.id.et_edit_profile_email);
    }

    public void clickCancel(){
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancel();
            }
        });
    }

    public void setCancel(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .commit();
    }
}