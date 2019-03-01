package ibt.com.tapizy.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ibt.com.tapizy.retrofit_provider.RetrofitApiClient;
import ibt.com.tapizy.retrofit_provider.RetrofitService;

public class BaseActivity extends AppCompatActivity {

    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
    }
}