package com.firstapp.kyecothreads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<userdata> userList;

    public UserAdapter(List<userdata> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userdata user = userList.get(position);

        String userInfo = "Name: " + user.getName() + ", Points: " + user.getPoints();
        holder.userInfoTextView.setText(userInfo);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userInfoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userInfoTextView = itemView.findViewById(R.id.userInfoTextView);
        }
    }
}
