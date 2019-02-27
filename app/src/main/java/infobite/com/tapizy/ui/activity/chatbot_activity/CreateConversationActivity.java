package infobite.com.tapizy.ui.activity.chatbot_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.SubQuestionListAdapter;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import infobite.com.tapizy.model.conversation_modal.ConversationList;
import infobite.com.tapizy.retrofit_provider.RetrofitService;
import infobite.com.tapizy.retrofit_provider.WebResponse;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateConversationActivity extends BaseActivity implements View.OnClickListener {

    private List<ConversationList> conversationLists = new ArrayList<>();
    private List<ConversationList> spinnerConversationLists = new ArrayList<>();
    private RecyclerView recyclerViewChatbot;
    private String strChatbotName;

    private int intRelateId = 0;
    private String strUserId;
    private String strText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conversation);

        init();
    }

    private void init() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        //strChatbotName = getIntent().getStringExtra("name");

        (findViewById(R.id.floatingCreateChatbot)).setOnClickListener(this);

        recyclerViewChatbot = findViewById(R.id.recyclerViewChatbot);
        recyclerViewChatbot.setHasFixedSize(true);
        recyclerViewChatbot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        //recyclerViewChatbot.setAdapter(conversationListAdapter);

        findViewById(R.id.btnWelcomeText).setOnClickListener(this);

        if (AppPreference.getFirstBooleanPref(mContext, Constant.FIRST_CONVERSATION)) {
            findViewById(R.id.llWelcome).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.llWelcome).setVisibility(View.GONE);
        }

        //getConversationList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWelcomeText:
                String strWelcomeText = ((EditText) findViewById(R.id.edtWelcomeText)).getText().toString();
                if (strWelcomeText.isEmpty()) {
                    Alerts.show(mContext, "Please enter welcome text");
                } else {
                    AppPreference.setFirstBooleanPref(mContext, Constant.FIRST_CONVERSATION, false);
                    findViewById(R.id.llWelcome).setVisibility(View.GONE);
                    createConversationApi("", strWelcomeText, "welcome", "");
                }
                break;
            case R.id.floatingCreateChatbot:
                if (AppPreference.getFirstBooleanPref(mContext, Constant.FIRST_CONVERSATION)) {
                    Alerts.show(mContext, "Please add first welcome message");
                } else {
                    createChatbotDialog();
                }
                break;
        }
    }

    private void createConversationApi(String strRelateId, String strText, String strType, String strResponseRelateId) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.createConversationResponse(retrofitApiClient.createConversation
                    (strUserId, "1", strRelateId, strText, strType, strResponseRelateId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        Alerts.show(mContext, jsonObject + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //getConversationList();
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

    private void createChatbotDialog() {
        final Dialog dialogChatbot = new Dialog(mContext);
        dialogChatbot.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChatbot.setContentView(R.layout.dialog_conversation);

        dialogChatbot.setCanceledOnTouchOutside(true);
        dialogChatbot.setCancelable(true);
       /* if (dialogChatbot.getWindow() != null)
            dialogChatbot.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        final List<String> subQuestionList = new ArrayList<>();
        dialogChatbot.findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);

        final SubQuestionListAdapter subQuestionListAdapter = new SubQuestionListAdapter(mContext, subQuestionList);
        final RecyclerView recyclerViewSubQuestion = dialogChatbot.findViewById(R.id.recyclerViewSubQuestion);
        recyclerViewSubQuestion.setHasFixedSize(true);
        recyclerViewSubQuestion.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewSubQuestion.setAdapter(subQuestionListAdapter);

        dialogChatbot.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChatbot.dismiss();
            }
        });

        dialogChatbot.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSubQuestion = ((EditText) dialogChatbot.findViewById(R.id.edtSubQuestion)).getText().toString();
                if (strSubQuestion.isEmpty()) {
                    Alerts.show(mContext, "Please enter sub text");
                } else {
                    subQuestionList.add(strSubQuestion);
                    subQuestionListAdapter.notifyDataSetChanged();
                    ((EditText) dialogChatbot.findViewById(R.id.edtSubQuestion)).setText("");
                    dialogChatbot.findViewById(R.id.tvEmpty).setVisibility(View.GONE);

                    if (subQuestionList.size() > 0) {
                        int size = subQuestionList.size();
                        int scrollTo = size - 1;
                        recyclerViewSubQuestion.scrollToPosition(scrollTo);
                    }
                }
            }
        });

       /* Window window = dialogChatbot.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogChatbot.show();
    }

    private void getConversationList() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.conversationListResponse(retrofitApiClient.selectConversation(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ApiConversationMainModal apiConversationMainModal = (ApiConversationMainModal) result.body();
                    conversationLists.clear();
                }

                @Override
                public void onResponseFailed(String error) {

                }
            });
        } else {
            cd.show(mContext);
        }
    }
}
