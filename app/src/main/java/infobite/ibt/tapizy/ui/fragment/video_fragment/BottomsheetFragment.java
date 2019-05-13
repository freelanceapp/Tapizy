package infobite.ibt.tapizy.ui.fragment.video_fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hdodenhof.circleimageview.CircleImageView;
import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.login_data_modal.UserDataMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitApiClient;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity;
import infobite.ibt.tapizy.ui.activity.user_activities.LoginActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import retrofit2.Response;

public class BottomsheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private RetrofitApiClient retrofitApiClient;
    private Context mContext;
    private View v;
    private CircleImageView imgA, imgB;
    private String strId_A, strName_A, strAvatar_A;
    private String strId_B, strName_B, strAvatar_B;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        mContext = getActivity();
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
        return v;
    }

    private void init() {
        imgA = v.findViewById(R.id.imgA);
        imgB = v.findViewById(R.id.imgB);
        v.findViewById(R.id.llUserA).setOnClickListener(this);
        v.findViewById(R.id.llUserB).setOnClickListener(this);
        v.findViewById(R.id.llAddAccount).setOnClickListener(this);

        strId_A = User.getUser().getUser().getUid();
        strName_A = User.getUser().getUser().getUName();
        strAvatar_A = User.getUser().getUser().getUProfile();

        setValue();
    }

    private void setValue() {
        ((TextView) v.findViewById(R.id.tvA)).setText(User.getUser().getUser().getUName());
        Glide.with(mContext)
                .load(Constant.PROFILE_IMAGE_BASE_URL + User.getUser().getUser().getUProfile())
                .into(imgA);

        if (AppPreference.getMultiBoolean(mContext, Constant.USER_B)) {
            v.findViewById(R.id.llAddAccount).setVisibility(View.GONE);
            v.findViewById(R.id.llUserB).setVisibility(View.VISIBLE);
            strId_B = AppPreference.getMultiStringPreference(mContext, Constant.USER_ID_B);
            strName_B = AppPreference.getMultiStringPreference(mContext, Constant.USER_NAME_B);
            strAvatar_B = AppPreference.getMultiStringPreference(mContext, Constant.USER_AVATAR_B);
            ((TextView) v.findViewById(R.id.tvB)).setText(strName_B);
            Glide.with(mContext)
                    .load(Constant.PROFILE_IMAGE_BASE_URL + strAvatar_B)
                    .into(imgB);
        } else {
            v.findViewById(R.id.llUserB).setVisibility(View.GONE);
            v.findViewById(R.id.llAddAccount).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddAccount:
                AppPreference.setBooleanPreference(mContext, Constant.MULTI_ACCOUNT, true);
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.llUserA:
                break;
            case R.id.llUserB:
                selectAccountB();
                break;
        }
    }

    private void selectAccountB() {
        AppPreference.setMultiStringPreference(mContext, Constant.USER_ID_B, strId_A);
        AppPreference.setMultiStringPreference(mContext, Constant.USER_NAME_B, strName_A);
        AppPreference.setMultiStringPreference(mContext, Constant.USER_AVATAR_B, strAvatar_A);
        getUserDetailApi();
    }

    /*
     * Profile detail api
     * */
    private void getUserDetailApi() {
        RetrofitService.getOtpData(new Dialog(mContext), retrofitApiClient.getUserDetail(strId_B), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                UserDataMainModal mainModal = (UserDataMainModal) result.body();
                if (mainModal != null) {
                    AppPreference.setBooleanPreference(mContext, Constant.IS_LOGIN, true);
                    AppPreference.setBooleanPreference(mContext, "update", true);
                    Gson gson = new GsonBuilder().setLenient().create();
                    String data = gson.toJson(mainModal);
                    AppPreference.setStringPreference(mContext, Constant.USER_DATA, data);
                    User.setUser(mainModal);

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    dismiss();
                    getActivity().finish();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }
}
