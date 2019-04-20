package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.conversation_modal.NewConversationSubResponseList;

public class ChipsListAdapter extends RecyclerView.Adapter<ChipsListAdapter.ViewHolder> {

    private List<NewConversationSubResponseList> faqLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public ChipsListAdapter(Context context, List<NewConversationSubResponseList> faqLists, View.OnClickListener onClickListener) {
        this.faqLists = faqLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chips_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NewConversationSubResponseList faq = faqLists.get(position);
        holder.tvChips.setText(faq.getMultichoiceOption());
        holder.tvChips.setTag(position);
        holder.tvChips.setOnClickListener(onClickListener);
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

        public TextView tvChips;

        public ViewHolder(View v) {
            super(v);
            tvChips = v.findViewById(R.id.tvChips);
        }
    }
}
