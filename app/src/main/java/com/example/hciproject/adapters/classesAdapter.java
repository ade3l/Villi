package com.example.hciproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hciproject.R;
import com.example.hciproject.objects.Classes;

import java.util.List;

public class classesAdapter extends RecyclerView.Adapter<classesAdapter.classesItemViewHolder>{
    Context context;
    List<Classes> classes;

    public classesAdapter(Context context, List<Classes> classes) {
        this.context = context;
        this.classes = classes;
    }

    @NonNull
    @Override
    public classesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_classes,parent,false);
        return new classesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull classesItemViewHolder holder, int position) {
        Classes myClass =classes.get(position);
        holder.subjectTV.setText(myClass.getSubject());
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    static class classesItemViewHolder extends RecyclerView.ViewHolder{
        TextView subjectTV,timeTV;
        public classesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTV= itemView.findViewById(R.id.subject);
            timeTV= itemView.findViewById(R.id.time);

        }
    }
}
