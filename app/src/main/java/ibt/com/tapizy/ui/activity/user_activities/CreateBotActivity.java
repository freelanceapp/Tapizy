package ibt.com.tapizy.ui.activity.user_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.adapter.SpinnerBotCategoryAdapter;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.SubCatList;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.BaseActivity;

public class CreateBotActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bot);
        init();
    }

    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.rlBlue).setOnClickListener(this);
        findViewById(R.id.rlGreen).setOnClickListener(this);
        findViewById(R.id.rlMaroon).setOnClickListener(this);
        findViewById(R.id.rlOlive).setOnClickListener(this);
        findViewById(R.id.rlPurple).setOnClickListener(this);
        findViewById(R.id.rlTeal).setOnClickListener(this);

        botCategorySpinner();
        botSubCategorySpinner();
    }

    /*
     * Category and sub category spinner
     * */
    private void botCategorySpinner() {
        final List<SubCatList> items = new ArrayList<>();
        for (int i = 0; i < Constant.CATEGORY_LIST.length; i++) {
            String strName = Constant.CATEGORY_LIST[i];
            SubCatList subCatList = new SubCatList(String.valueOf(i), strName);
            items.add(subCatList);
        }

        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        Spinner spinnerList = findViewById(R.id.spinnerCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void botSubCategorySpinner() {
        final List<SubCatList> items = new ArrayList<>();
        for (int i = 0; i < Constant.CATEGORY_LIST.length; i++) {
            String strName = Constant.CATEGORY_LIST[i];
            SubCatList subCatList = new SubCatList(String.valueOf(i), strName);
            items.add(subCatList);
        }

        SpinnerBotCategoryAdapter botCategoryAdapter = new SpinnerBotCategoryAdapter(mContext, R.layout.spinner_category_layout, items);
        Spinner spinnerList = findViewById(R.id.spinnerSubCategory);
        spinnerList.setAdapter(botCategoryAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.rlBlue:
                Alerts.show(mContext, "Blue");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Blue");
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Blue).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Green).setVisibility(View.GONE);
                findViewById(R.id.rlC_Maroon).setVisibility(View.GONE);
                findViewById(R.id.rlC_Olive).setVisibility(View.GONE);
                findViewById(R.id.rlC_Purple).setVisibility(View.GONE);
                findViewById(R.id.rlC_Teal).setVisibility(View.GONE);
                break;
            case R.id.rlGreen:
                Alerts.show(mContext, "Green");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Green");
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Green).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Blue).setVisibility(View.GONE);
                findViewById(R.id.rlC_Maroon).setVisibility(View.GONE);
                findViewById(R.id.rlC_Olive).setVisibility(View.GONE);
                findViewById(R.id.rlC_Purple).setVisibility(View.GONE);
                findViewById(R.id.rlC_Teal).setVisibility(View.GONE);
                break;
            case R.id.rlMaroon:
                Alerts.show(mContext, "Maroon");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Maroon");
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Maroon).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Green).setVisibility(View.GONE);
                findViewById(R.id.rlC_Blue).setVisibility(View.GONE);
                findViewById(R.id.rlC_Olive).setVisibility(View.GONE);
                findViewById(R.id.rlC_Purple).setVisibility(View.GONE);
                findViewById(R.id.rlC_Teal).setVisibility(View.GONE);
                break;
            case R.id.rlOlive:
                Alerts.show(mContext, "Olive");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Olive");
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Olive).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Green).setVisibility(View.GONE);
                findViewById(R.id.rlC_Blue).setVisibility(View.GONE);
                findViewById(R.id.rlC_Maroon).setVisibility(View.GONE);
                findViewById(R.id.rlC_Purple).setVisibility(View.GONE);
                findViewById(R.id.rlC_Teal).setVisibility(View.GONE);
                break;
            case R.id.rlPurple:
                Alerts.show(mContext, "Purple");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Purple");
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Purple).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Green).setVisibility(View.GONE);
                findViewById(R.id.rlC_Blue).setVisibility(View.GONE);
                findViewById(R.id.rlC_Maroon).setVisibility(View.GONE);
                findViewById(R.id.rlC_Olive).setVisibility(View.GONE);
                findViewById(R.id.rlC_Teal).setVisibility(View.GONE);
                break;
            case R.id.rlTeal:
                Alerts.show(mContext, "Teal");
                ((TextView) findViewById(R.id.txtSelectedColor)).setText("Teal");
                ((TextView) findViewById(R.id.txtTeal)).setTextColor(getResources().getColor(R.color.white));
                ((TextView) findViewById(R.id.txtBlue)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtGreen)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtMaroon)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtOlive)).setTextColor(getResources().getColor(R.color.gray_h));
                ((TextView) findViewById(R.id.txtPurple)).setTextColor(getResources().getColor(R.color.gray_h));

                findViewById(R.id.rlC_Teal).setVisibility(View.VISIBLE);
                findViewById(R.id.rlC_Green).setVisibility(View.GONE);
                findViewById(R.id.rlC_Blue).setVisibility(View.GONE);
                findViewById(R.id.rlC_Maroon).setVisibility(View.GONE);
                findViewById(R.id.rlC_Olive).setVisibility(View.GONE);
                findViewById(R.id.rlC_Purple).setVisibility(View.GONE);
                break;
        }
    }
}
