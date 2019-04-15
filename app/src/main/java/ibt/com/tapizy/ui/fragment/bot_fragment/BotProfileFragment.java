package ibt.com.tapizy.ui.fragment.bot_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseFragment;

import static ibt.com.tapizy.ui.activity.bot_activities.BotProfileActivity.fragmentUtilsBot;

public class BotProfileFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.bot_fragment_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        rootView.findViewById(R.id.imgEdit).setOnClickListener(this);

        ((TextView) rootView.findViewById(R.id.edtBotName)).setText(User.getBotDetail().getBotName());
        ((TextView) rootView.findViewById(R.id.txtSelectedColor)).setText(User.getBotDetail().getBotColor());
        ((TextView) rootView.findViewById(R.id.txtMainCategory)).setText(User.getBotDetail().getBotType());
        ((TextView) rootView.findViewById(R.id.txtSubCategory)).setText(User.getBotDetail().getBotSubType());
        ((EditText) rootView.findViewById(R.id.edtWeblink)).setText(User.getBotDetail().getWebisite());
        ((EditText) rootView.findViewById(R.id.edtBotDescription)).setText(User.getBotDetail().getDescription());

        String botColor = User.getBotDetail().getBotColor();
        if (botColor.equalsIgnoreCase("Blue")) {
            int[] visible = {R.id.rlBlue, R.id.rlGreen, R.id.rlPurple, R.id.rlMaroon, R.id.rlOlive, R.id.rlTeal};
            colorVisibility(visible, R.color.bot_blue);
        } else if (botColor.equalsIgnoreCase("Green")) {
            int[] visible = {R.id.rlGreen, R.id.rlPurple, R.id.rlBlue, R.id.rlMaroon, R.id.rlOlive, R.id.rlTeal};
            colorVisibility(visible, R.color.bot_green);
        } else if (botColor.equalsIgnoreCase("Maroon")) {
            int[] visible = {R.id.rlMaroon, R.id.rlGreen, R.id.rlBlue, R.id.rlPurple, R.id.rlOlive, R.id.rlTeal};
            colorVisibility(visible, R.color.bot_maroon);
        } else if (botColor.equalsIgnoreCase("Olive")) {
            int[] visible = {R.id.rlOlive, R.id.rlGreen, R.id.rlBlue, R.id.rlMaroon, R.id.rlPurple, R.id.rlTeal};
            colorVisibility(visible, R.color.bot_olive);
        } else if (botColor.equalsIgnoreCase("Purple")) {
            int[] visible = {R.id.rlPurple, R.id.rlGreen, R.id.rlBlue, R.id.rlMaroon, R.id.rlOlive, R.id.rlTeal};
            colorVisibility(visible, R.color.bot_purple);
        } else if (botColor.equalsIgnoreCase("Teal")) {
            int[] visible = {R.id.rlTeal, R.id.rlGreen, R.id.rlBlue, R.id.rlMaroon, R.id.rlOlive, R.id.rlPurple};
            colorVisibility(visible, R.color.bot_teal);
        }
        Glide.with(mContext)
                .load(Constant.BOT_PROFILE_IMAGE + User.getBotDetail().getBotAvtar())
                .placeholder(R.drawable.img_chatbot)
                .into((ImageView) rootView.findViewById(R.id.imgBotProfile));
    }

    private void colorVisibility(int[] visible, int color) {
        rootView.findViewById(R.id.viewA).setBackgroundColor(getResources().getColor(color));
        for (int i = 0; i < visible.length; i++) {
            if (i == 0) {
                rootView.findViewById(visible[i]).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(visible[i]).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgEdit:
                fragmentUtilsBot.replaceFragment(new BotUpdateProfileFragment(), Constant.BotUpdateProfileFragment, R.id.frameLayout);
                break;
        }
    }
}
