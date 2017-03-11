package com.timecapsule.app.profilefragment.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timecapsule.app.R;

/**
 * Created by catwong on 3/10/17.
 */

public class ProfileCreatedViewHolder extends RecyclerView.ViewHolder {

    TextView tv_numbers;


    public ProfileCreatedViewHolder(View itemView) {
        super(itemView);
        tv_numbers = (TextView) itemView.findViewById(R.id.tv_profile_number);
    }


    public void bind(Integer integer) {
        tv_numbers.setText(String.valueOf(integer));
    }
}
