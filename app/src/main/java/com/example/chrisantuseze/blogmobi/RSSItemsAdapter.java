package com.example.chrisantuseze.blogmobi;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chrisantuseze.blogmobi.Library.RSSItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CHRISANTUS EZE on 3/24/2018.
 */

public class RSSItemsAdapter extends RecyclerView.Adapter<RSSItemsAdapter.ViewHolder> {
    private final List<RSSItem> mItems = new ArrayList<>();
    private OnItemClickListener mListener;
    private final Context context;
    private ImageView imageView;

    RSSItemsAdapter(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    /**
     * Item click listener
     *
     * @param listener listener
     */
    void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Set {@link RSSItem} list
     *
     * @param items item list
     */
    void setItems(List<RSSItem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public RSSItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RSSItemsAdapter.ViewHolder holder, int position) {
        if (mItems.size() <= position) {
            return;
        }

        final RSSItem item = mItems.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvPubDate.setText(item.getPublishDate());
        if (position == 1){
//            if (item.getImage() != null)
//                Picasso.get().load(item.getImage()).fit().placeholder(R.drawable.awesome).centerCrop().into(imageView);
        }else{
            if (item.getImage() != null){
                String images = item.getImage();
                Glide.with(context).load(images).into(holder.image);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onItemSelected(item);
            }
        });
        holder.itemView.setTag(item);

        Typeface fontTitle = Typeface.createFromAsset(context.getAssets(),  "fonts/Roboto-Bold.ttf");
        Typeface fontDate = Typeface.createFromAsset(context.getAssets(),  "fonts/Sansation-Light.ttf");

        holder.tvTitle.setTypeface(fontTitle);
        holder.tvPubDate.setTypeface(fontDate);

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    interface OnItemClickListener {
        void onItemSelected(RSSItem rssItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_title_text)
        TextView tvTitle;

        @BindView(R.id.article_listing_smallprint)
        TextView tvPubDate;

        @BindView(R.id.image)
        ImageView image;



        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
