package com.muhammadchambersc196.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.helper.SelectedListItem;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<CourseNote> mCourseNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CourseNote currentNote = mCourseNotes.get(position);

                    SelectedListItem.setSelectedNote(currentNote);
                }
            });
        }
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_v1, parent, false);
        return new NoteAdapter.NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if (mCourseNotes != null) {
            CourseNote current = mCourseNotes.get(position);
            String name = current.getNote();
            holder.noteItemView.setText(name);
        } else {
            holder.noteItemView.setText("No Course Note");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourseNotes != null) {
            return mCourseNotes.size();
        }
        return 0;
    }

    public void setNotes(List<CourseNote> courseNote) {
        mCourseNotes = courseNote;
        notifyDataSetChanged();
    }
}
