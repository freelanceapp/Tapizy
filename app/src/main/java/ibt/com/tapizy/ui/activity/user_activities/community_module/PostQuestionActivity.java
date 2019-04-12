package ibt.com.tapizy.ui.activity.user_activities.community_module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import ibt.com.tapizy.utils.ConnectionDetector;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PostQuestionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_question);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        init();
    }

    private void init() {
        Button submitQuestion = findViewById(R.id.post_submit_question);
        submitQuestion.setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_submit_question:
                String strQuestion = ((EditText) findViewById(R.id.etQuestion)).getText().toString();
                if (strQuestion.isEmpty()) {
                    Alerts.show(mContext, "Please enter your question");
                } else {
                    postAnswerApi(strQuestion);
                }
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void postAnswerApi(String strQuestion) {
        String strUserId = AppPreference.getStringPreference(mContext, Constant.USER_ID);
        String strCityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
        RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.insertQuestionApi(strUserId,
                strCityId, strQuestion), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    Alerts.show(mContext, jsonObject + "");
                    Intent returnIntent = new Intent();
                    setResult(998, returnIntent);
                    finish();

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
