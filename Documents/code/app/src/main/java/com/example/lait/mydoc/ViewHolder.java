package com.example.lait.mydoc;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView person;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        person = itemView.findViewById(R.id.person);
    }
}
