package ibt.com.tapizy.ui.activity.user_activities.chatbot_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyalbira.loadingdots.LoadingDots;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.conversation_modal.NewConversationMainModal;
import ibt.com.tapizy.model.conversation_modal.NewConversationQuestionsData;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private int finalMsgSeq;

    private BotList botData;
    private String strUserId;
    private ChatListAdapter chatListAdapter;
    private List<NewConversationQuestionsData> chatList = new ArrayList<>();
    private NewConversationMainModal apiConversationMainModal;
    private LoadingDots loadingDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_chat);

        init();
    }

    private void init() {
        loadingDots = findViewById(R.id.loadingDots);
        loadingDots.setVisibility(View.VISIBLE);
        botData = getIntent().getParcelableExtra("bot_data");

        Glide.with(mContext)
                .load(Constant.BOT_PROFILE_IMAGE + botData.getAvtar())
                .placeholder(R.drawable.img_chatbot)
                .into((CircleImageView) findViewById(R.id.imgBot));
        ((TextView) findViewById(R.id.tvChatbotName)).setText(botData.getBotName());

        findViewById(R.id.imgAddFav).setOnClickListener(this);
        findViewById(R.id.imgSend).setOnClickListener(this);
        strUserId = User.getUser().getUser().getUid();

        findViewById(R.id.ivBack).setOnClickListener(this);

        chatListAdapter = new ChatListAdapter(mContext, chatList, this);

        RecyclerView recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatList.setAdapter(chatListAdapter);

        chatListAdapter.notifyDataSetChanged();

        getConversationList("0", "0", "0");
    }

    private void getConversationList(String relateId, String optionRelateId, String msgSequence) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getConversationList(null, retrofitApiClient.conversationApi(
                    "1", "1", relateId, optionRelateId, msgSequence), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    apiConversationMainModal = (NewConversationMainModal) result.body();
                    if (!apiConversationMainModal.getError()) {
                        NewConversationQuestionsData questionsData = new NewConversationQuestionsData();
                        questionsData.setQuestionId(apiConversationMainModal.getQuestions().getQuestionId());
                        questionsData.setBotId(apiConversationMainModal.getQuestions().getBotId());
                        questionsData.setErrorMessage(apiConversationMainModal.getQuestions().getErrorMessage());
                        questionsData.setFrom("bot");
                        questionsData.setMsgSequence(apiConversationMainModal.getQuestions().getMsgSequence());
                        questionsData.setOptionRelateId(apiConversationMainModal.getQuestions().getOptionRelateId());
                        questionsData.setRelateId(apiConversationMainModal.getQuestions().getRelateId());
                        questionsData.setResponse(apiConversationMainModal.getQuestions().getResponse());
                        questionsData.setType(apiConversationMainModal.getQuestions().getType());
                        questionsData.setSubResponse(apiConversationMainModal.getQuestions().getSubResponse());
                        chatList.add(questionsData);

                        if (apiConversationMainModal.getQuestions().getSubResponse().size() == 0) {
                            loadingDots.setVisibility(View.VISIBLE);
                            String msgSequence = apiConversationMainModal.getQuestions().getMsgSequence();
                            if (msgSequence.isEmpty()) {
                                msgSequence = "0";
                            }
                            int msgSeq = Integer.parseInt(msgSequence);
                            msgSeq += 1;
                            finalMsgSeq = msgSeq;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getConversationList("0", "0", "" + finalMsgSeq);
                                }
                            }, 2000);
                        } else {
                            loadingDots.setVisibility(View.GONE);
                        }
                    } else {
                        Alerts.show(mContext, apiConversationMainModal.getMessage());
                    }

                    chatListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                    loadingDots.setVisibility(View.GONE);
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
            case R.id.tvChips:
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
        loadingDots.setVisibility(View.VISIBLE);
        int pos = Integer.parseInt(view.getTag().toString());

        int chatPos = chatList.size() - 1;

        final String relateId = chatList.get(chatPos).getQuestionId();
        String msgSequence = chatList.get(chatPos).getMsgSequence();
        int msgSeq = Integer.parseInt(msgSequence);
        msgSeq += 1;

        final String optionId = chatList.get(chatPos).getSubResponse().get(pos).getOptionId();
        String response = chatList.get(chatPos).getSubResponse().get(pos).getMultichoiceOption();

        finalMsgSeq += 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getConversationList(relateId, optionId, finalMsgSeq + "");
            }
        }, 2000);

        NewConversationQuestionsData questionsData = new NewConversationQuestionsData();
        questionsData.setQuestionId("");
        questionsData.setBotId("");
        questionsData.setErrorMessage("");
        questionsData.setFrom("user");
        questionsData.setMsgSequence("");
        questionsData.setOptionRelateId("");
        questionsData.setRelateId("");
        questionsData.setResponse(response);
        questionsData.setType("");

        chatList.add(questionsData);
        chatListAdapter.notifyDataSetChanged();
    }

    private void addToFavApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.addToFav(strUserId, botData.getUid(), "1"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, true);
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            Alerts.show(mContext, "Added to favourite " + getString(R.string.emoji));
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
