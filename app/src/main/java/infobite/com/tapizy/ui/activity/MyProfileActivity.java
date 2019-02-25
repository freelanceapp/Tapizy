package infobite.com.tapizy.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Connection;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.com.tapizy.R;
import infobite.com.tapizy.constant.Constant;
import infobite.com.tapizy.loading_indicator.style.Circle;
import infobite.com.tapizy.model.User;
import infobite.com.tapizy.utils.AppPreference;
import infobite.com.tapizy.utils.BaseActivity;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView icEditProfile;
    private String strId = "", strPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        icEditProfile = findViewById(R.id.ic_edit_profile);
        icEditProfile.setOnClickListener(this);
        setUserData();
    }

    private void setUserData() {
        strId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        strPhone = User.getUser().getUser().getUContact();
        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .into((CircleImageView) findViewById(R.id.img_user_profile));
        ((TextView) findViewById(R.id.tv_username)).setText(User.getUser().getUser().getUUsername());
        ((TextView) findViewById(R.id.tv_name)).setText(User.getUser().getUser().getUName());
        ((TextView) findViewById(R.id.tv_emaiaddress)).setText(User.getUser().getUser().getUEmail());
        ((TextView) findViewById(R.id.tv_gender)).setText(User.getUser().getUser().getUGender());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_edit_profile:
                Intent intent = new Intent(mContext, CreateProfileActivity.class);
                intent.putExtra("from", "myProfile");
                intent.putExtra("uid", strId);
                intent.putExtra("phone", strPhone);
                startActivity(intent);
                break;
        }
    }
}
