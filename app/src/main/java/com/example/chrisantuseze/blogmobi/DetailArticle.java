package com.example.chrisantuseze.blogmobi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chrisantuseze.blogmobi.Library.RSSItem;

public class DetailArticle extends AppCompatActivity {
    private TextView tvTitle, tvPubDate, tvDescription, tvContinue;
    private ImageView imageView;
    Toolbar mToolbar;
    public static final String URL_KEY = "infoscope";
    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvPubDate = findViewById(R.id.article_pubdate);
        tvDescription = findViewById(R.id.article_detail);
        tvTitle = findViewById(R.id.article_title);
        tvContinue = findViewById(R.id.continuereading);
        imageView = findViewById(R.id.image);

        final RSSItem item = (RSSItem) getIntent().getExtras().getSerializable(ListArticle.KEY_FEED);

        if(item != null){
            String title = item.getTitle();
            String pubDate = item.getPublishDate();
            String description = item.getDescription();
            url = item.getLink();


            tvTitle.setText(title);
            tvPubDate.setText(pubDate);
            tvDescription.setText(Html.fromHtml(description));

            Typeface font = Typeface.createFromAsset(getAssets(),  "fonts/Sansation-Regular.ttf");

            tvPubDate.setTypeface(font);
            tvContinue.setTypeface(font);
            Glide.with(this).load(item.getImage()).into(imageView);

            tvContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                    Intent webIntent = new Intent(getApplicationContext(), LinkWebview.class);
                    webIntent.putExtra(URL_KEY, url);
                    startActivity(webIntent);
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.detailmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
