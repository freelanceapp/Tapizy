package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationList;

import static ibt.com.tapizy.ui.activity.user_activities.chatbot_activity.CreateConversationActivity.createConversationActivity;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {

    private Context context;
    private List<ApiConversationList> conversationLists;
    private View.OnClickListener onClickListener;

    public ConversationListAdapter(Context context, List<ApiConversationList> conversationLists, View.OnClickListener onClickListener) {
        this.context = context;
        this.conversationLists = conversationLists;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conversation_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ApiConversationList apiConversationList = conversationLists.get(position);
        holder.tvQuestion.setText(apiConversationList.getText());

        String strRelateId = apiConversationList.getRelateId();
        String strResponseRelateId = apiConversationList.getResponseRelatedId();

        if (!strRelateId.equalsIgnoreCase("0")) {
            for (int i = 0; i < conversationLists.size(); i++) {
                if (strRelateId.equalsIgnoreCase(conversationLists.get(i).getId())) {
                    for (int j = 0; j < conversationLists.get(i).getResponseData().size(); j++) {
                        if (strResponseRelateId.equalsIgnoreCase(conversationLists.get(i).getResponseData().get(j).getSubConId())) {
                            holder.tvTitle.setText(conversationLists.get(i).getResponseData().get(j).getResponseText());
                        }
                    }
                }
            }
        }

        SubQuestionListAdapter subQuestionListAdapter = new SubQuestionListAdapter(context, conversationLists.get(position).getResponseData(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rlSubQuestion:
                                int pos = Integer.parseInt(v.getTag().toString());
                                createConversationActivity.createChatbotDialog(apiConversationList.getId(),
                                        apiConversationList.getResponseData().get(pos).getResponseText(),
                                        apiConversationList.getResponseData().get(pos).getSubConId());
                                break;
                        }
                    }
                });
        holder.recyclerViewSubQuestion.setHasFixedSize(true);
        holder.recyclerViewSubQuestion.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerViewSubQuestion.setAdapter(subQuestionListAdapter);
        subQuestionListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return conversationLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewSubQuestion;
        public TextView tvQuestion, tvTitle;

        public ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvQuestion = v.findViewById(R.id.tvQuestion);
            recyclerViewSubQuestion = v.findViewById(R.id.recyclerViewSubQuestion);
        }
    }
}
