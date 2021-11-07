package com.example.hciproject.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class classesAdapter extends RecyclerView.Adapter<classesAdapter.classesItemViewHolder>{
    @NonNull
    @Override
    public classesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull classesItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class classesItemViewHolder extends RecyclerView.ViewHolder{
        public classesItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
