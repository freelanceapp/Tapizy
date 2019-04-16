package ibt.com.tapizy.ui.fragment.user_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.SpinnerFaqAdapter;
import ibt.com.tapizy.model.spinner_faq.FaqModel;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseFragment;

public class AvailabilityFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private List<FaqModel> faqModels = new ArrayList<>();
    private EditText edtQuestion;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_availability, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        edtQuestion = rootView.findViewById(R.id.edtQuestion);
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        faqModels.add(new FaqModel("", ""));
        faqModels.add(new FaqModel("How can I scan QR code?", getString(R.string.qr_help)));
        faqModels.add(new FaqModel("Other!", ""));

        Spinner spinnerFaq = rootView.findViewById(R.id.spinnerFaq);
        SpinnerFaqAdapter faqAdapter = new SpinnerFaqAdapter(mContext, R.layout.spinner_custom_layout, faqModels);
        spinnerFaq.setAdapter(faqAdapter);
        spinnerFaq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtQuestion.setText(faqModels.get(position).getAnswer());
                edtQuestion.setHint("");
                if (faqModels.get(position).getQuestion().equalsIgnoreCase("Other!")) {
                    edtQuestion.setFocusableInTouchMode(true);
                    edtQuestion.requestFocus();
                    edtQuestion.setHintTextColor(mContext.getResources().getColor(R.color.gray_d));
                    btnSubmit.setVisibility(View.VISIBLE);
                    edtQuestion.setHint("Please enter your question!!!");
                } else {
                    btnSubmit.setVisibility(View.GONE);
                    edtQuestion.setFocusable(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                Alerts.show(mContext, "Under development...!!!");
                break;
        }
    }
}
