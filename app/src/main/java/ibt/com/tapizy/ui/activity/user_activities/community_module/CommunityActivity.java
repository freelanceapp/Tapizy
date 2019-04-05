package ibt.com.tapizy.ui.activity.user_activities.community_module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.PostQuestionAdapter;
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

    private PostQuestionAdapter postQuestionAdapter;
    private ArrayList<QuestionList> questionList = new ArrayList<>();
    private RecyclerView rvQuestionPost;

    /************************************************/
    public static FragmentManager fragmentManager;

    private ApiCityListMainModal cityListMainModal;
    private List<CityList> cityList = new ArrayList<>();

    public static CommunityActivity communityActivity;
    private String strCityId = "";
    private Spinner spinnerCity;
    private SpinnerCityListAdapter cityListAdapter;

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

        rvQuestionPost = findViewById(R.id.rvQuestionPost);
        findViewById(R.id.fabQuestion).setOnClickListener(this);

        rvQuestionPost.setHasFixedSize(true);
        rvQuestionPost.setLayoutManager(new LinearLayoutManager(mContext));

        postQuestionAdapter = new PostQuestionAdapter(questionList, mContext, retrofitApiClient);
        rvQuestionPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvQuestionPost.setItemAnimator(new DefaultItemAnimator());
        rvQuestionPost.setAdapter(postQuestionAdapter);

        String strCityId = AppPreference.getStringPreference(mContext, Constant.CITY_ID);
        if (!strCityId.isEmpty()) {
            communityActivity.selectQuestionApi(strCityId);
        }

        initCitySpinner();
        cityListApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.fabQuestion:
                Intent intent = new Intent(mContext, PostQuestionActivity.class);
                startActivityForResult(intent, 998);
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

                    if (cityList.size() > 0) {
                        findViewById(R.id.tvWait).setVisibility(View.GONE);
                        CityList cityData = new CityList();
                        cityData.setCityId("0");
                        cityData.setCityname("Select city");
                        cityList.add(0, cityData);
                    } else {
                        CityList cityData = new CityList();
                        cityData.setCityId("0");
                        cityData.setCityname("No city list");
                        cityList.add(0, cityData);
                    }

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
                            Alerts.show(mContext, mainModal.getMessage());

                            if (questionList.size() > 0) {
                                //findViewById(R.id.fabQuestion).setVisibility(View.VISIBLE);
                                findViewById(R.id.tvEmpty).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.tvEmpty)).setText("");
                            } else {
                                //findViewById(R.id.fabQuestion).setVisibility(View.GONE);
                                findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
                                ((TextView) findViewById(R.id.tvEmpty)).setText("No data");
                            }
                        } else {
                            Alerts.show(mContext, mainModal.getMessage());
                        }
                    }
                    postQuestionAdapter.notifyDataSetChanged();
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
