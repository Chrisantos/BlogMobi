package com.example.chrisantuseze.blogmobi;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {
    private TextView tvCall, tvEmail, tvSendEmail;
    private ImageView mStartCall, mGotoMail;
    private Typeface font;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        tvCall = findViewById(R.id.call);
        tvEmail = findViewById(R.id.email);
        tvSendEmail = findViewById(R.id.send_mail);

        mStartCall = findViewById(R.id.start_call);
        mGotoMail = findViewById(R.id.goto_mail);

        font = Typeface.createFromAsset(getAssets(),  "fonts/Aller_Bd.ttf");
        tvSendEmail.setTypeface(font);
        tvEmail.setTypeface(font);
        tvCall.setTypeface(font);

        mGotoMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ "ezechris30@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(intent);
            }
        });

        mStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:08138203079"));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
