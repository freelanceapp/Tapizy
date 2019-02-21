package infobite.com.tapizy.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.ChatbotListAdapter;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.model.database_modal.ChatbotList;
import infobite.com.tapizy.model.database_modal.ChatbotMainModal;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;

public class CreateChatbotActivity extends BaseActivity implements View.OnClickListener {

    private List<ChatbotList> chatbotLists = new ArrayList<>();
    private RecyclerView recyclerViewChatbot;
    private ChatbotListAdapter chatbotListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chatbot);

        init();
    }

    private void init() {

        (findViewById(R.id.btnChat)).setVisibility(View.GONE);
        (findViewById(R.id.floatingCreateChatbot)).setOnClickListener(this);

        chatbotListAdapter = new ChatbotListAdapter(mContext, chatbotLists, this);
        recyclerViewChatbot = findViewById(R.id.recyclerViewChatbot);
        recyclerViewChatbot.setHasFixedSize(true);
        recyclerViewChatbot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatbot.setAdapter(chatbotListAdapter);

        getChatbotList();
    }

    private void getChatbotList() {
        String strChatbotListData = AppPreference.getStringPreference(mContext, Constant.CHATBOT_LIST);
        if (strChatbotListData.isEmpty())
            return;
        Gson getGson = new Gson();
        ChatbotMainModal chatbotMainModal = getGson.fromJson(strChatbotListData, ChatbotMainModal.class);
        chatbotLists.clear();
        chatbotLists.addAll(chatbotMainModal.getChatbotList());
        chatbotListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingCreateChatbot:
                if (chatbotLists.size() > 0) {
                    Alerts.show(mContext, "You can register only one bot " + ("\ud83d\ude05"));
                } else {
                    createChatbotDialog();
                }
                break;
            case R.id.llChatbot:
                int pos = Integer.parseInt(v.getTag().toString());
                Intent intent = new Intent(mContext, CreateConversationActivity.class);
                intent.putExtra("name", pos + "" + chatbotLists.get(pos).getName());
                startActivity(intent);
                break;
        }
    }

    private void createChatbotDialog() {
        final Dialog dialogChatbot = new Dialog(mContext);
        dialogChatbot.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChatbot.setContentView(R.layout.dialog_chatbot);

        dialogChatbot.setCanceledOnTouchOutside(true);
        dialogChatbot.setCancelable(true);
       /* if (dialogChatbot.getWindow() != null)
            dialogChatbot.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        dialogChatbot.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = ((EditText) dialogChatbot.findViewById(R.id.edtChatbotName)).getText().toString();
                if (strName.isEmpty()) {
                    ((EditText) dialogChatbot.findViewById(R.id.edtChatbotName)).setError("Please enter chatbot name");
                } else {
                    ChatbotList chatbotList = new ChatbotList();
                    chatbotList.setName(strName);
                    chatbotLists.add(chatbotList);

                    ChatbotMainModal chatbotMainModal = new ChatbotMainModal();
                    chatbotMainModal.setChatbotList(chatbotLists);

                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(chatbotMainModal);
                    AppPreference.setStringPreference(mContext, Constant.CHATBOT_LIST, data);
                    getChatbotList();
                    dialogChatbot.dismiss();
                }
            }
        });

       /* Window window = dialogChatbot.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
        dialogChatbot.show();
    }
}
