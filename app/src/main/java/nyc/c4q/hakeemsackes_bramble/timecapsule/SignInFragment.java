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

public class SignInFragment extends Fragment {

    private View mRoot;
    private ImageView iv_signin_bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_sign_in, parent, false);
        setSignIn();
        return mRoot;
    }


    private void setSignIn() {
        iv_signin_bottom = (ImageView) mRoot.findViewById(R.id.iv_signin_bottom);
        iv_signin_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn();
            }
        });
    }

    private void goSignIn() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        SignInFragment.this.startActivity(intent);
    }
}
