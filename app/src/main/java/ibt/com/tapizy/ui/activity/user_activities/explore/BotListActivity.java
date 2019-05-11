package ibt.com.tapizy.ui.activity.user_activities.explore;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatbotListAdapter;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.api_bot_list.BotListMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.UserBotChatActivity;
import ibt.com.tapizy.ui.activity.user_activities.HomeActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class BotListActivity extends BaseActivity implements View.OnClickListener {

    private List<BotList> chatbotLists = new ArrayList<>();
    private ChatbotListAdapter chatbotListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_list);

        init();
    }

    private void init() {
        findViewById(R.id.ic_back_newactivity).setOnClickListener(this);
        RecyclerView recyclerViewBotList = findViewById(R.id.recyclerViewBotList);

        chatbotListAdapter = new ChatbotListAdapter(mContext, chatbotLists, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewBotList.setLayoutManager(mLayoutManager);
        recyclerViewBotList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBotList.setAdapter(chatbotListAdapter);

        String strUserId = User.getUser().getUser().getUid();
        String strCatId = getIntent().getStringExtra("cat_name");
        botListApi(strUserId, strCatId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back_newactivity:
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.llChatbot:
                int pos = Integer.parseInt(v.getTag().toString());
                //Intent intentA = new Intent(mContext, ChatActivity.class);
                Intent intentA = new Intent(mContext, UserBotChatActivity.class);
                intentA.putExtra("bot_data", (Parcelable) chatbotLists.get(pos));
                startActivity(intentA);
                break;
        }
    }

    private void botListApi(String strUserId, String strCatId) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.botListResponse(new Dialog(mContext), retrofitApiClient.botList(strCatId, strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    BotListMainModal botListMainModal = (BotListMainModal) result.body();
                    chatbotLists.clear();
                    if (botListMainModal != null) {
                        if (botListMainModal.getBotList() != null) {
                            findViewById(R.id.tvEmpty).setVisibility(View.GONE);
                            chatbotLists.addAll(botListMainModal.getBotList());
                        } else {
                            findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
                        }
                    }
                    chatbotListAdapter.notifyDataSetChanged();
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