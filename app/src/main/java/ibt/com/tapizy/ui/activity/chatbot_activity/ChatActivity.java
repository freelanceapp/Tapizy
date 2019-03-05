package ibt.com.tapizy.ui.activity.chatbot_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatListAdapter;
import ibt.com.tapizy.adapter.QuestionListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.ChatQuestionList;
import ibt.com.tapizy.model.ChatSubItems;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationList;
import ibt.com.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import ibt.com.tapizy.model.chat_history.ChatHistoryMainModal;
import ibt.com.tapizy.model.communication.CommunicationMainModal;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.AppProgressDialog;
import ibt.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private QuestionListAdapter questionListAdapter;
    private String strBotId, strUserId, strMainQuesId, strSubQuesId, strFrom;

    private ChatListAdapter chatListAdapter;

    private List<ChatQuestionList> questionArrayLists = new ArrayList<>();
    private List<ChatSubItems> chatList = new ArrayList<>();
    private List<ApiConversationList> conversationLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_chat);

        init();
    }

    private void init() {
        findViewById(R.id.imgAddFav).setOnClickListener(this);
        findViewById(R.id.imgSend).setOnClickListener(this);
        strUserId = User.getUser().getUser().getUid();
        BotList botData = getIntent().getParcelableExtra("bot_data");
        strBotId = botData.getUid();

        if (botData.getAvtar() != null) {
            Glide.with(mContext)
                    .load(Constant.PROFILE_IMAGE_BASE_URL + botData.getAvtar())
                    .into((CircleImageView) findViewById(R.id.imgBot));
        }

        ((TextView) findViewById(R.id.tvChatbotName)).setText(botData.getBotName());
        findViewById(R.id.ivBack).setOnClickListener(this);

        questionListAdapter = new QuestionListAdapter(mContext, questionArrayLists, this);
        chatListAdapter = new ChatListAdapter(mContext, chatList, this, strFrom);

        RecyclerView recyclerViewQuestion = findViewById(R.id.recyclerViewQuestion);
        recyclerViewQuestion.setHasFixedSize(true);
        recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuestion.setAdapter(questionListAdapter);

        RecyclerView recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatList.setAdapter(chatListAdapter);

        questionListAdapter.notifyDataSetChanged();
        chatListAdapter.notifyDataSetChanged();

        chatHistoryApi();
    }

    private void chatHistoryApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getChatHistoryData(new Dialog(mContext), retrofitApiClient.chatHistory(strUserId, strBotId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ChatHistoryMainModal chatHistoryMainModal = (ChatHistoryMainModal) result.body();
                    if (chatHistoryMainModal != null) {
                        if (!chatHistoryMainModal.getError()) {
                            if (chatHistoryMainModal.getChatMsg() != null) {
                                if (chatHistoryMainModal.getChatMsg().size() > 0) {
                                    for (int i = 0; i < chatHistoryMainModal.getChatMsg().size(); i++) {
                                        chatList.add(new ChatSubItems(chatHistoryMainModal.getChatMsg().get(i).getMessageFrom(),
                                                chatHistoryMainModal.getChatMsg().get(i).getMessage()));
                                    }
                                    int size = chatHistoryMainModal.getChatMsg().size();
                                    int pos = size - 1;
                                    if (chatHistoryMainModal.getChatMsg().get(pos).getMessageFrom().equalsIgnoreCase("bot")) {
                                        String strLastMsgId = chatHistoryMainModal.getChatMsg().get(pos).getMsgId();
                                        getConversationList(strLastMsgId);
                                    }
                                } else {
                                    welcomeApi();
                                }
                            } else {
                                welcomeApi();
                            }
                        } else {
                            welcomeApi();
                        }
                    } else {
                        welcomeApi();
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

    private void getConversationList(final String strLastMsgId) {
        final Dialog dialog = new Dialog(mContext);
        AppProgressDialog.show(dialog);
        if (cd.isNetworkAvailable()) {
            RetrofitService.conversationListResponse(retrofitApiClient.selectConversation(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppProgressDialog.hide(dialog);
                    ApiConversationMainModal apiConversationMainModal = (ApiConversationMainModal) result.body();
                    conversationLists.clear();
                    if (!apiConversationMainModal.getError()) {
                        if (apiConversationMainModal.getConversation() != null) {
                            if (apiConversationMainModal.getConversation().size() > 0) {
                                conversationLists.addAll(apiConversationMainModal.getConversation());
                                for (int i = 0; i < conversationLists.size(); i++) {
                                    if (strLastMsgId.equalsIgnoreCase(conversationLists.get(i).getId())) {
                                        if (conversationLists.get(i).getResponseData().size() > 0) {
                                            for (int j = 0; j < conversationLists.get(i).getResponseData().size(); j++) {
                                                ChatQuestionList subQues = new ChatQuestionList();
                                                subQues.setId(conversationLists.get(i).getResponseData().get(j).getSubConId());
                                                subQues.setName(conversationLists.get(i).getResponseData().get(j).getResponseText());
                                                questionArrayLists.add(subQues);
                                            }
                                        }
                                    }
                                }
                            } else {
                                Alerts.show(mContext, "No conversation found");
                            }
                        }
                    } else {
                        Alerts.show(mContext, "No conversation found");
                    }
                    questionListAdapter.notifyDataSetChanged();
                    chatListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    AppProgressDialog.hide(dialog);
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    private void welcomeApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCommunicationWelcomeData(new Dialog(mContext), retrofitApiClient.communicationWelcomeData(
                    strBotId, strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppPreference.setStringPreference(mContext, Constant.TOKEN, "bot");
                    final CommunicationMainModal mainModal = (CommunicationMainModal) result.body();
                    if (!mainModal.getError()) {
                        if (mainModal != null) {
                            strMainQuesId = mainModal.getConversation().getId();

                            chatList.add(new ChatSubItems("bot", mainModal.getWelcomeMessage()));
                            chatList.add(new ChatSubItems("bot", mainModal.getConversation().getText()));

                            if (mainModal.getConversation().getResponseData() != null) {
                                for (int i = 0; i < mainModal.getConversation().getResponseData().size(); i++) {
                                    ChatQuestionList subQues = new ChatQuestionList();
                                    subQues.setId(mainModal.getConversation().getResponseData().get(i).getResponseOptionId());
                                    subQues.setName(mainModal.getConversation().getResponseData().get(i).getResponseOptionMsg());
                                    questionArrayLists.add(subQues);
                                }
                            } else {
                                chatList.add(new ChatSubItems("bot", "Our executive will contact you !!!"));
                            }
                        }
                    } else {
                        Alerts.show(mContext, "No conversation found");
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
            case R.id.imgSend:
                Alerts.show(mContext, "Under Development!!!");
                break;
            case R.id.imgAddFav:
                addToFavApi();
                break;
        }
    }

    private void onChipsClick(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        strSubQuesId = questionArrayLists.get(pos).getId();
        chatList.add(new ChatSubItems("user", questionArrayLists.get(pos).getName()));
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
                            chatList.add(new ChatSubItems("bot", mainModal.getConversation().getText()));
                        }

                        if (mainModal.getConversation().getResponseData() != null) {
                            for (int i = 0; i < mainModal.getConversation().getResponseData().size(); i++) {
                                ChatQuestionList subQues = new ChatQuestionList();
                                subQues.setId(mainModal.getConversation().getResponseData().get(i).getResponseOptionId());
                                subQues.setName(mainModal.getConversation().getResponseData().get(i).getResponseOptionMsg());
                                questionArrayLists.add(subQues);
                            }
                        } else {
                            String strText = "Our executive will contact you !!!";
                            chatList.add(new ChatSubItems("bot", strText));
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

    private void addToFavApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.addToFav(strUserId, strBotId, "1"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, true);
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            //Alerts.show(mContext, jsonObject.getString("message"));
                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
