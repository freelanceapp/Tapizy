package infobite.ibt.tapizy.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import infobite.ibt.tapizy.R;
import infobite.ibt.tapizy.utils.BaseActivity;

public class TermsPolicyActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_policy);
        init();
    }

    private void init() {
        updateWebViewCoins();

        findViewById(R.id.imgBack).setOnClickListener(this);
        TextView textView = findViewById(R.id.tvDetail);
        String title = getIntent().getStringExtra("title");
        String data = getIntent().getStringExtra("data");

        ((TextView) findViewById(R.id.txtTitle)).setText(title);
        textView.setText(Html.fromHtml(data));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }
}

