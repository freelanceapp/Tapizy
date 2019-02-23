package infobite.com.tapizy.ui.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.RecentChatAdapter;
import infobite.com.tapizy.model.RecentChatModal;
import infobite.com.tapizy.utils.BaseActivity;

public class RecentChatActivity extends BaseActivity  implements View.OnClickListener {
    private ImageView icBackRecentChat;
    private RecyclerView rclvRecentChat;
    private RecentChatAdapter recentChatAdapter;
    private List<RecentChatModal> recentChatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        init();
    }

    private void init() {
        rclvRecentChat = findViewById(R.id.rclv_recent_chat);
        icBackRecentChat = findViewById(R.id.ic_back_recent_chat);
        icBackRecentChat.setOnClickListener(this);
        rclvRecentChat.setHasFixedSize(true);
        rclvRecentChat.setLayoutManager(new LinearLayoutManager(this));

        recentChatList.add(new RecentChatModal(R.drawable.profile_image, "Rupesh"));
        recentChatList.add(new RecentChatModal(R.drawable.profile_image, "Bhati"));
        recentChatList.add(new RecentChatModal(R.drawable.profile_image, "Name of Person"));

        recentChatAdapter = new RecentChatAdapter(mContext, recentChatList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rclvRecentChat.setLayoutManager(layoutManager);
        rclvRecentChat.setItemAnimator(new DefaultItemAnimator());
        rclvRecentChat.setAdapter(recentChatAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_back_recent_chat:
                finish();
                break;
        }
    }
}
