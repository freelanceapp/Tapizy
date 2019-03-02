package ibt.com.tapizy.ui.activity.community_module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.SpinnerCityListAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.city_list_modal.ApiCityListMainModal;
import ibt.com.tapizy.model.city_list_modal.CityList;
import ibt.com.tapizy.model.community_post_modal.QuestionAnswerListMainModal;
import ibt.com.tapizy.model.community_post_modal.QuestionList;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.BaseActivity;
import retrofit2.Response;

public class CommunityActivity extends BaseActivity implements View.OnClickListener {

    public static int CITY_REQUEST_CODE = 898;
    public static FragmentManager fragmentManager;

    private ApiCityListMainModal cityListMainModal;
    private List<CityList> cityList = new ArrayList<>();

    public static CommunityActivity communityActivity;
    private String strCityId = "", strCityName = "";
    private Spinner spinnerCity;
    private SpinnerCityListAdapter cityListAdapter;
    private PostAnswerFragment postAnswerFragment;
    private ArrayList<QuestionList> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        communityActivity = this;
        initFragment();
    }

    private void initFragment() {
        spinnerCity = findViewById(R.id.spinnerCity);
        findViewById(R.id.imgBack).setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        postAnswerFragment = new PostAnswerFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.my_frame_container, postAnswerFragment, Constant.PostAnswerFragment)
                .commit();
        initCitySpinner();
        cityListApi();
    }

    @Override
    public void onBackPressed() {
        Fragment PostAnswerFragment = fragmentManager.findFragmentByTag(Constant.PostAnswerFragment);
        Fragment PostQuestionFragment = fragmentManager.findFragmentByTag(Constant.PostQuestionFragment);
        if (PostAnswerFragment != null) {
            super.onBackPressed();
        } else if (PostQuestionFragment != null) {
            replaceMainFragment();
        } else {
            super.onBackPressed();
        }
    }

    private void replaceMainFragment() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.my_frame_container, new PostAnswerFragment(),
                        Constant.PostAnswerFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }

    private void initCitySpinner() {

        cityListAdapter = new SpinnerCityListAdapter(mContext, R.layout.spinner_city_name, cityList);
        spinnerCity.setAdapter(cityListAdapter);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!cityList.get(position).getCityId().equalsIgnoreCase("0")) {
                    strCityId = cityList.get(position).getCityId();
                    strCityName = cityList.get(position).getCityname();
                    AppPreference.setStringPreference(mContext, Constant.CITY_ID, strCityId);
                    selectQuestionApi(strCityId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cityListApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCityList(new Dialog(mContext), retrofitApiClient.cityList(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    cityListMainModal = (ApiCityListMainModal) result.body();
                    cityList.clear();
                    if (cityListMainModal != null) {
                        cityList.addAll(cityListMainModal.getCityList());
                        Alerts.show(mContext, cityListMainModal.getMessage());
                    }
                    Alerts.show(mContext, cityListMainModal.getMessage());

                    CityList cityData = new CityList();
                    cityData.setCityId("0");
                    cityData.setCityname("Select city");
                    cityList.add(0, cityData);
                    cityListAdapter.notifyDataSetChanged();

                    String cityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
                    if (!cityId.isEmpty()) {
                        if (cityList.size() > 0) {
                            for (int i = 0; i < cityList.size(); i++) {
                                if (cityId.equalsIgnoreCase(cityList.get(i).getCityId())) {
                                    spinnerCity.setSelection(i);
                                }
                            }
                        } else {
                            Alerts.show(mContext, "City list not found");
                        }
                    } else {
                        Alerts.show(mContext, "Select city");
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        }
    }

    public void selectQuestionApi(String strCityId) {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getQuestionListData(new Dialog(mContext), retrofitApiClient.questionListData(strCityId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    QuestionAnswerListMainModal mainModal = (QuestionAnswerListMainModal) result.body();
                    questionList.clear();
                    if (mainModal != null) {
                        if (mainModal.getQuestionList() != null) {
                            questionList.addAll(mainModal.getQuestionList());
                            postAnswerFragment.selectQuestionApi(questionList);
                            Alerts.show(mContext, mainModal.getMessage());
                        } else {
                            Alerts.show(mContext, mainModal.getMessage());
                        }
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 998) {
            String strCityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
            if (!strCityId.isEmpty()) {
                selectQuestionApi(strCityId);
            }
        }
    }
}
