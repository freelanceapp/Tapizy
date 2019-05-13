package infobite.ibt.tapizy.ui.fragment.user_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.FaqListAdapter;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.faq_data.FaqList;
import infobite.ibt.tapizy.model.faq_data.FaqMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.BaseFragment;
import infobite.ibt.tapizy.utils.ConnectionDetector;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AvailabilityFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private List<FaqList> faqModels = new ArrayList<>();
    private Button btnSubmit;
    private FaqListAdapter faqListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_availability, container, false);
        activity = getActivity();
        mContext = getActivity();
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        RecyclerView recyclerViewFaq = rootView.findViewById(R.id.recyclerViewFaq);
        recyclerViewFaq.setHasFixedSize(true);
        recyclerViewFaq.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        faqListAdapter = new FaqListAdapter(mContext, faqModels, this);
        recyclerViewFaq.setAdapter(faqListAdapter);

        faqListApi();
    }

    private void faqListApi() {
        String userId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getFaqList(null, retrofitApiClient.faqList(userId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    FaqMainModal faqMainModal = (FaqMainModal) result.body();
                    faqModels.clear();
                    if (!faqMainModal.getError()) {
                        faqModels.addAll(faqMainModal.getQuery());
                    } else {
                        Alerts.show(mContext, faqMainModal.getMessage());
                    }
                    faqListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                askQuestionApi();
                break;
        }
    }

    private void askQuestionApi() {
        String userId = User.getUser().getUser().getUid();
        String strQuestion = ((EditText) rootView.findViewById(R.id.edtQuestion)).getText().toString();
        String strComment = ((EditText) rootView.findViewById(R.id.edtComment)).getText().toString();

        if (strQuestion.isEmpty()) {
            Alerts.show(mContext, "Enter some text...!!!");
        } else if (strComment.isEmpty()) {
            Alerts.show(mContext, "Enter some text...!!!");
        } else {
            if (cd.isNetworkAvailable()) {
                RetrofitService.getResponseData(new Dialog(mContext), retrofitApiClient.support(
                        userId, strQuestion, "", strComment), new WebResponse() {
                    @Override
                    public void onResponseSuccess(Response<?> result) {
                        ResponseBody responseBody = (ResponseBody) result.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (jsonObject.getBoolean("error")) {
                                Alerts.show(mContext, jsonObject.getString("message"));
                            } else {
                                Alerts.show(mContext, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        faqListApi();
                    }

                    @Override
                    public void onResponseFailed(String error) {

                    }
                });
            } else {
                cd.show(mContext);
            }
        }
    }
}
