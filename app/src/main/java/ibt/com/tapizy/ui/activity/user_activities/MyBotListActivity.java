package ibt.com.tapizy.ui.activity.user_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ibt.com.tapizy.R;
import ibt.com.tapizy.ui.activity.bot_activities.BotHomeActivity;
import ibt.com.tapizy.utils.BaseActivity;

public class MyBotListActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bot_list);
        init();
    }

    private void init() {
        findViewById(R.id.llChatbot).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(mContext, BotHomeActivity.class));
    }
}
