package com.example.user_pc.semnas_ti.admin.datafaq;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.model.FaqResponse;

import java.util.List;

public class FaqAdminAdapter extends RecyclerView.Adapter<FaqAdminAdapter.ViewHolder> {
    private Context context;
    private OnClickListener onClickListener;
    private List<FaqResponse> faqResponses;

    public FaqAdminAdapter(Context context, List<FaqResponse> faqResponses) {
        this.context = context;
        this.faqResponses = faqResponses;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_faq_admin, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FaqResponse faqResponse = faqResponses.get(i);
        viewHolder.bind(faqResponse);
    }

    @Override
    public int getItemCount() {
        return faqResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvName, tvEmail;
        ImageView imQuestion;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question_admin);
            tvName = itemView.findViewById(R.id.tv_name_created_faq);
            tvEmail = itemView.findViewById(R.id.tv_email_created_faq);
            imQuestion = itemView.findViewById(R.id.iv_faq_admin);

            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(FaqResponse faqResponse) {
            tvQuestion.setText(faqResponse.getQuestion());
            tvName.setText(faqResponse.getName());
            tvEmail.setText(faqResponse.getEmail());
            imQuestion.setImageResource(R.drawable.ic_question_answer_blue);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
