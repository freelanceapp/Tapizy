package infobite.com.tapizy.otp_verification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import infobite.com.tapizy.R;
import infobite.com.tapizy.ui.Activity.HomeActivity;
import infobite.com.tapizy.ui.Activity.MainActivity;
import infobite.com.tapizy.utils.BaseActivity;

public class OtpVerificationActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_verification);

        Button submitotp = findViewById(R.id.submit_otp);
        submitotp.setOnClickListener(this);

        if (checkAndRequestPermissions()) {
        }
        readOtp();

    }
    private void readOtp(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //  String  number = extras.getString("mobilenumber");
            String message = extras.getString("message");
            //  ((TextView)findViewById(R.id.mobile)).setText(number);
            int a1 = Integer.parseInt(String.valueOf(message.charAt(0)));
            ((EditText) findViewById(R.id.et_otp_a)).setText(a1 + "");
            int a2 = message.charAt(1);
            ((EditText)findViewById(R.id.et_otp_b)).setText(a2 + "");
            int a3 = Integer.parseInt(String.valueOf(message.charAt(2)));
            ((EditText)findViewById(R.id.et_otp_c)).setText(a3 + "");
            int a4 = Integer.parseInt(String.valueOf(message.charAt(3)));
            ((EditText)findViewById(R.id.et_otp_d)).setText(a4 + "");
            int a5 = Integer.parseInt(String.valueOf(message.charAt(4)));
            ((EditText)findViewById(R.id.et_otp_e)).setText(a5 + "");
            int a6 = Integer.parseInt(String.valueOf(message.charAt(5)));
            ((EditText)findViewById(R.id.et_otp_f)).setText(a6 + "");
        }
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_otp:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
        }
    }
}
