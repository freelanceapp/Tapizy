package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.faq_data.FaqList;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.ViewHolder> {

    private List<FaqList> faqLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public FaqListAdapter(Context context, List<FaqList> faqLists, View.OnClickListener onClickListener) {
        this.faqLists = faqLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_faq_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FaqList faq = faqLists.get(position);
        holder.txtQuestion.setText(faq.getQuestion());
        holder.txtComment.setText(faq.getComment());
        holder.txtAdminComment.setText(faq.getAdminComment());

        String status = faq.getStatus();
        if (status.equals("1")) {
            holder.txtStatus.setText("Pending");
        } else if (status.equals("0")) {
            holder.txtStatus.setText("Resolve");
        } else if (status.equals("2")) {
            holder.txtStatus.setText("Rejected");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtQuestion, txtComment, txtStatus, txtAdminComment;

        public ViewHolder(View v) {
            super(v);
            txtQuestion = v.findViewById(R.id.txtQuestion);
            txtComment = v.findViewById(R.id.txtComment);
            txtStatus = v.findViewById(R.id.txtStatus);
            txtAdminComment = v.findViewById(R.id.txtAdminComment);
        }
    }
}
