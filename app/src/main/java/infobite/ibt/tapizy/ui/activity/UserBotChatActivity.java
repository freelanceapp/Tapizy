package infobite.ibt.tapizy.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyalbira.loadingdots.LoadingDots;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.ChatListAdapter;
import infobite.ibt.tapizy.click_listener_interface.CustomClickListener;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.api_bot_list.BotList;
import infobite.ibt.tapizy.model.chat_history.ChatHistoryData;
import infobite.ibt.tapizy.model.chat_history.ChatHistoryMainModal;
import infobite.ibt.tapizy.model.chat_history.ChatHistorySubResponse;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationMainModal;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationQuestionsData;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationSubResponseList;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.user_activities.WebviewActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseActivity;
import infobite.ibt.tapizy.utils.EmailValidate;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UserBotChatActivity extends BaseActivity implements View.OnClickListener, CustomClickListener {

    private Firebase reference1, reference2;
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
                .load(botData.getAvtar())
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

        recyclerViewChatList.post(new Runnable() {
            @Override
            public void run() {
                if (chatListAdapter.getItemCount() > 0) {
                    int pos = chatListAdapter.getItemCount() - 1;
                    recyclerViewChatList.smoothScrollToPosition(pos);
                }
            }
        });

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://tapizyuser-a87d9.firebaseio.com/" + strUserId + "_" + botData.getUid());
        reference2 = new Firebase("https://tapizyuser-a87d9.firebaseio.com/" + botData.getUid() + "_" + strUserId);

        chatHistoryApi();
        firebaseChatApi();
    }

    private void chatHistoryApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getChatHistoryData(new Dialog(mContext), retrofitApiClient.chatHistory(strUserId, botData.getUid()), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ChatHistoryMainModal chatHistoryMainModal = (ChatHistoryMainModal) result.body();
                    if (!chatHistoryMainModal.getError()) {
                        if (chatHistoryMainModal.getChat().size() > 0) {
                            if (!chatHistoryMainModal.getChat().get(0).getChatId().isEmpty()) {
                                for (ChatHistoryData historyData : chatHistoryMainModal.getChat()) {
                                    NewConversationQuestionsData questionsData = new NewConversationQuestionsData();
                                    questionsData.setQuestionId("");
                                    questionsData.setBotId("");
                                    questionsData.setErrorMessage("");
                                    questionsData.setFrom(historyData.getMessageFrom());
                                    questionsData.setMsgSequence(historyData.getMsgSequence());
                                    questionsData.setOptionRelateId(historyData.getOptionRelateId());
                                    questionsData.setRelateId(historyData.getRelateId());
                                    questionsData.setResponse(historyData.getChatMessage());
                                    questionsData.setType(historyData.getMsgType());
                                    if (historyData.getSubResponse().size() > 0) {
                                        List<NewConversationSubResponseList> subResponse = new ArrayList<>();
                                        for (ChatHistorySubResponse chatHistorySubResponse : historyData.getSubResponse()) {
                                            NewConversationSubResponseList subResponseData = new NewConversationSubResponseList();
                                            subResponseData.setLink(chatHistorySubResponse.getLink());
                                            subResponseData.setLinkType(chatHistorySubResponse.getLinkType());
                                            subResponseData.setMinimum(chatHistorySubResponse.getMinimum());
                                            subResponseData.setMaximum(chatHistorySubResponse.getMaximum());
                                            subResponseData.setMaximumDigits(chatHistorySubResponse.getMaximumDigits());
                                            subResponseData.setMinimumDigits(chatHistorySubResponse.getMinimumDigits());
                                            subResponseData.setMsgSequence(chatHistorySubResponse.getMsgSequence());
                                            subResponseData.setMultichoiceOption(chatHistorySubResponse.getMultichoiceOption());
                                            subResponseData.setOptionId(chatHistorySubResponse.getOptionId());
                                            subResponseData.setPrefix(chatHistorySubResponse.getPrefix());
                                            subResponseData.setSuffix(chatHistorySubResponse.getSuffix());
                                            subResponseData.setTitle(chatHistorySubResponse.getTitle());
                                            subResponse.add(subResponseData);
                                        }
                                        questionsData.setSubResponse(subResponse);
                                    }
                                    chatList.add(questionsData);
                                }
                                if (chatList.size() > 0) {
                                    int lastPosition = chatList.size() - 1;
                                    strResponseType = chatList.get(lastPosition).getType();
                                    if (strResponseType.isEmpty()) {
                                        strResponseType = "FIREBASE_CHAT";
                                    }
                                    if (chatList.get(lastPosition).getType().isEmpty()) {
                                        findViewById(R.id.rlChat).setVisibility(View.VISIBLE);
                                    } else {
                                        findViewById(R.id.rlChat).setVisibility(View.GONE);
                                    }
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
                                    } else if (strResponseType.equalsIgnoreCase("FIREBASE_CHAT")) {
                                        findViewById(R.id.rlChat).setVisibility(View.VISIBLE);
                                        edtChatValue.setHint("Ask anything ?");
                                        edtChatValue.setFilters(new InputFilter[]{});
                                        edtChatValue.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                    }
                                }
                                loadingDots.setVisibility(View.GONE);
                            } else {
                                getConversationList("0", "0", "0", "");
                            }
                        } else {
                            getConversationList("0", "0", "0", "");
                        }
                    } else {
                        Alerts.show(mContext, chatHistoryMainModal.getMessage());
                    }
                    chatListAdapter.notifyDataSetChanged();
                    recyclerViewChatList.post(new Runnable() {
                        @Override
                        public void run() {
                            if (chatListAdapter.getItemCount() > 0) {
                                int pos = chatListAdapter.getItemCount() - 1;
                                recyclerViewChatList.smoothScrollToPosition(pos);
                            }
                        }
                    });
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

                            Map<String, String> map = new HashMap<>();
                            map.put("question_id", questionsData.getQuestionId());
                            map.put("bot_id", questionsData.getBotId());
                            map.put("error_msg", questionsData.getErrorMessage());
                            map.put("from", questionsData.getFrom());
                            map.put("message_data", "");
                            map.put("msg_sequence", questionsData.getMsgSequence());
                            map.put("option_relate_id", questionsData.getOptionRelateId());
                            map.put("relate_id", questionsData.getRelateId());
                            map.put("response", questionsData.getResponse());
                            map.put("type", questionsData.getType());
                            reference1.push().setValue(map);
                            reference2.push().setValue(map);

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
                            strResponseType = "FIREBASE_CHAT";
                            findViewById(R.id.rlChat).setVisibility(View.VISIBLE);
                            edtChatValue.setHint("Ask anything ?");
                            edtChatValue.setFilters(new InputFilter[]{});
                            edtChatValue.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            Alerts.show(mContext, "No conversation");
                        }
                    } else {
                        Alerts.show(mContext, apiConversationMainModal.getMessage());
                    }
                    chatListAdapter.notifyDataSetChanged();
                    recyclerViewChatList.post(new Runnable() {
                        @Override
                        public void run() {
                            if (chatListAdapter.getItemCount() > 0) {
                                int pos = chatListAdapter.getItemCount() - 1;
                                recyclerViewChatList.smoothScrollToPosition(pos);
                            }
                        }
                    });
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

    private void firebaseChatApi() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String question_id = map.get("question_id").toString();
                String bot_id = map.get("bot_id").toString();
                String error_msg = map.get("error_msg").toString();
                String from = map.get("from").toString();
                String msg_sequence = map.get("msg_sequence").toString();
                String option_relate_id = map.get("option_relate_id").toString();
                String relate_id = map.get("relate_id").toString();
                String response = map.get("response").toString();
                String type = map.get("type").toString();
                String message_data = map.get("message_data").toString();

                if (type.equals("typing")) {
                    if (from.equals("user")) {
                        loadingDots.setVisibility(View.GONE);
                    } else {
                        loadingDots.setVisibility(View.VISIBLE);
                    }
                } else if (type.equals("stop_typing")) {
                    loadingDots.setVisibility(View.GONE);
                } else {
                    if (type.equalsIgnoreCase("Statement") || type.equalsIgnoreCase("email") || type.equalsIgnoreCase("number") ||
                            type.equalsIgnoreCase("contact_us") || type.equalsIgnoreCase("link") || type.equalsIgnoreCase("range") ||
                            type.equalsIgnoreCase("multichoice")) {

                    } else {
                        if (from.equals("user")) {
                            loadingDots.setVisibility(View.GONE);
                        } else {
                            loadingDots.setVisibility(View.VISIBLE);
                        }
                    }
                    if (strResponseType.equals("FIREBASE_CHAT")) {
                        if (from.equals("bot")) {
                            NewConversationQuestionsData newQuestionsData = new NewConversationQuestionsData();
                            newQuestionsData.setQuestionId("");
                            newQuestionsData.setBotId("");
                            newQuestionsData.setErrorMessage("");
                            newQuestionsData.setFrom("bot");
                            newQuestionsData.setMsgSequence("");
                            newQuestionsData.setOptionRelateId("");
                            newQuestionsData.setRelateId("");
                            newQuestionsData.setResponse(response);
                            newQuestionsData.setType("");

                            chatList.add(newQuestionsData);
                            chatListAdapter.notifyDataSetChanged();
                            recyclerViewChatList.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (chatListAdapter.getItemCount() > 0) {
                                        int pos = chatListAdapter.getItemCount() - 1;
                                        recyclerViewChatList.smoothScrollToPosition(pos);
                                    }
                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        edtChatValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Map<String, String> map = new HashMap<>();
                map.put("question_id", "");
                map.put("bot_id", "");
                map.put("error_msg", "");
                map.put("from", "user");
                map.put("message_data", "");
                map.put("msg_sequence", "");
                map.put("option_relate_id", "");
                map.put("relate_id", "");
                map.put("response", "");
                map.put("type", "typing");
                reference1.push().setValue(map);
                reference2.push().setValue(map);
            }

            @Override
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("question_id", "");
                        map.put("bot_id", "");
                        map.put("error_msg", "");
                        map.put("from", "user");
                        map.put("message_data", "");
                        map.put("msg_sequence", "");
                        map.put("option_relate_id", "");
                        map.put("relate_id", "");
                        map.put("response", "");
                        map.put("type", "stop_typing");
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                    }
                }, 1500);
            }
        });
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
                        findViewById(R.id.rlChat).setVisibility(View.GONE);
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
                        findViewById(R.id.rlChat).setVisibility(View.GONE);
                    }
                } else if (strResponseType.equalsIgnoreCase("FIREBASE_CHAT")) {
                    String strMessage = edtChatValue.getText().toString();
                    edtChatValue.setText("");
                    if (!strMessage.isEmpty()) {
                        sendNumberEmail(strMessage);
                        sendFirebaseMsgAPi(strMessage);
                    }
                }
                break;
            case R.id.imgAddFav:
                addToFavApi();
                break;
            case R.id.txtDone:
                int position = Integer.parseInt(v.getTag().toString());
                View view = recyclerViewChatList.getChildAt(position);
                if (view == null)
                    return;
                TextView txtValue = view.findViewById(R.id.txtValue);
                if (txtValue == null)
                    return;
                sendNumberEmail(txtValue.getText().toString());

                String questionId = chatList.get(position).getQuestionId();
                String optionId = chatList.get(position).getSubResponse().get(0).getOptionId();
                msgSequence = chatList.get(position).getSubResponse().get(0).getMsgSequence();
                getConversationList(questionId, optionId, msgSequence, txtValue.getText().toString());
                break;
        }
    }

    private void sendFirebaseMsgAPi(String strMsg) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(null, retrofitApiClient.updateFirebaseMsgApi(
                    botData.getUid(), strUserId, strMsg, "user"), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
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
        recyclerViewChatList.post(new Runnable() {
            @Override
            public void run() {
                if (chatListAdapter.getItemCount() > 0) {
                    int pos = chatListAdapter.getItemCount() - 1;
                    recyclerViewChatList.smoothScrollToPosition(pos);
                }
            }
        });

        Map<String, String> map = new HashMap<>();
        map.put("question_id", newQuestionsData.getQuestionId());
        map.put("bot_id", newQuestionsData.getBotId());
        map.put("error_msg", newQuestionsData.getErrorMessage());
        map.put("from", newQuestionsData.getFrom());
        map.put("message_data", strValue);
        map.put("msg_sequence", newQuestionsData.getMsgSequence());
        map.put("option_relate_id", newQuestionsData.getOptionRelateId());
        map.put("relate_id", newQuestionsData.getRelateId());
        map.put("response", newQuestionsData.getResponse());
        map.put("type", newQuestionsData.getType());
        reference1.push().setValue(map);
        reference2.push().setValue(map);
    }

    private void addToFavApi() {
        String botId = botData.getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.addToFav(strUserId, botId, "1"), new WebResponse() {
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
                getConversationList(strQuestionId, strOptionId, strMsgSequense + "", subResponseData.getMultichoiceOption());
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
        recyclerViewChatList.post(new Runnable() {
            @Override
            public void run() {
                if (chatListAdapter.getItemCount() > 0) {
                    int pos = chatListAdapter.getItemCount() - 1;
                    recyclerViewChatList.smoothScrollToPosition(pos);
                }
            }
        });


        Map<String, String> map = new HashMap<>();
        map.put("question_id", newQuestionsData.getQuestionId());
        map.put("bot_id", newQuestionsData.getBotId());
        map.put("error_msg", newQuestionsData.getErrorMessage());
        map.put("from", newQuestionsData.getFrom());
        map.put("message_data", "");
        map.put("msg_sequence", newQuestionsData.getMsgSequence());
        map.put("option_relate_id", newQuestionsData.getOptionRelateId());
        map.put("relate_id", newQuestionsData.getRelateId());
        map.put("response", newQuestionsData.getResponse());
        map.put("type", newQuestionsData.getType());
        reference1.push().setValue(map);
        reference2.push().setValue(map);

        if (questionsData.getType().equalsIgnoreCase("link") ||
                questionsData.getType().equalsIgnoreCase("contact_us")) {
            Intent intent = new Intent(mContext, WebviewActivity.class);
            intent.putExtra("url", subResponseData.getLink());
            intent.putExtra("title", subResponseData.getTitle());
            startActivity(intent);
        }
    }

    /************************************************************/
    /*
     * BackPressed
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
