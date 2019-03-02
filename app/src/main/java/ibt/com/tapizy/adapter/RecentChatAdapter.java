package ibt.com.tapizy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import ibt.com.tapizy.model.api_chat_list.ChatList;

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.ViewHolder> {

    private View rootview;
    private Context context;
    private List<ChatList> recentChatList;
    private View.OnClickListener onClickListener;

    public RecentChatAdapter(Context context, List<ChatList> recentChatList, View.OnClickListener onClickListener) {
        this.context = context;
        this.recentChatList = recentChatList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rootview = layoutInflater.inflate(R.layout.custom_recent_chat_list, null);
        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChatList recentchatmodal = recentChatList.get(i);
        if (recentchatmodal.getAvtar() != null) {
            Glide.with(context)
                    .load(Constant.PROFILE_IMAGE_BASE_URL +recentchatmodal.getAvtar())
                    .into(viewHolder.profileImage);
        }
        viewHolder.chatPersonName.setText(recentchatmodal.getBotName());

        viewHolder.llBot.setTag(i);
        viewHolder.llBot.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public int getItemCount() {
        return recentChatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView chatPersonName;
        private LinearLayout llBot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llBot = itemView.findViewById(R.id.llBot);
            profileImage = itemView.findViewById(R.id.iv_recent_profile_img);
            chatPersonName = itemView.findViewById(R.id.tv_chat_person_name);
        }
    }
}
