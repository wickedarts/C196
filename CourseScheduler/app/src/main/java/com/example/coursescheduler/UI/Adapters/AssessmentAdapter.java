package com.example.coursescheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.DetailedAssessmentViewActivity;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitle;
        private final TextView assessmentStartDate;
        private final TextView assessmentType;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.assessmentTitleTextDynamic);
            assessmentStartDate = itemView.findViewById(R.id.assessmentStartDateDynamic);
            assessmentType = itemView.findViewById(R.id.assessmentEndDateDynamic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentViewActivity.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("courseID", current.getAssessmentCourseID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentStartDate", current.getStartDate().toString());
                    intent.putExtra("assessmentEndDate", current.getEndDate().toString());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentSelectionPosition", current.getAssessmentSelectionPosition());
                    intent.putExtra("descriptionText", current.getDescriptionText());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }

    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            AssessmentEntity current = mAssessments.get(position);
            holder.assessmentTitle.setText(current.getAssessmentName());
            holder.assessmentStartDate.setText(current.getStartDate().toString());
            holder.assessmentType.setText(current.getAssessmentType());
        }
        else {
            holder.assessmentTitle.setText("No Word");
            holder.assessmentStartDate.setText("No Word");
            holder.assessmentType.setText("No Word");
        }
    }

    public void setWords(List<AssessmentEntity> words) {
        mAssessments = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}
