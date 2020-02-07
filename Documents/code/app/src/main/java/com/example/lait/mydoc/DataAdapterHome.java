package com.example.lait.mydoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapterHome  extends RecyclerView.Adapter<ViewHolder>{

    ArrayList<String> persons;

    LayoutInflater inflater;

    public DataAdapterHome(Context context, ArrayList<String> persons) {
        this.persons = persons;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doctor_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String per = persons.get(position);
        holder.person.setText(per);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}

