package ibt.com.tapizy.ui.activity.recent_chat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.RecentChatAdapter;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.api_chat_list.ChatList;
import ibt.com.tapizy.model.api_chat_list.ChatListMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.chatbot_activity.ChatActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class RecentChatActivity extends BaseActivity implements View.OnClickListener {

    private ImageView icBackRecentChat;
    private RecyclerView rclvRecentChat;
    private RecentChatAdapter recentChatAdapter;
    private List<ChatList> recentChatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        init();
    }

    private void init() {
        rclvRecentChat = findViewById(R.id.rclv_recent_chat);
        icBackRecentChat = findViewById(R.id.ic_back_recent_chat);
        icBackRecentChat.setOnClickListener(this);
        rclvRecentChat.setHasFixedSize(true);
        rclvRecentChat.setLayoutManager(new LinearLayoutManager(this));

        recentChatAdapter = new RecentChatAdapter(mContext, recentChatList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rclvRecentChat.setLayoutManager(layoutManager);
        rclvRecentChat.setItemAnimator(new DefaultItemAnimator());
        rclvRecentChat.setAdapter(recentChatAdapter);

        chatListApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back_recent_chat:
                finish();
                break;
            case R.id.llBot:
                int pos = Integer.parseInt(v.getTag().toString());
                BotList botData = new BotList();
                botData.setAvtar(recentChatList.get(pos).getAvtar());
                botData.setBotName(recentChatList.get(pos).getBotName());
                botData.setUid(recentChatList.get(pos).getBotId());
                Intent intentA = new Intent(mContext, ChatActivity.class);
                intentA.putExtra("bot_data", (Parcelable) botData);
                startActivity(intentA);
                break;
        }
    }

    private void chatListApi() {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getChatListData(new Dialog(mContext), retrofitApiClient.chatList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ChatListMainModal chatListMainModal = (ChatListMainModal) result.body();
                    recentChatList.clear();
                    if (chatListMainModal != null) {
                        if (chatListMainModal.getConversationBotList() != null) {
                            recentChatList.addAll(chatListMainModal.getConversationBotList());
                        }
                    }
                    recentChatAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }
}
