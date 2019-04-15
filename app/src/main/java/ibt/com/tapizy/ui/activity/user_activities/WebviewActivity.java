package ibt.com.tapizy.ui.activity.user_activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import okhttp3.ResponseBody;
import retrofit2.Response;

public class WebviewActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    private CountDownTimer timer;
    private long millisInFuture = 10000; //30 seconds
    private long countDownInterval = 1000;
    private String strTitle, strCoins;
    private boolean isTimerCancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        String strUrl = getIntent().getStringExtra("url");
        strTitle = getIntent().getStringExtra("title");
        strCoins = getIntent().getStringExtra("coins");

        ((TextView) findViewById(R.id.txtTitle)).setText(strTitle);

        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.pb);
        renderWebPage(strUrl);
        countDownTimerForCoins();
    }

    protected void renderWebPage(String urlToRender) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(urlToRender);
    }

    @Override
    public void onClick(View v) {
        isTimerCancel = true;
        timer.cancel();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        isTimerCancel = true;
        timer.cancel();
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * Count down timer for coins
     * */
    private void countDownTimerForCoins() {
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.txtTimer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.txtTimer)).setText("Finished");
                if (!isTimerCancel) {
                    payCoinApi();
                }
            }
        }.start();
    }

    private void payCoinApi() {
        String userId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(new Dialog(mContext), retrofitApiClient.payCoins(
                    strCoins, userId, "2", "social_link", strTitle, "", ""), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            AppPreference.setBooleanPreference(mContext, Constant.COIN_UPDATE, true);
                            myCoinsUpdate();
                            Alerts.show(mContext, jsonObject.getString("message"));
                        } else {
                            Alerts.show(mContext, jsonObject.getString("message"));
                        }
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
        } else {
            cd.show(mContext);
        }
    }
}
