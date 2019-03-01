package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.api_bot_list.BotList;

public class ChatbotListAdapter extends RecyclerView.Adapter<ChatbotListAdapter.ViewHolder> {

    private List<BotList> chatbotLists;
    private Context context;
    private View.OnClickListener onClickListener;

    public ChatbotListAdapter(Context context, List<BotList> chatbotLists, View.OnClickListener onClickListener) {
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
        holder.tvChatbotName.setText(chatbotLists.get(position).getBotName());

        if (chatbotLists.get(position).getAvtar() != null) {
            Glide.with(context)
                    .load(Constant.PROFILE_IMAGE_BASE_URL + chatbotLists.get(position).getAvtar())
                    .into(holder.imgBot);
        }
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

        private LinearLayout llChatbot;
        private TextView tvChatbotName;
        private ImageView imgBot;

        public ViewHolder(View v) {
            super(v);
            tvChatbotName = v.findViewById(R.id.tvChatbotName);
            llChatbot = v.findViewById(R.id.llChatbot);
            imgBot = v.findViewById(R.id.imgBot);
        }
    }
}
