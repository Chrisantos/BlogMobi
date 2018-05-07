package com.example.chrisantuseze.blogmobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class LinkWebview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_webview);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            String url = getIntent().getExtras().getString(DetailArticle.URL_KEY);
            Bundle arguments = new Bundle();
            arguments.putString(LinkWebviewFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(LinkWebviewFragment.ARG_ITEM_ID));
            arguments.putString(DetailArticle.URL_KEY, url);
            LinkWebviewFragment fragment = new LinkWebviewFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.website_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            navigateUpTo(new Intent(LinkWebview.this, DetailArticle.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
