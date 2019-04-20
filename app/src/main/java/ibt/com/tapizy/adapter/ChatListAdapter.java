package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.conversation_modal.NewConversationQuestionsData;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<NewConversationQuestionsData> chatList;
    private Context mContext;
    private View.OnClickListener onClickListener;

    public ChatListAdapter(Context mContext, List<NewConversationQuestionsData> chatList, View.OnClickListener onClickListener) {
        this.chatList = chatList;
        this.mContext = mContext;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String strUserType = chatList.get(position).getFrom();
        if (strUserType.equalsIgnoreCase("bot")) {
            holder.cardChat.setVisibility(View.VISIBLE);
            holder.cardChatUser.setVisibility(View.GONE);
            holder.tvQuestion.setText(chatList.get(position).getResponse());
        } else {
            holder.cardChat.setVisibility(View.GONE);
            holder.cardChatUser.setVisibility(View.VISIBLE);
            holder.tvUser.setText(chatList.get(position).getResponse());
        }

        ChipsListAdapter chipsListAdapter = new ChipsListAdapter(mContext, chatList.get(position).getSubResponse(), onClickListener);
        holder.recyclerViewChips.setHasFixedSize(true);
        holder.recyclerViewChips.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewChips.setAdapter(chipsListAdapter);
        chipsListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewChips;
        private RelativeLayout rlMainView;
        private TextView tvQuestion, tvUser;
        private CardView cardChatUser, cardChat;

        public ViewHolder(View v) {
            super(v);
            cardChat = v.findViewById(R.id.cardChat);
            cardChatUser = v.findViewById(R.id.cardChatUser);
            recyclerViewChips = v.findViewById(R.id.recyclerViewChips);
            rlMainView = v.findViewById(R.id.rlMainView);
            tvQuestion = v.findViewById(R.id.tvQuestion);
            tvUser = v.findViewById(R.id.tvUser);
        }
    }
}
