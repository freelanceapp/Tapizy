package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.model.api_conversation_modal.ApiSubResponseList;

public class SubQuestionListAdapter extends RecyclerView.Adapter<SubQuestionListAdapter.ViewHolder> {

    private List<ApiSubResponseList> subQuestionList;
    private Context context;
    private View.OnClickListener onClickListener;

    public SubQuestionListAdapter(Context context, List<ApiSubResponseList> subQuestionList, View.OnClickListener onClickListener) {
        this.subQuestionList = subQuestionList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_question_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtQuestion.setText(subQuestionList.get(position).getResponseText());
        holder.rlSubQuestion.setTag(position);
        holder.rlSubQuestion.setOnClickListener(onClickListener);

        if (onClickListener == null) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgClick.setVisibility(View.GONE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgClick.setVisibility(View.VISIBLE);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subQuestionList.remove(position);
                notifyDataSetChanged();
            }
        });
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

        private RelativeLayout rlSubQuestion;
        public TextView txtQuestion;
        private ImageView imgClick, imgDelete;

        public ViewHolder(View v) {
            super(v);
            imgDelete = v.findViewById(R.id.imgDelete);
            imgClick = v.findViewById(R.id.imgClick);
            rlSubQuestion = v.findViewById(R.id.rlSubQuestion);
            txtQuestion = v.findViewById(R.id.txtQuestion);
        }
    }
}
