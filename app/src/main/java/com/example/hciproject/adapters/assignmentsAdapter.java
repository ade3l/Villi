package com.example.hciproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hciproject.R;
import com.example.hciproject.objects.Assignment;

import org.w3c.dom.Text;

import java.util.List;

public class assignmentsAdapter extends RecyclerView.Adapter<assignmentsAdapter.itemViewHolder> {
    List<Assignment> assignments;


    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_assignment, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.subTV.setText(assignment.getSubject());
        holder.nameTV.setText(assignment.getName());
        holder.dueDateTV.setText(assignment.getDueDate());
        holder.dueTimeTV.setText(assignment.getDueTime());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView subTV, nameTV, dueDateTV, dueTimeTV;
        public itemViewHolder(View itemView) {
            super(itemView);
            subTV= itemView.findViewById(R.id.subject_name);
            nameTV= itemView.findViewById(R.id.assignment_name);
            dueDateTV= itemView.findViewById(R.id.due_date);
            dueTimeTV= itemView.findViewById(R.id.due_time);
        }
    }
}
