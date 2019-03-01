package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.QuestionList;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private List<QuestionList> chatbotLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public QuestionListAdapter(Context context, List<QuestionList> chatbotLists, View.OnClickListener onClickListener) {
        this.chatbotLists = chatbotLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_question_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvQuestion.setText(chatbotLists.get(position).getName());
        holder.tvQuestion.setTag(position);
        holder.tvQuestion.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chatbotLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;

        public ViewHolder(View v) {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQuestion);
        }
    }
}
