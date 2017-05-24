package com.virendra.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by virendra
 */

class CustomRecyclerView extends RecyclerView {

    private View emptyView;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    CustomRecyclerView.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    CustomRecyclerView.this.setVisibility(View.VISIBLE);
                }
            } else if (adapter != null && emptyView == null) {
                CustomRecyclerView.this.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    protected void setAdapterWithEmptyView(Adapter adapter, View emptyView) {
        this.emptyView = emptyView;
        setAdapter(adapter);
    }
}
