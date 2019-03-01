package ibt.com.tapizy.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.community_post_modal.QuestionList;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseFragment;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static ibt.com.tapizy.ui.activity.community_module.CommunityActivity.fragmentManager;

public class PostQuestionFragment extends BaseFragment implements View.OnClickListener {

    private QuestionList questionListModal;
    private View rootView;
    private CardView cvPostQuestion;
    private Button submitQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_post_question, container, false);
        activity = getActivity();
        mContext = getActivity();
        init();
        return rootView;
    }

    private void init() {
        cvPostQuestion = rootView.findViewById(R.id.cv_post_question);
        submitQuestion = rootView.findViewById(R.id.post_submit_question);
        submitQuestion.setOnClickListener(this);
    }

    private void replaceFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.my_frame_container, new PostAnswerFragment(),
                        Constant.PostAnswerFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_submit_question:
                String strQuestion = ((EditText) rootView.findViewById(R.id.etQuestion)).getText().toString();
                if (strQuestion.isEmpty()) {
                    Alerts.show(mContext, "Please enter your question");
                } else {
                    postAnswerApi(strQuestion);
                }
                break;
        }
    }

    private void postAnswerApi(String strQuestion) {
        String strUserId = User.getUser().getUser().getUid();
        String strCityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
        RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.insertQuestionApi(strUserId,
                strCityId, strQuestion), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    Alerts.show(mContext, jsonObject + "");
                    replaceFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });
    }
}
