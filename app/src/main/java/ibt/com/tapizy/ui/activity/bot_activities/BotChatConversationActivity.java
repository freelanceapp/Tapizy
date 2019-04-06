package ibt.com.tapizy.ui.activity.bot_activities;

import android.os.Bundle;
import android.view.View;

import ibt.com.tapizy.R;
import ibt.com.tapizy.utils.BaseActivity;

public class BotChatConversationActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_chat_conversation);
        init();
    }

    private void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
