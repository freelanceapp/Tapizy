package infobite.com.tapizy.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import infobite.com.tapizy.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                startActivity(intent);
            }
        });
    }
}
