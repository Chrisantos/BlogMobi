package com.example.chrisantuseze.blogmobi.Library;

import java.util.List;

/**
 * Created by CHRISANTUS EZE on 3/24/2018.
 */

public class RSSFeed {
    /**
     * List of parsed {@link RSSItem} objects
     */
    private List<RSSItem> mItems;

    public List<RSSItem> getItems() {
        return mItems;
    }

    public void setItems(List<RSSItem> items) {
        mItems = items;
    }
}
