package ibt.com.tapizy.ui.fragment.user_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatbotListAdapter;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.api_bot_list.BotListMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.bot_activities.BotHomeActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseFragment;
import ibt.com.tapizy.utils.ConnectionDetector;
import retrofit2.Response;

public class MyBotListFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    private List<BotList> chatbotLists = new ArrayList<>();
    private ChatbotListAdapter chatbotListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_my_bot_list, container, false);
        activity = getActivity();
        mContext = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        //rootView.findViewById(R.id.llChatbot).setOnClickListener(this);

        RecyclerView recyclerViewBotList = rootView.findViewById(R.id.recyclerViewBotList);

        chatbotListAdapter = new ChatbotListAdapter(mContext, chatbotLists, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewBotList.setLayoutManager(mLayoutManager);
        recyclerViewBotList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBotList.setAdapter(chatbotListAdapter);
        botListApi();
    }

    private void botListApi() {
        String strUserId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.botListResponse(new Dialog(mContext), retrofitApiClient.myBotList(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    BotListMainModal botListMainModal = (BotListMainModal) result.body();
                    chatbotLists.clear();
                    if (botListMainModal != null) {
                        if (botListMainModal.getBotList() != null) {
                            chatbotLists.addAll(botListMainModal.getBotList());
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

    @Override
    public void onClick(View v) {
        startActivity(new Intent(mContext, BotHomeActivity.class));
    }
}
