package infobite.com.tapizy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.model.RecentChatModal;

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.ViewHolder> {
    private View rootview;
    private Context context;
    private List<RecentChatModal> recentChatList;

    public RecentChatAdapter(Context context, List<RecentChatModal> recentChatList) {
        this.context = context;
        this.recentChatList = recentChatList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        rootview = layoutInflater.inflate(R.layout.custom_recent_chat_list,null);
        return new ViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RecentChatModal recentchatmodal  = recentChatList.get(i);
        viewHolder.profileImage.setImageDrawable(context.getResources().getDrawable(recentchatmodal.getPrifileImage()));
        viewHolder.chatPersonName.setText(recentchatmodal.getName());
    }

    @Override
    public int getItemCount() {
        return recentChatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView profileImage;
        private TextView chatPersonName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.iv_recent_profile_img);
            chatPersonName = itemView.findViewById(R.id.tv_chat_person_name);
        }
    }
}
