package ibt.com.tapizy.ui.activity.user_activities.chatbot_activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyalbira.loadingdots.LoadingDots;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.ChatListAdapter;
import ibt.com.tapizy.click_listener_interface.CustomClickListener;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.api_bot_list.BotList;
import ibt.com.tapizy.model.conversation_modal.NewConversationMainModal;
import ibt.com.tapizy.model.conversation_modal.NewConversationQuestionsData;
import ibt.com.tapizy.model.conversation_modal.NewConversationSubResponseList;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.ui.activity.user_activities.WebviewActivity;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.EmailValidate;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChatActivity extends BaseActivity implements View.OnClickListener, CustomClickListener {

    private Firebase reference1, reference2;
    private String loginUserName = "";
    private String chatUserName = "";

    /**********************************************/
    private int finalMsgSeq;
    private BotList botData;
    private String strUserId;
    private ChatListAdapter chatListAdapter;
    private List<NewConversationQuestionsData> chatList = new ArrayList<>();
    private NewConversationMainModal apiConversationMainModal;
    private LoadingDots loadingDots;
    private EditText edtChatValue;
    private String strResponseType = "";
    private InputFilter filter;
    private RecyclerView recyclerViewChatList;
    private String msgSequence = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_chat);

        init();
    }

    private void init() {
        filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }
                return null;
            }
        };

        edtChatValue = findViewById(R.id.edtChatValue);
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

        chatListAdapter = new ChatListAdapter(mContext, chatList, this, this);

        recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatList.setAdapter(chatListAdapter);

        chatListAdapter.notifyDataSetChanged();

        getConversationList("0", "0", "0", "");
    }

    private void getConversationList(String relateId, String optionRelateId, String msgSequence, String data) {
        String botId = botData.getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getConversationList(null, retrofitApiClient.conversationApi(
                    strUserId, botId, relateId, optionRelateId, msgSequence, data), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    apiConversationMainModal = (NewConversationMainModal) result.body();
                    if (!apiConversationMainModal.getError()) {
                        if (!apiConversationMainModal.getQuestions().getQuestionId().isEmpty()) {
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
                            strResponseType = questionsData.getType();

                            findViewById(R.id.rlChat).setVisibility(View.GONE);

                            if (strResponseType.equalsIgnoreCase("email")) {
                                loadingDots.setVisibility(View.GONE);
                                findViewById(R.id.rlChat).setVisibility(View.VISIBLE);
                                edtChatValue.setHint("Enter your email");
                                edtChatValue.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            } else if (strResponseType.equalsIgnoreCase("number")) {
                                loadingDots.setVisibility(View.GONE);
                                findViewById(R.id.rlChat).setVisibility(View.VISIBLE);
                                edtChatValue.setHint("Enter your number");
                                edtChatValue.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                                edtChatValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                            } else {
                                findViewById(R.id.rlChat).setVisibility(View.GONE);
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
                                            getConversationList("0", "0", "" + finalMsgSeq, "");
                                        }
                                    }, 2000);
                                } else {
                                    loadingDots.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            Alerts.show(mContext, "Question finished");
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
            case R.id.imgSend:
                if (strResponseType.equalsIgnoreCase("email")) {
                    String strEmail = edtChatValue.getText().toString();
                    if (strEmail.isEmpty()) {
                        Alerts.show(mContext, "Please enter email id...!!!");
                    } else if (!EmailValidate.isValidEmailId(strEmail)) {
                        Alerts.show(mContext, "Please enter valid email id...!!!");
                    } else {
                        edtChatValue.setText("");
                        sendNumberEmail(strEmail);
                        msgSequence = apiConversationMainModal.getQuestions().getMsgSequence();
                        if (msgSequence.isEmpty()) {
                            msgSequence = "0";
                        }
                        int msgSeq = Integer.parseInt(msgSequence);
                        msgSeq += 1;
                        getConversationList("", "", msgSeq + "", strEmail);
                    }
                } else if (strResponseType.equalsIgnoreCase("number")) {
                    String strNumber = edtChatValue.getText().toString();
                    if (strNumber.isEmpty()) {
                        Alerts.show(mContext, "Please enter number...!!!");
                    } else if (strNumber.length() < 6) {
                        Alerts.show(mContext, "Number length must be greater than 5...!!!");
                    } else {
                        edtChatValue.setText("");
                        sendNumberEmail(strNumber);

                        msgSequence = apiConversationMainModal.getQuestions().getMsgSequence();
                        if (msgSequence.isEmpty()) {
                            msgSequence = "0";
                        }
                        int msgSeq = Integer.parseInt(msgSequence);
                        msgSeq += 1;
                        getConversationList("", "", msgSeq + "", strNumber);
                    }
                }
                break;
            case R.id.imgAddFav:
                addToFavApi();
                break;
            case R.id.txtDone:
                int position = Integer.parseInt(v.getTag().toString());
                View view = recyclerViewChatList.getChildAt(position);
                TextView txtValue = view.findViewById(R.id.txtValue);
                sendNumberEmail(txtValue.getText().toString());

                String questionId = chatList.get(position).getQuestionId();
                String optionId = chatList.get(position).getSubResponse().get(0).getOptionId();
                msgSequence = chatList.get(position).getSubResponse().get(0).getMsgSequence();
                getConversationList(questionId, optionId, msgSequence, txtValue.getText().toString());
                break;
        }
    }

    private void sendNumberEmail(String strValue) {
        NewConversationQuestionsData newQuestionsData = new NewConversationQuestionsData();
        newQuestionsData.setQuestionId("");
        newQuestionsData.setBotId("");
        newQuestionsData.setErrorMessage("");
        newQuestionsData.setFrom("user");
        newQuestionsData.setMsgSequence("");
        newQuestionsData.setOptionRelateId("");
        newQuestionsData.setRelateId("");
        newQuestionsData.setResponse(strValue);
        newQuestionsData.setType("");

        chatList.add(newQuestionsData);
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

    @Override
    public void getPosition(int parentPosition, int childPosition, final NewConversationQuestionsData questionsData,
                            final NewConversationSubResponseList subResponseData) {
        loadingDots.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String strQuestionId = questionsData.getQuestionId();
                String strOptionId = subResponseData.getOptionId();
                String strMsgSequense = subResponseData.getMsgSequence();
                if (strMsgSequense.isEmpty()) {
                    strMsgSequense = questionsData.getMsgSequence();
                    if (strMsgSequense.isEmpty()) {
                        strMsgSequense = "0";
                    }
                    int msgSeq = Integer.parseInt(strMsgSequense);
                    msgSeq += 1;
                    strMsgSequense = String.valueOf(msgSeq);
                }
                getConversationList(strQuestionId, strOptionId, strMsgSequense + "", "");
            }
        }, 2000);

        NewConversationQuestionsData newQuestionsData = new NewConversationQuestionsData();
        newQuestionsData.setQuestionId("");
        newQuestionsData.setBotId("");
        newQuestionsData.setErrorMessage("");
        newQuestionsData.setFrom("user");
        newQuestionsData.setMsgSequence("");
        newQuestionsData.setOptionRelateId("");
        newQuestionsData.setRelateId("");
        newQuestionsData.setResponse(subResponseData.getMultichoiceOption());
        newQuestionsData.setType("");

        chatList.add(newQuestionsData);
        chatListAdapter.notifyDataSetChanged();

        if (questionsData.getType().equalsIgnoreCase("link") ||
                questionsData.getType().equalsIgnoreCase("contact_us")) {
            Intent intent = new Intent(mContext, WebviewActivity.class);
            intent.putExtra("url", subResponseData.getLink());
            intent.putExtra("title", subResponseData.getTitle());
            startActivity(intent);
        }
    }
}
