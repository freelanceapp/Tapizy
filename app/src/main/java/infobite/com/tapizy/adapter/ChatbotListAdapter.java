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
import infobite.com.tapizy.model.database_modal.ChatbotList;

public class ChatbotListAdapter extends RecyclerView.Adapter<ChatbotListAdapter.ViewHolder> {

    private List<ChatbotList> chatbotLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public ChatbotListAdapter(Context context, List<ChatbotList> chatbotLists, View.OnClickListener onClickListener) {
        this.chatbotLists = chatbotLists;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chatbot_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvChatbotName.setText(chatbotLists.get(position).getName());
        holder.llChatbot.setTag(position);
        holder.llChatbot.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return chatbotLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llChatbot;
        public TextView tvChatbotName;

        public ViewHolder(View v) {
            super(v);
            tvChatbotName = v.findViewById(R.id.tvChatbotName);
            llChatbot = v.findViewById(R.id.llChatbot);
        }
    }
}
