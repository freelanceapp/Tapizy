package ibt.com.tapizy.ui.activity.chatbot_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatListAdapter;
import ibt.com.tapizy.adapter.QuestionListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.QuestionList;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.communication.CommunicationMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerViewQuestion, recyclerViewChatList;
    private QuestionListAdapter questionListAdapter;
    private String strChatbotName, strBotId, strBotAvtar, strUserId;
    private String strMainQuesId, strSubQuesId, strUserType = "";

    private ChatListAdapter chatListAdapter;

    private List<QuestionList> questionArrayLists = new ArrayList<>();
    private List<String> chatList = new ArrayList<>();

    private BotList botData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_chat);

        init();
    }

    private void init() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        botData = getIntent().getParcelableExtra("bot_data");
        strChatbotName = botData.getBotName();
        strBotId = botData.getUid();
        strBotAvtar = botData.getAvtar();

        findViewById(R.id.ivBack).setOnClickListener(this);

        questionListAdapter = new QuestionListAdapter(mContext, questionArrayLists, this);
        chatListAdapter = new ChatListAdapter(mContext, chatList, this);

        recyclerViewQuestion = findViewById(R.id.recyclerViewQuestion);
        recyclerViewQuestion.setHasFixedSize(true);
        recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuestion.setAdapter(questionListAdapter);

        recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatList.setAdapter(chatListAdapter);

        questionListAdapter.notifyDataSetChanged();
        chatListAdapter.notifyDataSetChanged();

        welcomeApi();
    }

    private void welcomeApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCommunicationWelcomeData(new Dialog(mContext), retrofitApiClient.communicationWelcomeData(
                    strBotId, strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppPreference.setStringPreference(mContext, Constant.TOKEN, "bot");
                    final CommunicationMainModal mainModal = (CommunicationMainModal) result.body();
                    if (mainModal != null) {
                        strMainQuesId = mainModal.getConversation().getId();
                        chatList.add(mainModal.getWelcomeMessage());
                        chatList.add(mainModal.getConversation().getText());

                        if (mainModal.getConversation().getResponseData() != null) {
                            for (int i = 0; i < mainModal.getConversation().getResponseData().size(); i++) {
                                QuestionList subQues = new QuestionList();
                                subQues.setId(mainModal.getConversation().getResponseData().get(i).getResponseOptionId());
                                subQues.setName(mainModal.getConversation().getResponseData().get(i).getResponseOptionMsg());
                                questionArrayLists.add(subQues);
                            }
                        } else {
                            chatList.add("Our executive will contact you !!!");
                        }
                    }
                    questionListAdapter.notifyDataSetChanged();
                    chatListAdapter.notifyDataSetChanged();
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
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvQuestion:
                onChipsClick(v);
                break;
        }
    }

    private void onChipsClick(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        strSubQuesId = questionArrayLists.get(pos).getId();
        chatList.add(questionArrayLists.get(pos).getName());
        questionArrayLists.clear();

        AppPreference.setStringPreference(mContext, Constant.TOKEN, "user");

        questionListAdapter.notifyDataSetChanged();
        chatListAdapter.notifyDataSetChanged();
        sendMsgApi();
    }

    private void sendMsgApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCommunicationWelcomeData(new Dialog(mContext), retrofitApiClient.sendMsg(
                    strBotId, strUserId, strMainQuesId, strSubQuesId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppPreference.setStringPreference(mContext, Constant.TOKEN, "bot");
                    final CommunicationMainModal mainModal = (CommunicationMainModal) result.body();
                    if (mainModal != null) {
                        strMainQuesId = mainModal.getConversation().getId();
                        if (mainModal.getConversation().getText() != null) {
                            chatList.add(mainModal.getConversation().getText());
                        }

                        if (mainModal.getConversation().getResponseData() != null) {
                            for (int i = 0; i < mainModal.getConversation().getResponseData().size(); i++) {
                                QuestionList subQues = new QuestionList();
                                subQues.setId(mainModal.getConversation().getResponseData().get(i).getResponseOptionId());
                                subQues.setName(mainModal.getConversation().getResponseData().get(i).getResponseOptionMsg());
                                questionArrayLists.add(subQues);
                            }
                        } else {
                            String strText = "Our executive will contact you !!!";
                            chatList.add(strText);
                        }
                    }
                    questionListAdapter.notifyDataSetChanged();
                    chatListAdapter.notifyDataSetChanged();
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
