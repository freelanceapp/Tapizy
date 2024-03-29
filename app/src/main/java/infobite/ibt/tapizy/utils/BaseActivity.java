package infobite.ibt.tapizy.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.retrofit_provider.RetrofitApiClient;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static infobite.ibt.tapizy.ui.activity.user_activities.HomeActivity.sNavigationDrawer;

public class BaseActivity extends AppCompatActivity {

    public RetrofitApiClient retrofitApiClient;
    public ConnectionDetector cd;
    public Context mContext;
    public boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        mContext = this;
        cd = new ConnectionDetector(mContext);
        retrofitApiClient = RetrofitService.getRetrofit();
    }

    public void myCoinsApi() {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        String userId;
        if (userType.equalsIgnoreCase("user")) {
            userId = User.getUser().getUser().getUid();
        } else {
            userId = User.getBotDetail().getId();
        }

        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(null, retrofitApiClient.myCoinsCount(
                    userType, userId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            User.setCoins(jsonObject.getString("mycoin"));
                            if (sNavigationDrawer != null) {
                                sNavigationDrawer.setHeaderData();
                            }
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

    public void myCoinsUpdate() {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        String userId;
        if (userType.equalsIgnoreCase("user")) {
            userId = User.getUser().getUser().getUid();
        } else {
            userId = User.getBotDetail().getId();
        }

        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(null, retrofitApiClient.myCoinsCount(
                    userType, userId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            User.setCoins(jsonObject.getString("mycoin"));
                            setCoins();
                            finish();
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

    public void updateWebViewCoins() {
        setCoins();
    }

    private void setCoins() {
        Glide.with(mContext)
                .asGif()
                .load(Constant.COIN_GIF)
                .useAnimationPool(true)
                .placeholder(R.drawable.coin_gif)
                .into(((ImageView) findViewById(R.id.imgToolbarCoinGif)));

        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (userType.equalsIgnoreCase("user")) {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        } else {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        }
    }

    public void myCoinsApiTrending(final String title) {
        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        String userId;
        if (userType.equalsIgnoreCase("user")) {
            userId = User.getUser().getUser().getUid();
        } else {
            userId = User.getBotDetail().getId();
        }

        if (cd.isNetworkAvailable()) {
            RetrofitService.getResponseData(null, retrofitApiClient.myCoinsCount(
                    userType, userId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    ResponseBody responseBody = (ResponseBody) result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        if (!jsonObject.getBoolean("error")) {
                            User.setCoins(jsonObject.getString("mycoin"));
                            setTitle(title);
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

    private void setTitle(String title) {
        ((TextView) findViewById(R.id.txtTitle)).setText(title);
        Glide.with(mContext)
                .asGif()
                .load(Constant.COIN_GIF)
                .useAnimationPool(true)
                .placeholder(R.drawable.coin_gif)
                .into(((ImageView) findViewById(R.id.imgToolbarCoinGif)));

        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (userType.equalsIgnoreCase("user")) {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        } else {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) findViewById(R.id.txtCoinsCount)).setText(coins);
        }

    }

}