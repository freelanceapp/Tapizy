package ibt.com.tapizy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ibt.com.tapizy.R;
import ibt.com.tapizy.constant.Constant;
import ibt.com.tapizy.model.User;
import ibt.com.tapizy.model.favourite_bot.FavoriteBot;
import ibt.com.tapizy.retrofit_provider.RetrofitApiClient;
import ibt.com.tapizy.retrofit_provider.RetrofitService;
import ibt.com.tapizy.retrofit_provider.WebResponse;
import ibt.com.tapizy.utils.Alerts;
import ibt.com.tapizy.utils.AppPreference;
import ibt.com.tapizy.utils.blast_icon.PopField;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static ibt.com.tapizy.ui.activity.user_activities.HomeActivity.mContext;

public class FavouriteBotListAdapter extends RecyclerView.Adapter<FavouriteBotListAdapter.ViewHolder> {

    private RetrofitApiClient retrofitApiClient;
    private ArrayList<FavoriteBot> tapizyListModels;
    private Context context;
    private View.OnClickListener onClickListener;
    private Activity activity;

    public FavouriteBotListAdapter(Context context, ArrayList<FavoriteBot> tapizyListModels, View.OnClickListener onClickListener,
                                   Activity activity, RetrofitApiClient retrofitApiClient) {
        this.tapizyListModels = tapizyListModels;
        this.context = context;
        this.onClickListener = onClickListener;
        this.activity = activity;
        this.retrofitApiClient = retrofitApiClient;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llayout;
        public ImageView iv_tapizy_logo;
        public TextView tv_tapizy_title;

        public ViewHolder(View v) {
            super(v);
            tv_tapizy_title = v.findViewById(R.id.tv_tapizy_title);
            iv_tapizy_logo = v.findViewById(R.id.iv_tapizy_logo);
            llayout = v.findViewById(R.id.llayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favourite_bot, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {
        final PopField mPopField;
        mPopField = PopField.attach2Window(activity);

        Vholder.tv_tapizy_title.setText(tapizyListModels.get(position).getBotName());
        Glide.with(context)
                .load(Constant.PROFILE_IMAGE_BASE_URL + tapizyListModels.get(position).getAvtar())
                .placeholder(R.drawable.ic_default_profile)
                .into(Vholder.iv_tapizy_logo);
        Vholder.llayout.setTag(position);
        Vholder.llayout.setOnClickListener(onClickListener);

        Vholder.iv_tapizy_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopField.popView(Vholder.iv_tapizy_logo);
            }
        });
        Vholder.iv_tapizy_logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeDialog(mPopField, tapizyListModels.get(position).getBotId(), Vholder.iv_tapizy_logo, position);
                return false;
            }
        });
    }

    private void removeDialog(final PopField mPopField, final String botId, final ImageView imageView, final int position) {
        final Dialog dialogRemove = new Dialog(context);
        dialogRemove.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRemove.setContentView(R.layout.dialog_remove_fav_bot);

        dialogRemove.setCanceledOnTouchOutside(true);
        dialogRemove.setCancelable(true);
        if (dialogRemove.getWindow() != null)
            dialogRemove.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogRemove.findViewById(R.id.txtOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRemove.dismiss();
                removeFav(mPopField, botId, imageView, position);
            }
        });
        dialogRemove.findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRemove.dismiss();
            }
        });

        Window window = dialogRemove.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogRemove.show();
    }

    public void removeFav(final PopField mPopField, String strBotId, final ImageView imageView, final int position) {
        String strUserId = User.getUser().getUser().getUid();
        RetrofitService.getloginData(new Dialog(mContext), retrofitApiClient.addToFav(strUserId, strBotId, "0"), new WebResponse() {
            @Override
            public void onResponseSuccess(Response<?> result) {
                ResponseBody responseBody = (ResponseBody) result.body();
                AppPreference.setBooleanPreference(mContext, Constant.ADD_TO_FAV, true);
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    if (!jsonObject.getBoolean("error")) {
                        mPopField.popView(imageView);
                        tapizyListModels.remove(position);
                        Alerts.show(mContext, "Remove from favourite");
                    } else {
                        Alerts.show(mContext, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }

            @Override
            public void onResponseFailed(String error) {
                Alerts.show(mContext, error);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tapizyListModels.size();
    }
}
