package infobite.com.tapizy.ui.activity;

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
import infobite.com.tapizy.database.DatabaseHandler;
import infobite.com.tapizy.model.conversation_modal.ConversationList;
import infobite.com.tapizy.utils.BaseActivity;

public class CreateConversationActivity extends BaseActivity implements View.OnClickListener {

    private List<ConversationList> conversationLists = new ArrayList<>();
    private RecyclerView recyclerViewChatbot;
    private ConversationListAdapter conversationListAdapter;
    private String strChatbotName;

    private DatabaseHandler databaseHandler;
    private int intRelateId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chatbot);

        init();
    }

    private void init() {
        strChatbotName = getIntent().getStringExtra("name");
        databaseHandler = new DatabaseHandler(mContext, strChatbotName);
        if (databaseHandler.getContactsCount()) {
            conversationLists.clear();
            conversationLists.addAll(databaseHandler.getAllUrlList());
        }

        (findViewById(R.id.floatingCreateChatbot)).setOnClickListener(this);
        (findViewById(R.id.btnChat)).setOnClickListener(this);

        conversationListAdapter = new ConversationListAdapter(mContext, conversationLists, this, databaseHandler);
        recyclerViewChatbot = findViewById(R.id.recyclerViewChatbot);
        recyclerViewChatbot.setHasFixedSize(true);
        recyclerViewChatbot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatbot.setAdapter(conversationListAdapter);
        conversationListAdapter.notifyDataSetChanged();
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

        UrlSpinnerAdapter adapter = new UrlSpinnerAdapter(mContext, R.layout.spinner_url_layout, conversationLists);
        Spinner spinnerList = dialogChatbot.findViewById(R.id.spinnerList);
        spinnerList.setAdapter(adapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intRelateId = conversationLists.get(position).getId();
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
                    ConversationList conversationList = new ConversationList();
                    conversationList.setText(strName);
                    conversationList.setRelateId(String.valueOf(intRelateId));
                    conversationLists.add(conversationList);

                    databaseHandler.addItemCart(conversationList);
                    conversationLists.clear();
                    conversationLists.addAll(databaseHandler.getAllUrlList());
                    conversationListAdapter.notifyDataSetChanged();

                    dialogChatbot.dismiss();
                }
            }
        });

       /* Window window = dialogChatbot.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogChatbot.show();
    }
}
