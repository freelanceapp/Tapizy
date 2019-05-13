package infobite.ibt.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.click_listener_interface.CustomClickListener;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationQuestionsData;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationSubResponseList;

public class ChipsListAdapter extends RecyclerView.Adapter<ChipsListAdapter.ViewHolder> {

    private List<NewConversationSubResponseList> faqLists;
    private Context context;
    private NewConversationQuestionsData questionsData;
    private CustomClickListener customClickListener;
    private int parentPosition;

    public ChipsListAdapter(Context context, List<NewConversationSubResponseList> faqLists, NewConversationQuestionsData questionsData,
                            CustomClickListener customClickListener, int parentPosition) {
        this.faqLists = faqLists;
        this.context = context;
        this.questionsData = questionsData;
        this.customClickListener = customClickListener;
        this.parentPosition = parentPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chips_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewConversationSubResponseList faq = faqLists.get(position);
        holder.tvChips.setText(faq.getMultichoiceOption());

        holder.tvChips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!faq.getMsgSequence().isEmpty()) {
                    customClickListener.getPosition(parentPosition, position, questionsData, faq);
                }
            }
        });
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
