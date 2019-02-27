package infobite.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infobite.com.tapizy.R;

public class SubQuestionListAdapter extends RecyclerView.Adapter<SubQuestionListAdapter.ViewHolder> {

    private List<String> subQuestionList;
    private Context context;

    public SubQuestionListAdapter(Context context, List<String> subQuestionList) {
        this.subQuestionList = subQuestionList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_question_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtQuestion.setText(subQuestionList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return subQuestionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtQuestion;

        public ViewHolder(View v) {
            super(v);
            txtQuestion = v.findViewById(R.id.txtQuestion);
        }
    }
}
