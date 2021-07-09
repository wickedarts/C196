package com.example.coursescheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.DetailedCourseViewActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemTitle;
        private final TextView courseItemStartDateTextDynamic;
        private final TextView courseItemEndDateTextDynamic;
        private final TextView courseItemStatus;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemTitle = itemView.findViewById(R.id.courseItemTitleDynamic);
            courseItemStartDateTextDynamic = itemView.findViewById(R.id.courseItemStartDateDynamic);
            courseItemEndDateTextDynamic = itemView.findViewById(R.id.courseItemEndDateDynamic);
            courseItemStatus = itemView.findViewById(R.id.courseItemStatusDynamic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourseViewActivity.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTermID", current.getCourseTermID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("courseMentorName", current.getCourseMentor());
                    intent.putExtra("courseMentorPhone", current.getCourseMentorPhone());
                    intent.putExtra("courseMentorEmail", current.getCourseMentorEmail());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);

        return new CourseViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if(mCourses != null) {
            final CourseEntity current = mCourses.get(position);
            holder.courseItemTitle.setText(current.getCourseName());
            holder.courseItemStartDateTextDynamic.setText(current.getStartDate().toString());
            holder.courseItemEndDateTextDynamic.setText(current.getEndDate().toString());
            holder.courseItemStatus.setText(current.getCourseStatus());
        }
        else {
            holder.courseItemTitle.setText("No Word");
            holder.courseItemStartDateTextDynamic.setText("No Word");
            holder.courseItemEndDateTextDynamic.setText("No Word");
            holder.courseItemStatus.setText("No Word");
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    public void setWords(List<CourseEntity> words) {
        mCourses = words;
        notifyDataSetChanged();
    }

}
