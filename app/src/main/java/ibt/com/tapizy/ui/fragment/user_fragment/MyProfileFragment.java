package ibt.com.tapizy.ui.fragment.user_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.ui.activity.user_activities.CreateProfileActivity;
import ibt.com.tapizy.utils.BaseFragment;

public class MyProfileFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private String strId = "", strPhone = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView.findViewById(R.id.ic_edit_profile).setOnClickListener(this);
        setUserData();
    }

    private void setUserData() {
        strId = User.getUser().getUser().getUid();
        strPhone = User.getUser().getUser().getUContact();
        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .placeholder(R.drawable.ic_default_profile)
                .into((CircleImageView) rootView.findViewById(R.id.img_user_profile));
        ((TextView) rootView.findViewById(R.id.tv_name)).setText(User.getUser().getUser().getUName());
        ((TextView) rootView.findViewById(R.id.tv_emaiaddress)).setText(User.getUser().getUser().getUEmail());
        ((TextView) rootView.findViewById(R.id.tv_gender)).setText(User.getUser().getUser().getUGender());
        ((TextView) rootView.findViewById(R.id.tvCity)).setText(User.getUser().getUser().getCity());
        ((TextView) rootView.findViewById(R.id.tvState)).setText(User.getUser().getUser().getState());
        ((TextView) rootView.findViewById(R.id.tvCountry)).setText(User.getUser().getUser().getCountry());
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
