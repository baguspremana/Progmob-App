package com.example.user_pc.semnas_ti.auth.faquser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.FaqUserResponse;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    private Context context;
    private OnClickListener onClickListener;
    private List<FaqUserResponse> faqUserResponses;

    public FaqAdapter(Context context, List<FaqUserResponse> faqUserResponses) {
        this.context = context;
        this.faqUserResponses = faqUserResponses;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_faq, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FaqUserResponse faqUserResponse = faqUserResponses.get(i);
        viewHolder.bind(faqUserResponse);
    }

    @Override
    public int getItemCount() {
        return faqUserResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvTgl;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question_user);
            tvTgl = itemView.findViewById(R.id.tv_tgl_updated_faq_user);
            img = itemView.findViewById(R.id.iv_faq_user);

            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(FaqUserResponse faqUserResponse) {
            String tgl = DateFormated.setDate(faqUserResponse.getUpdatedAt());
            tvQuestion.setText(faqUserResponse.getQuestion());
            tvTgl.setText(tgl);
            img.setImageResource(R.drawable.ic_question_answer_blue);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
