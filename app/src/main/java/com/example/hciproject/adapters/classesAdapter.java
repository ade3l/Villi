package com.example.hciproject.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hciproject.R;
import com.example.hciproject.data.DataSource;
import com.example.hciproject.fragments.timetableFragment;
import com.example.hciproject.objects.Classes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        holder.timeTV.setText(String.format("%s - %s",myClass.getStartTime(),myClass.getEndTime()));
        holder.classId.setText(myClass.getClassId());
        holder.item.setOnLongClickListener(view -> {
            deleteConfirm(myClass.getClassId());
            return false;
        });
    }

    private void deleteConfirm(String classId) {
        AlertDialog confirmationDialog=new MaterialAlertDialogBuilder(context,R.style.cancelDialogTheme)
                .setTitle("Confirm delete class")
                .setMessage("This class will be removed. This change cannot be undone")
                .setPositiveButton("Cancel",null)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataSource.delete(classId);
                        Toast.makeText(context, "Classes deleted", Toast.LENGTH_SHORT).show();
                        timetableFragment.refreshClasses();
                    }
                }).create();
        confirmationDialog.show();

    }


    @Override
    public int getItemCount() {
        return classes.size();
    }

    static class classesItemViewHolder extends RecyclerView.ViewHolder{
        TextView subjectTV,timeTV,classId;
        CardView item;
        public classesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTV= itemView.findViewById(R.id.subject);
            timeTV= itemView.findViewById(R.id.time);
            item=itemView.findViewById(R.id.item);
            classId=itemView.findViewById(R.id.classId);
        }
    }
}
