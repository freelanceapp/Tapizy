package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.model.ChatSubItems;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<ChatSubItems> chatList;
    private Context context;
    private View.OnClickListener onClickListener;
    private String strFrom;

    public ChatListAdapter(Context context, List<ChatSubItems> chatList, View.OnClickListener onClickListener, String strFrom) {
        this.chatList = chatList;
        this.context = context;
        this.onClickListener = onClickListener;
        this.strFrom = strFrom;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_list, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String strText = chatList.get(position).getMsg();
        if (strText.isEmpty()) {
            holder.tvQuestion.setText("Our executive will call you later.");
        } else {
            holder.tvQuestion.setText(chatList.get(position).getMsg());
        }

        //String strUserType = AppPreference.getStringPreference(context, Constant.TOKEN);
        String strUserType = chatList.get(position).getFrom();
        if (strUserType.equalsIgnoreCase("bot")) {
            holder.rlMainView.setGravity(Gravity.LEFT);
        } else {
            holder.rlMainView.setGravity(Gravity.RIGHT);
        }
        /*if (chatList.size() > 0) {
            int pos = chatList.size() - 1;
            if (position == pos) {
                if (strUserType.equalsIgnoreCase("bot")) {
                    holder.rlMainView.setGravity(Gravity.LEFT);
                } else {
                    holder.rlMainView.setGravity(Gravity.RIGHT);
                }
            }
        }*/
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

        public static LoadingDots loadingDots;
        private RelativeLayout rlMainView;
        public TextView tvQuestion;

        public ViewHolder(View v) {
            super(v);
            loadingDots = v.findViewById(R.id.loadingDots);
            rlMainView = v.findViewById(R.id.rlMainView);
            tvQuestion = v.findViewById(R.id.tvQuestion);
        }
    }
}
