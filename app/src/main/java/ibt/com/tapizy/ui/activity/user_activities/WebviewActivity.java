package ibt.com.tapizy.ui.activity.user_activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.coins_list_modal.CoinTimeList;
import ibt.com.tapizy.model.coins_list_modal.CoinsTimeModal;
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
    private String strTitle;

    private List<CoinTimeList> timeLists = new ArrayList<>();
    private int counting = 0;
    private String updatecoins = "";
    private Handler handler = new Handler();
    private Runnable runnable;

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

        ((TextView) findViewById(R.id.txtTitle)).setText(strTitle);

        mWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.pb);
        renderWebPage(strUrl);
        coinsTimeApi();
        updateWebViewCoins();
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
        handler.removeCallbacks(runnable);
        payCoinApi(updatecoins);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        handler.removeCallbacks(runnable);
                        payCoinApi(updatecoins);
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
     * Count down timer for coins
     * */
    private void payCoinApi(String strCoins) {
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

    /*
     * Coins timer list api
     * */
    private void coinsTimeApi() {
        if (cd.isNetworkAvailable()) {
            RetrofitService.getCoinTimeData(new Dialog(mContext), retrofitApiClient.coinsTime(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    CoinsTimeModal coinsTimeModal = (CoinsTimeModal) result.body();
                    timeLists.clear();
                    if (!coinsTimeModal.getError()) {
                        timeLists.addAll(coinsTimeModal.getCoins());
                        if (timeLists.size() > 0) {
                            startTimer();
                        }
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

    private void startTimer() {
        runnable = new Runnable() {
            @Override
            public void run() {
                counting++;
                String time = String.valueOf(counting);
                if (timeLists.size() > 0) {
                    for (CoinTimeList coinTime : timeLists) {
                        if (time.equals(coinTime.getTime())) {
                            updatecoins = coinTime.getCoins();
                            //((TextView) findViewById(R.id.txtTimer)).setText(time + " coins:-" + coinTime.getCoins());
                        }
                    }
                }
                startTimer();
            }
        };
        handler.postDelayed(runnable, 60000);
    }
}
