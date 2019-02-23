package infobite.com.tapizy.ui.activity.community_module;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.model.database_modal.ChatbotList;
import infobite.com.tapizy.utils.BaseActivity;

public class CommunityActivity extends BaseActivity implements View.OnClickListener {

    private List<ChatbotList> chatbotLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        init();
    }

    private void init() {

    }

    @Override
    public void onClick(View v) {

    }
}
