package com.example.coursescheduler.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.TermEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.CourseActivity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemTitle;
        private final TextView termItemStartDateTextDynamic;
        private final TextView termItemEndDateTextDynamic;


        private TermViewHolder(View itemView) {
            super(itemView);
            termItemTitle = itemView.findViewById(R.id.termItemTitle);
            termItemStartDateTextDynamic = itemView.findViewById(R.id.termItemStartDateTextDynamic);
            termItemEndDateTextDynamic = itemView.findViewById(R.id.termItemEndDateTextDynamic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("startDate", current.getStartDate().toString());
                    intent.putExtra("endDate", current.getEndDate().toString());
                    intent.putExtra("currentTerm", current.getCurrentTerm());
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms; // Cached copy of words

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        if (mTerms != null) {
            final TermEntity current = mTerms.get(position);
            holder.termItemTitle.setText(current.getTermName());
            holder.termItemStartDateTextDynamic.setText(current.getStartDate().toString());//All text fields require strings
            holder.termItemEndDateTextDynamic.setText(current.getEndDate().toString());//All text fields require strings

        } else {
            // Covers the case of data not being ready yet.
            holder.termItemTitle.setText("No Term");
            holder.termItemStartDateTextDynamic.setText("No Term");
            holder.termItemEndDateTextDynamic.setText("No Term");
        }
    }
    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }

    public void setWords(List<TermEntity> words) {
        mTerms = words;
        notifyDataSetChanged();
    }

}