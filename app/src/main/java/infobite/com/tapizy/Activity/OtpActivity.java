package infobite.com.tapizy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        init();
    }

    private void init()
    {
        submit_otp = findViewById(R.id.submit_otp);
        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnResend = findViewById(R.id.btnResend);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpActivity.this, TrandingActivity.class);
                startActivity(intent);
            }
        });
    }
}
