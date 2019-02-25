package infobite.com.tapizy.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import infobite.com.tapizy.R;

public class OtpActivity extends AppCompatActivity {

    private Button submit_otp;
    private TextView btnResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

    }
}
