package com.timecapsule.app.feedactivity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timecapsule.app.R;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    TextView tv_numbers;

    public FeedViewHolder(View itemView) {
        super(itemView);
        tv_numbers = (TextView) itemView.findViewById(R.id.tv_number);

    }

    public void bind(Integer integer) {
        tv_numbers.setText(String.valueOf(integer));
    }
}
