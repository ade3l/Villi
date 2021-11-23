package com.example.hciproject.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hciproject.MainActivity;
import com.example.hciproject.R;
import com.example.hciproject.assignmentDetailsActivity;
import com.example.hciproject.data.DataSource;
import com.example.hciproject.objects.Assignment;

import java.util.List;

public class assignmentsAdapter extends RecyclerView.Adapter<assignmentsAdapter.itemViewHolder> {
    List<Assignment> assignments;
    Boolean submitted;
    Context context;
    public assignmentsAdapter(List<Assignment> assignments, Boolean submitted, Context context) {
        this.assignments = assignments;
        this.submitted = submitted;
        this.context = context;
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
        holder.nameTV.setTransitionName("detailsTransition");
        holder.cardView.setOnClickListener(v -> {
            Intent intent= new Intent(context, assignmentDetailsActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    holder.nameTV, "detailsTransition");
            intent.putExtra("assignment name", assignment.getName());
            intent.putExtra("assignment subject", assignment.getSubject());
            intent.putExtra("assignment due date", assignment.getDueDate());
            intent.putExtra("assignment due time", assignment.getDueTime());
            intent.putExtra("assignment description", assignment.getDescription());
            intent.putExtra("assignment id", assignment.getAssignmentID());
            intent.putExtra("assignment submitted", assignment.getSubmittedDate());
            intent.putExtra("assignment submitted time", assignment.getSubmittedTime());

            context.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView subTV, nameTV, dueDateTV, dueTimeTV, dueOrSubTV;
        CardView cardView;
        public itemViewHolder(View itemView) {
            super(itemView);
            subTV= itemView.findViewById(R.id.subject_name);
            nameTV= itemView.findViewById(R.id.assignment_name);
            dueDateTV= itemView.findViewById(R.id.due_date);
            dueTimeTV= itemView.findViewById(R.id.due_time);
            dueOrSubTV= itemView.findViewById(R.id.dueOrSubmitText);
            cardView= itemView.findViewById(R.id.assignment_item);
        }
    }
}
