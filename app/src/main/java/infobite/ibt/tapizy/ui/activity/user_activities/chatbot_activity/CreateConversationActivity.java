package infobite.ibt.tapizy.ui.activity.user_activities.chatbot_activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.ConversationListAdapter;
import infobite.ibt.tapizy.adapter.SubQuestionListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.api_conversation_modal.ApiConversationList;
import infobite.ibt.tapizy.model.api_conversation_modal.ApiConversationMainModal;
import infobite.ibt.tapizy.model.api_conversation_modal.ApiSubResponseList;
import infobite.ibt.tapizy.model.local_sub_question_modal.SubQuesList;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.AppProgressDialog;
import infobite.ibt.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class CreateConversationActivity extends BaseActivity implements View.OnClickListener {

    public static CreateConversationActivity createConversationActivity;
    private ConversationListAdapter conversationListAdapter;
    private List<ApiConversationList> conversationLists = new ArrayList<>();
    private RecyclerView recyclerViewChatbot;
    private String strChatbotName;

    private int intRelateId = 0;
    private String strUserId;
    private String strText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conversation);
        createConversationActivity = this;
        init();
    }

    private void init() {
        strUserId = User.getUser().getUser().getUid();

        (findViewById(R.id.floatingCreateChatbot)).setOnClickListener(this);
        (findViewById(R.id.imgBack)).setOnClickListener(this);

        conversationListAdapter = new ConversationListAdapter(mContext, conversationLists, this);
        recyclerViewChatbot = findViewById(R.id.recyclerViewChatbot);
        recyclerViewChatbot.setHasFixedSize(true);
        recyclerViewChatbot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatbot.setAdapter(conversationListAdapter);

        findViewById(R.id.btnWelcomeText).setOnClickListener(this);

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
                    createConversationApi("", strWelcomeText, "welcome", "", "");
                }
                break;
            case R.id.floatingCreateChatbot:
                if (conversationLists.size() > 0) {
                    createChatbotDialog("", "", "");
                } else {
                    if (AppPreference.getFirstBooleanPref(mContext, Constant.FIRST_CONVERSATION)) {
                        Alerts.show(mContext, "Please add first welcome message");
                    } else {
                        createChatbotDialog("", "", "");
                    }
                }
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }

    public void createConversationApi(String strRelateId, String strText, String strType, String strResponseRelateId,
                                      String strSubQuestion) {
        final Dialog dialog = new Dialog(mContext);
        AppProgressDialog.show(dialog);
        if (cd.isNetworkAvailable()) {
            RetrofitService.createConversationResponse(retrofitApiClient.createConversation
                    (strUserId, "1", strRelateId, strText, strType, strResponseRelateId, strSubQuestion), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    AppProgressDialog.hide(dialog);
                    ApiConversationMainModal apiConversationMainModal = (ApiConversationMainModal) result.body();
                    conversationLists.clear();
                    if (apiConversationMainModal.getConversation() != null) {
                        conversationLists.addAll(apiConversationMainModal.getConversation());
                    }
                    conversationListAdapter.notifyDataSetChanged();

                    int size = conversationLists.size();
                    if (size > 0) {
                        int scrollTo = size - 1;
                        recyclerViewChatbot.scrollToPosition(scrollTo);
                    }
                    //getConversationList();
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

    public void createChatbotDialog(final String strRelateId, String strM_Question, final String strResponseRelateId) {
        final Dialog dialogChatbot = new Dialog(mContext);
        dialogChatbot.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChatbot.setContentView(R.layout.dialog_conversation);

        dialogChatbot.setCanceledOnTouchOutside(true);
        dialogChatbot.setCancelable(true);
       /* if (dialogChatbot.getWindow() != null)
            dialogChatbot.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        final List<ApiSubResponseList> subQuestionList = new ArrayList<>();
        dialogChatbot.findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);

        if (!strM_Question.isEmpty()) {
            ((TextView) dialogChatbot.findViewById(R.id.tvMainQ)).setText(strM_Question);
        }

        final SubQuestionListAdapter subQuestionListAdapter = new SubQuestionListAdapter(mContext, subQuestionList, null);
        final RecyclerView recyclerViewSubQuestion = dialogChatbot.findViewById(R.id.recyclerViewSubQuestion);
        recyclerViewSubQuestion.setHasFixedSize(true);
        recyclerViewSubQuestion.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewSubQuestion.setAdapter(subQuestionListAdapter);

        dialogChatbot.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMainQuestion = ((EditText) dialogChatbot.findViewById(R.id.edtQuestion)).getText().toString();

                List<SubQuesList> subQuesLists = new ArrayList<>();
                for (int i = 0; i < subQuestionList.size(); i++) {
                    SubQuesList ques = new SubQuesList();
                    ques.setSubText(subQuestionList.get(i).getResponseText());
                    subQuesLists.add(ques);
                }

                Gson gson = new GsonBuilder().setLenient().create();
                String data = gson.toJson(subQuesLists);

                Log.e("sub_list - ", data);
                if (strMainQuestion.isEmpty()) {
                    Alerts.show(mContext, "Please enter initial question");
                } else {
                    if (subQuestionList.size() > 0) {
                        createConversationApi(strRelateId, strMainQuestion, "multichoice", strResponseRelateId, data);
                    } else {
                        createConversationApi(strRelateId, strMainQuestion, "statement", strResponseRelateId, data);
                    }
                }
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
                    ApiSubResponseList subQuesList = new ApiSubResponseList();
                    subQuesList.setResponseText(strSubQuestion);
                    subQuestionList.add(subQuesList);
                    subQuestionListAdapter.notifyDataSetChanged();
                    ((EditText) dialogChatbot.findViewById(R.id.edtSubQuestion)).setText("");
                    ((EditText) dialogChatbot.findViewById(R.id.edtSubQuestion)).setHint("Enter another question");
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

}
