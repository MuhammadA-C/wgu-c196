package com.muhammadchambersc196.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.helper.SelectedListItem;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course currentCourse = mCourses.get(position);

                    SelectedListItem.setSelectedCourse(currentCourse);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_v1, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String name = current.getTitle();
            holder.courseItemView.setText(name);
        } else {
            holder.courseItemView.setText("No Course Name");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        }
        return 0;
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}
