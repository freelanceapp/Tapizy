package infobite.com.tapizy.ui.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.ChatListAdapter;
import infobite.com.tapizy.adapter.QuestionListAdapter;
import infobite.com.tapizy.database.DatabaseHandler;
import infobite.com.tapizy.model.QuestionList;
import infobite.com.tapizy.model.conversation_modal.ConversationList;
import infobite.com.tapizy.utils.BaseActivity;

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerViewQuestion, recyclerViewChatList;
    private QuestionListAdapter questionListAdapter;
    private String strChatbotName;
    private DatabaseHandler databaseHandler;
    private List<QuestionList> questionArrayLists = new ArrayList<>();

    private List<ConversationList> conversationLists = new ArrayList<>();

    private ChatListAdapter chatListAdapter;
    private List<String> chatList = new ArrayList<>();

    private String strClickId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_chat);

        init();
    }

    private void init() {
        strChatbotName = getIntent().getStringExtra("name");
        databaseHandler = new DatabaseHandler(mContext, strChatbotName);
        if (databaseHandler.getContactsCount()) {
            conversationLists.clear();
            conversationLists.addAll(databaseHandler.getAllUrlList());
        }

        initialQuestion();

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
    }

    private void initialQuestion() {
        questionArrayLists.clear();
        for (int i = 0; i < conversationLists.size(); i++) {
            if (conversationLists.get(i).getRelateId().equalsIgnoreCase("0")) {
                QuestionList question = new QuestionList();
                question.setId(String.valueOf(conversationLists.get(i).getId()));
                question.setName(conversationLists.get(i).getType());
                questionArrayLists.add(question);
            }
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
        strClickId = questionArrayLists.get(pos).getId();
        String strGetQuestion = questionArrayLists.get(pos).getName();
        chatList.add(strGetQuestion);

        questionArrayLists.clear();
        for (int i = 0; i < conversationLists.size(); i++) {
            if (conversationLists.get(i).getRelateId().equalsIgnoreCase(strClickId)) {
                QuestionList question = new QuestionList();
                question.setId(String.valueOf(conversationLists.get(i).getId()));
                question.setName(conversationLists.get(i).getType());
                questionArrayLists.add(question);
            }
        }

        questionListAdapter.notifyDataSetChanged();
        chatListAdapter.notifyDataSetChanged();
    }
}
