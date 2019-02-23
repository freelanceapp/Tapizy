package infobite.com.tapizy.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.jsoup.Connection;

import infobite.com.tapizy.R;
import infobite.com.tapizy.utils.BaseActivity;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView icEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        icEditProfile = findViewById(R.id.ic_edit_profile);
        icEditProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_edit_profile:
                startActivity(new Intent(mContext,CreateProfileActivity.class));
                break;
        }
    }
}
