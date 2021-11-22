package com.example.hciproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hciproject.R;
import com.example.hciproject.data.DataSource;
import com.example.hciproject.objects.Assignment;

import java.util.List;

public class assignmentsAdapter extends RecyclerView.Adapter<assignmentsAdapter.itemViewHolder> {
    List<Assignment> assignments;
    Boolean submitted;
    public assignmentsAdapter(List<Assignment> assignments, Boolean submitted){
        this.assignments = assignments;
        this.submitted = submitted;
    }

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
        if(!assignment.getSubject().equals("")){
            holder.subTV.setText(assignment.getSubject());
            holder.subTV.setVisibility(View.VISIBLE);
        }
        holder.nameTV.setText(assignment.getName());
        if(submitted){
            holder.dueOrSubTV.setText("Submitted on: ");
            holder.dueTimeTV.setVisibility(View.GONE);
        }
        else{
            holder.dueOrSubTV.setText("Due on: ");
        }
        holder.dueDateTV.setText(DataSource.getDateFromMillis(Long.parseLong(assignment.getDueDate()))+" ");
        holder.dueTimeTV.setText(assignment.getDueTime());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView subTV, nameTV, dueDateTV, dueTimeTV, dueOrSubTV;
        public itemViewHolder(View itemView) {
            super(itemView);
            subTV= itemView.findViewById(R.id.subject_name);
            nameTV= itemView.findViewById(R.id.assignment_name);
            dueDateTV= itemView.findViewById(R.id.due_date);
            dueTimeTV= itemView.findViewById(R.id.due_time);
            dueOrSubTV= itemView.findViewById(R.id.dueOrSubmitText);
        }
    }
}
