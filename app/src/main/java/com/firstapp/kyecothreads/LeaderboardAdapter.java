package com.firstapp.kyecothreads;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardItem> {

    public LeaderboardAdapter(Context context, ArrayList<LeaderboardItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_leaderboard, parent, false);
        }

        LeaderboardItem item = getItem(position);

        TextView rankTextView = convertView.findViewById(R.id.rankTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);

        rankTextView.setText(String.valueOf(item.getRank()));
        nameTextView.setText(item.getName());
        scoreTextView.setText(String.valueOf(item.getPoints())+" ");

        return convertView;
    }
}


