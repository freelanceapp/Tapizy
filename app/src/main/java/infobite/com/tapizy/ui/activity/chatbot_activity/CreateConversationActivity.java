package infobite.com.tapizy.ui.activity.chatbot_activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.ConversationListAdapter;
import infobite.com.tapizy.adapter.UrlSpinnerAdapter;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.database.DatabaseHandler;
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
    private ConversationListAdapter conversationListAdapter;
    private String strChatbotName;

    private DatabaseHandler databaseHandler;
    private int intRelateId = 0;
    private String strUserId;
    private String strText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chatbot);

        init();
    }

    private void init() {
        strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        strChatbotName = getIntent().getStringExtra("name");
        databaseHandler = new DatabaseHandler(mContext, strChatbotName);
        /*if (databaseHandler.getContactsCount()) {
            conversationLists.clear();
            conversationLists.addAll(databaseHandler.getAllUrlList());
        }*/

        (findViewById(R.id.floatingCreateChatbot)).setOnClickListener(this);
        (findViewById(R.id.btnChat)).setOnClickListener(this);

        conversationListAdapter = new ConversationListAdapter(mContext, conversationLists, conversationLists, this, databaseHandler);
        recyclerViewChatbot = findViewById(R.id.recyclerViewChatbot);
        recyclerViewChatbot.setHasFixedSize(true);
        recyclerViewChatbot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatbot.setAdapter(conversationListAdapter);

        getConversationList();
    }

    private void getConversationList() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.conversationListResponse(retrofitApiClient.selectConversation(strUserId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ApiConversationMainModal apiConversationMainModal = (ApiConversationMainModal) result.body();
                    conversationLists.clear();
                    if (apiConversationMainModal != null) {
                        if (apiConversationMainModal.getConversation().size() > 0) {
                            for (int i = 0; i < apiConversationMainModal.getConversation().size(); i++) {
                                ConversationList conversationList = new ConversationList();
                                conversationList.setText(apiConversationMainModal.getConversation().get(i).getText());
                                conversationList.setRelateId(apiConversationMainModal.getConversation().get(i).getRelateId());
                                conversationList.setId(Integer.valueOf(apiConversationMainModal.getConversation().get(i).getId()));
                                conversationLists.add(conversationList);
                            }
                        }
                    }

                    conversationListAdapter.notifyDataSetChanged();
                    int size = conversationLists.size();
                    if (size > 0) {
                        int scrollAt = size - 1;
                        recyclerViewChatbot.scrollToPosition(scrollAt);
                    }
                }

                @Override
                public void onResponseFailed(String error) {

                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingCreateChatbot:
                createChatbotDialog();
                break;
            case R.id.btnChat:
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("name", strChatbotName);
                startActivity(intent);
                break;
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

        spinnerConversationLists.clear();
        spinnerConversationLists.addAll(conversationLists);

        ConversationList conversationList = new ConversationList();
        conversationList.setText("Select question");
        conversationList.setId(0);
        conversationList.setRelateId("-1");
        spinnerConversationLists.add(0, conversationList);

        UrlSpinnerAdapter adapter = new UrlSpinnerAdapter(mContext, R.layout.spinner_url_layout, spinnerConversationLists);
        Spinner spinnerList = dialogChatbot.findViewById(R.id.spinnerList);
        spinnerList.setAdapter(adapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strText = spinnerConversationLists.get(position).getText();
                intRelateId = spinnerConversationLists.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogChatbot.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = ((EditText) dialogChatbot.findViewById(R.id.edtChatbotName)).getText().toString();
                if (strName.isEmpty()) {
                    ((EditText) dialogChatbot.findViewById(R.id.edtChatbotName))
                            .setError("Enter text for conversation " + ("\ud83d\ude05"));
                } else {
                    /*ConversationList conversationList = new ConversationList();
                    conversationList.setText(strName);
                    conversationList.setRelateId(String.valueOf(intRelateId));
                    conversationLists.add(conversationList);*/

                    /*databaseHandler.addItemCart(conversationList);
                    conversationLists.clear();
                    conversationLists.addAll(databaseHandler.getAllUrlList());*/

                    if (strText.equalsIgnoreCase("Select question")) {
                        intRelateId = 0;
                    }
                    createConversationApi(String.valueOf(intRelateId), strName);
                    dialogChatbot.dismiss();
                }
            }
        });

       /* Window window = dialogChatbot.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogChatbot.show();
    }

    private void createConversationApi(String strRelateId, String strText) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.createConversationResponse(retrofitApiClient.createConversation
                    (strUserId, "1", strRelateId, strText), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();

                    getConversationList();
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
