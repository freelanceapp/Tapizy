package infobite.com.tapizy.ui.activity.community_module;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import infobite.com.tapizy.R;
import infobite.com.tapizy.adapter.CustomerListAdapter;
import infobite.com.tapizy.model.city_list_modal.CityList;
import infobite.com.tapizy.model.city_list_modal.CityListMainModal;
import infobite.com.tapizy.model.database_modal.ChatbotList;
import infobite.com.tapizy.utils.Alerts;
import infobite.com.tapizy.utils.AppProgressDialog;
import infobite.com.tapizy.utils.BaseActivity;
import infobite.com.tapizy.utils.GpsTracker;

public class CommunityActivity extends BaseActivity implements View.OnClickListener {

    private List<ChatbotList> chatbotLists = new ArrayList<>();

    private CustomerListAdapter cityListAdapter;
    private CityListMainModal loginModal;
    private List<CityList> cityList = new ArrayList<>();
    private Dialog dialogCityList, dialog;
    double latitude; // latitude
    double longitude; // longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        init();
    }

    private void init() {
        dialog = new Dialog(mContext);

        locationPermission();
    }

    private void locationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                turnGPSOn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            turnGPSOn();
        } else {
            Alerts.show(mContext, "Select manual");
        }
    }

    public void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            GpsTracker gpsTracker = new GpsTracker(mContext);
            getAddressList();
        }
    }

    private void getAddressList() {
        GpsTracker gpsTracker = new GpsTracker(mContext);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        AppProgressDialog.show(dialog);
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                AppProgressDialog.hide(dialog);
                String strAddress = (addresses.get(0).getAddressLine(0));
                String strCity = (addresses.get(0).getLocality());
                String strState = (addresses.get(0).getAdminArea());
                String strCountry = (addresses.get(0).getCountryName());
                String strZipCode = (addresses.get(0).getPostalCode());

                ((TextView) findViewById(R.id.tvSelectCity)).setText(strCity);
            } else {
                AppProgressDialog.show(dialog);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAddressList();
                    }
                }, 3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cityListDialog() {
        dialogCityList = new Dialog(mContext);
        dialogCityList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCityList.setContentView(R.layout.dialog_city_list);

        dialogCityList.setCanceledOnTouchOutside(true);
        dialogCityList.setCancelable(true);
        if (dialogCityList.getWindow() != null)
            dialogCityList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        cityList.clear();
        cityList.addAll(loginModal.getUser());

        cityListAdapter = new CustomerListAdapter(mContext, R.layout.row_city_list, cityList);
        ListView listViewCustomer = (ListView) dialogCityList.findViewById(R.id.listViewCustomer);
        listViewCustomer.setAdapter(cityListAdapter);
        cityListAdapter.notifyDataSetChanged();

        listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) findViewById(R.id.tvSelectCustomer)).setText(location);
                dialogCityList.dismiss();
            }
        });

        ((EditText) dialogCityList.findViewById(R.id.edtSearchCity))
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        cityListAdapter.getFilter().filter(cs);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

        Window window = dialogCityList.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialogCityList.show();
    }

    @Override
    public void onClick(View v) {

    }
}
