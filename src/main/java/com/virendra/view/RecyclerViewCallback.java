package com.virendra.view;

/**
 * Created by virendra on 20/6/17.
 */

public interface RecyclerViewCallback {
    void loadMoreItems(int pageNo);

    void hasLoadedAllItems(boolean value);
}
