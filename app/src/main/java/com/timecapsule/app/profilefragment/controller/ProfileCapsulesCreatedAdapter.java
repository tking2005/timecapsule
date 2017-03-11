package com.timecapsule.app.profilefragment.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.view.ProfileCreatedViewHolder;

/**
 * Created by catwong on 3/10/17.
 */

public class ProfileCapsulesCreatedAdapter extends RecyclerView.Adapter<ProfileCreatedViewHolder> {

    @Override
    public ProfileCreatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childview = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_profile_created, parent, false);
        return new ProfileCreatedViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(ProfileCreatedViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        int num = 0;
        for (int i = 0; i < 12; i++) {
            num = i;
        }
        return num;
    }
}
