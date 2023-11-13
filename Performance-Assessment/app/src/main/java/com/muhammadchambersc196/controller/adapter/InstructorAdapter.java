package com.muhammadchambersc196.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.entities.CourseInstructor;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {
    private List<CourseInstructor> mInstructors;
    private final Context context;
    private final LayoutInflater mInflater;

    public InstructorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder {
        private final TextView instructorItemView;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            instructorItemView = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CourseInstructor currentInstructor = mInstructors.get(position);

                    //SelectedListItem.setSelectedCourse(currentInstructor);
                }
            });
        }
    }

    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_v1, parent, false);
        return new InstructorAdapter.InstructorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if (mInstructors != null) {
            CourseInstructor current = mInstructors.get(position);
            String name = current.getName();
            holder.instructorItemView.setText(name);
        } else {
            holder.instructorItemView.setText("No Instructor Name");
        }
    }

    @Override
    public int getItemCount() {
        if (mInstructors != null) {
            return mInstructors.size();
        }
        return 0;
    }

    public void setInstructors(List<CourseInstructor> instructors) {
        mInstructors = instructors;
        notifyDataSetChanged();
    }
}
