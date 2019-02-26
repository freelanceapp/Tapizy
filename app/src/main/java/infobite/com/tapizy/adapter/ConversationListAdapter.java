package infobite.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.database.DatabaseHandler;
import infobite.com.tapizy.model.conversation_modal.ConversationList;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {

    private List<ConversationList> chatbotLists;
    private List<ConversationList> updateList;
    private Context context;
    private View.OnClickListener onClickListener;
    private DatabaseHandler databaseHandler;

    public ConversationListAdapter(Context context, List<ConversationList> chatbotLists, List<ConversationList> updateList,
                                   View.OnClickListener onClickListener, DatabaseHandler databaseHandler) {
        this.chatbotLists = chatbotLists;
        this.updateList = updateList;
        this.context = context;
        this.onClickListener = onClickListener;
        this.databaseHandler = databaseHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conversation_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //updateList.clear();
        //updateList.addAll(databaseHandler.getAllUrlList());
        for (int i = 0; i < updateList.size(); i++) {
            String strRelateId = chatbotLists.get(position).getRelateId();
            String strId = String.valueOf(updateList.get(i).getId());
            if (strRelateId.equalsIgnoreCase(strId)) {
                String strText = updateList.get(i).getText();
                holder.tvQuestion.setText(strText);
            }
        }

        String strQ = chatbotLists.get(position).getText();
        holder.tvAnswer.setText(strQ);
        holder.llChatbot.setTag(position);
        holder.llChatbot.setOnClickListener(onClickListener);
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

        public LinearLayout llChatbot;
        public TextView tvQuestion, tvAnswer;

        public ViewHolder(View v) {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQuestion);
            tvAnswer = v.findViewById(R.id.tvAnswer);
            llChatbot = v.findViewById(R.id.llChatbot);
        }
    }
}
