package com.example.chrisantuseze.blogmobi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.chrisantuseze.blogmobi.FirebaseJobDispatcher.MyService;
import com.example.chrisantuseze.blogmobi.Library.RSSConverterFactory;
import com.example.chrisantuseze.blogmobi.Library.RSSFeed;
import com.example.chrisantuseze.blogmobi.Library.RSSItem;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import de.mateware.snacky.Snacky;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListArticle extends AppCompatActivity implements RSSItemsAdapter.OnItemClickListener{
    private static String mFeedUrl = "http://www.infoscopemedia.com.ng/feeds/posts/default?alt=rss";
    public static final String KEY_FEED = "FEED";
    private RSSItemsAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwRefresh;
    private ProgressBar mProgressBar;
    private ImageView mImage;

    private static final String JOB_TAG = "my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;

    private Toolbar mToolbar;


    private static final int SYNC_INTERVAL_HOURS = 1;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_article);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mSwRefresh = findViewById(R.id.swRefresh);
        mProgressBar = findViewById(R.id.progressBar);
//        mImage = findViewById(R.id.image);


        mAdapter = new RSSItemsAdapter(this, mImage);
        mAdapter.setListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRss();
                mSwRefresh.setRefreshing(false);
            }
        });

        startJob();

        fetchRss();

        

    }

    public void fetchRss() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://github.com")
                .addConverterFactory(RSSConverterFactory.create()).build();

        final Typeface fontTitle = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Bold.ttf");

        mProgressBar.setVisibility(View.VISIBLE);
        RSSService service = retrofit.create(RSSService.class);
        service.getRss(mFeedUrl).enqueue(new Callback<RSSFeed>() {
            @Override
            public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
                onRssItemsLoaded(response.body().getItems());
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<RSSFeed> call, Throwable t) {
                Snacky.builder()
                        .setActivity(ListArticle.this)
                        .setText("Unable to load feeds")
                        .setTextTypeface(fontTitle)
                        .setDuration(Snacky.LENGTH_LONG)
                        .setActionText(android.R.string.ok)
                        .error()
                        .show();

                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onRssItemsLoaded(List<RSSItem> rssItems) {
        if (rssItems != null){
            mAdapter.setItems(rssItems);
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getVisibility() != View.VISIBLE) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public void onItemSelected(RSSItem rssItem) {
        Intent intent = new Intent(getApplicationContext(), DetailArticle.class);
        intent.putExtra(KEY_FEED, rssItem);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }else if (id == R.id.action_contact){
            startActivity(new Intent(getApplicationContext(), ContactUs.class));
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void startJob(){
        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job job = jobDispatcher.newJobBuilder().
                setService(MyService.class).
                setLifetime(Lifetime.FOREVER).
                setRecurring(true).
                setTag(JOB_TAG).
                setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS)).
                setReplaceCurrent(true).
                setConstraints(Constraint.ON_ANY_NETWORK).
                build();
        jobDispatcher.schedule(job);

    }
}
