package com.timecapsule.app.feedactivity.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.feedactivity.view.FeedViewHolder;

import com.timecapsule.app.R;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new FeedViewHolder(new TextView(parent.getContext()));
        View childview = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_feed, parent, false);
        return new FeedViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        int num = 0;
        for (int i = 0; i < 12 ; i++) {
            num = i;
        }
        return num;
    }
}
