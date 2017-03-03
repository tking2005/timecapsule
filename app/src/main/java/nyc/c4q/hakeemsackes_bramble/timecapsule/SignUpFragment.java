package nyc.c4q.hakeemsackes_bramble.timecapsule;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by catwong on 3/2/17.
 */

public class SignUpFragment extends Fragment {

    private View mRoot;
    private ImageView iv_signup_bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_sign_up, parent, false);
        setSignUp();
        return mRoot;
    }

    private void setSignUp(){
        iv_signup_bottom = (ImageView) mRoot.findViewById(R.id.iv_signup_bottom);
        iv_signup_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();

            }
        });
    }

    private void goSignUp(){
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        SignUpFragment.this.startActivity(intent);
    }
}
