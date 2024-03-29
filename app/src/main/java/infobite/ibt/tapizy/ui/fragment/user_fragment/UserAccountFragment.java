package infobite.ibt.tapizy.ui.fragment.user_fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.adapter.TransactionListAdapter;
import infobite.ibt.tapizy.constant.Constant;
import infobite.ibt.tapizy.model.User;
import infobite.ibt.tapizy.model.transaction.TransactionCoinList;
import infobite.ibt.tapizy.model.transaction.TransactionMainModal;
import infobite.ibt.tapizy.retrofit_provider.RetrofitService;
import infobite.ibt.tapizy.retrofit_provider.WebResponse;
import infobite.ibt.tapizy.ui.activity.user_activities.QrCodeActivity;
import infobite.ibt.tapizy.utils.Alerts;
import infobite.ibt.tapizy.utils.AppPreference;
import infobite.ibt.tapizy.utils.BaseFragment;
import infobite.ibt.tapizy.utils.ConnectionDetector;
import retrofit2.Response;

public class UserAccountFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private final int PERMISSION_REQUEST_CODE = 456;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String GOT_RESULT = "scan_result";
    private final String ERROR_DECODING_IMAGE = "error_decoding_image";

    private TransactionListAdapter transactionListAdapter;
    private List<TransactionCoinList> transactionCoinLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_user_account, container, false);
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
        rootView.findViewById(R.id.imgQrScan).setOnClickListener(this);

        Glide.with(mContext)
                .asGif()
                .load(Constant.COIN_GIF)
                .useAnimationPool(true)
                .placeholder(R.drawable.coin_gif)
                .into(((ImageView) rootView.findViewById(R.id.imgToolbarCoinGif)));

        String userType = AppPreference.getStringPreference(mContext, Constant.USER_TYPE);
        if (userType.equalsIgnoreCase("user")) {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) rootView.findViewById(R.id.txtCoinsCount)).setText(coins);
        } else {
            String coins = User.getCoins();
            if (coins.isEmpty()) {
                coins = "0";
            }
            ((TextView) rootView.findViewById(R.id.txtCoinsCount)).setText(coins);
        }

        setTransactionRecyclerView();
    }

    private void setTransactionRecyclerView() {
        RecyclerView recyclerViewTransaction = rootView.findViewById(R.id.recyclerViewTransaction);
        transactionListAdapter = new TransactionListAdapter(mContext, transactionCoinLists, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewTransaction.setLayoutManager(mLayoutManager);
        recyclerViewTransaction.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaction.setAdapter(transactionListAdapter);
        transactionListAdapter.notifyDataSetChanged();

        transactionApi();
    }

    private void transactionApi() {
        String userId = User.getUser().getUser().getUid();
        if (cd.isNetworkAvailable()) {
            RetrofitService.getTransactionList(new Dialog(mContext), retrofitApiClient.transaction("user", userId), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    TransactionMainModal mainModal = (TransactionMainModal) result.body();
                    transactionCoinLists.clear();
                    if (!mainModal.getError()) {
                        if (mainModal.getCoins().size() > 0) {
                            transactionCoinLists.addAll(mainModal.getCoins());
                        }
                    } else {
                        Alerts.show(mContext, mainModal.getMessage());
                    }
                    transactionListAdapter.notifyDataSetChanged();
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
            case R.id.imgQrScan:
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                    } else {
                        Intent i = new Intent(mContext, QrCodeActivity.class);
                        startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(mContext, QrCodeActivity.class);
                    startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                } else {
                    Alerts.show(mContext, "Please give permission");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra(ERROR_DECODING_IMAGE);
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;
        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra(GOT_RESULT);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_coins, null);
            dialogBuilder.setView(dialogView);

            Glide.with(mContext)
                    .load(Constant.COIN_GIF)
                    .placeholder(R.drawable.coin_gif)
                    .into(((ImageView) dialogView.findViewById(R.id.imgCoin)));

            final AlertDialog alertDialog = dialogBuilder.create();

            (dialogView.findViewById(R.id.txtOk)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
