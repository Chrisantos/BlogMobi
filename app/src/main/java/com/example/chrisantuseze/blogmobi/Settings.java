package com.example.chrisantuseze.blogmobi;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private TextView tvNotif, tvAbout, tvVersion;
    private Typeface font;
    private CheckBox checkBox;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvAbout = findViewById(R.id.about_head1);
        tvNotif = findViewById(R.id.notif_head1);
        tvVersion = findViewById(R.id.version_head);
        checkBox = findViewById(R.id.checkbox);

        font = Typeface.createFromAsset(getAssets(),  "fonts/Aller_Bd.ttf");
        tvVersion.setTypeface(font);
        tvNotif.setTypeface(font);
        tvAbout.setTypeface(font);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
