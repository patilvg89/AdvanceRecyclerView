package com.virendra.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.view.R;

/**
 * Created by virendra
 */

public class RecyclerViewEmptySupport extends RelativeLayout {
    LayoutInflater mInflater;
    private TextView emptyTextView;
    private ImageView emptyImageView;
    private CustomRecyclerView customRecyclerView;
    private Context context;
    private boolean emptyTextViewAttrAvailable = false;
    private boolean emptyImageViewAttrAvailable = false;
    private boolean emptyTextViewColorAttrAvailable = false;

    public RecyclerViewEmptySupport(Context context) {
        super(context);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init(null);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init(attrs);
    }

    public RecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View v = mInflater.inflate(R.layout.recycler_view_empty_support, this, true);
        emptyTextView = (TextView) v.findViewById(R.id.list_empty_text);
        emptyImageView = (ImageView) v.findViewById(R.id.list_empty_image);
        customRecyclerView = (CustomRecyclerView) v.findViewById(R.id.custom_recycler_view);
        customRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        //hide recycler-view by default if data is available then show it
        customRecyclerView.setVisibility(View.GONE);

        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewEmptySupport);

            //No record found text available
            emptyTextViewAttrAvailable = a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_list_text);
            if (emptyTextViewAttrAvailable) {
                emptyTextView.setText(a.getString(R.styleable.RecyclerViewEmptySupport_empty_list_text));
                emptyTextView.setVisibility(View.VISIBLE);
            }

            //No record found text color available
            emptyTextViewColorAttrAvailable = a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_list_text_color);
            if (emptyTextViewColorAttrAvailable) {
                emptyTextView.setTextColor(a.getColor(R.styleable.RecyclerViewEmptySupport_empty_list_text_color,
                        ContextCompat.getColor(context, android.R.color.black)));
            }

            //Empty image available
            emptyImageViewAttrAvailable = a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_image_drawable);
            if (emptyImageViewAttrAvailable) {
                emptyImageView.setImageDrawable(a.getDrawable(R.styleable.RecyclerViewEmptySupport_empty_image_drawable));
                emptyImageView.setVisibility(View.VISIBLE);
            }

            //Empty image width/height provided by developer
            if (a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_image_width) &&
                    a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_image_width)) {
                setEmptyImageViewWidthHeight(a.getDimension(R.styleable.RecyclerViewEmptySupport_empty_image_width, Dimension.DP),
                        a.getDimension(R.styleable.RecyclerViewEmptySupport_empty_image_height, Dimension.DP));
            }
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (emptyImageViewAttrAvailable) {
            customRecyclerView.setAdapterWithEmptyView(adapter, emptyImageView);
        } else if (emptyTextViewAttrAvailable) {
            customRecyclerView.setAdapterWithEmptyView(adapter, emptyTextView);
        } else {
            customRecyclerView.setAdapter(adapter);
        }
    }

    public void setHasFixedSize(boolean value) {
        customRecyclerView.setHasFixedSize(value);
    }

    public void setAdapterWithEmptyTextView(RecyclerView.Adapter adapter, @Nullable String textMessage) {
        customRecyclerView.setAdapterWithEmptyView(adapter, emptyTextView);
        if (null != textMessage) {
            setNoRecordText(textMessage);
        }
    }


    public void setAdapterWithEmptyImageView(RecyclerView.Adapter adapter, @Nullable Drawable drawable) {
        customRecyclerView.setAdapterWithEmptyView(adapter, emptyImageView);
        if (null != drawable) {
            setEmptyImageViewDrawable(drawable);
        }
    }

    public void setAdapterWithEmptyImageView(RecyclerView.Adapter adapter, @Nullable Drawable drawable, @Dimension int width, @Dimension int height) {
        customRecyclerView.setAdapterWithEmptyView(adapter, emptyImageView);
        if (null != drawable) {
            setEmptyImageViewDrawable(drawable);
        }
        setEmptyImageViewWidthHeight(width, height);
    }

    private void setNoRecordText(String text) {
        emptyTextView.setText(text);
    }

    private void setEmptyImageViewDrawable(Drawable drawable) {
        emptyImageView.setImageDrawable(drawable);
    }

    private void setEmptyImageViewWidthHeight(int width, int height) {
        ViewGroup.LayoutParams params = emptyImageView.getLayoutParams();
        params.height = dpToPx(height);
        params.width = dpToPx(width);
        emptyImageView.setLayoutParams(params);
    }

    private void setEmptyImageViewWidthHeight(float width, float height) {
        ViewGroup.LayoutParams params = emptyImageView.getLayoutParams();
        params.height = (int) height;
        params.width = (int) width;
        emptyImageView.setLayoutParams(params);
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public RecyclerView.Adapter getAdapter() {
        return customRecyclerView.getAdapter();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        customRecyclerView.addItemDecoration(itemDecoration);
    }

    public void notifyDataSetChanged() {
        customRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void notifyItemRemoved(int position) {
        customRecyclerView.getAdapter().notifyItemRemoved(position);
    }

    public void notifyItemInserted(int position) {
        customRecyclerView.getAdapter().notifyItemInserted(position);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        customRecyclerView.getAdapter().notifyItemRangeChanged(positionStart, itemCount);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        customRecyclerView.getAdapter().notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        customRecyclerView.getAdapter().notifyItemRangeRemoved(positionStart, itemCount);
    }

    public void setPaginationAdapter(RecyclerView.Adapter adapter, final int visibleThreshold, final int allItemCount, final RecyclerViewCallback callback) {
        if (adapter.getItemCount() <= visibleThreshold) {//for st page adapter size will be <= perPageCount
            setAdapter(adapter);
        }

        //final int totalPagesForPagination = allItemCount <= visibleThreshold ? 1 : (allItemCount / visibleThreshold) + 1;

        customRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = customRecyclerView.getLayoutManager().getChildCount();
                int totalItemCount = customRecyclerView.getLayoutManager().getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) customRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int firstVisibleItem = ((LinearLayoutManager) customRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if ((totalItemCount - visibleItemCount)//reached to end do api call
                        <= (firstVisibleItem + visibleThreshold) && totalItemCount < allItemCount) {
                    callback.loadMoreItems((totalItemCount / visibleThreshold) + 1);
                } else if ((totalItemCount - visibleItemCount)//reached to end
                        <= (firstVisibleItem + visibleThreshold) && totalItemCount == allItemCount) {
                    callback.hasLoadedAllItems(true);
                }
            }
        });

    }

    public void updatePaginationAdapter(final RecyclerView.Adapter adapter, final int size) {
        customRecyclerView.post(new Runnable() {
            public void run() {
                adapter.notifyItemInserted(size - 1);
            }
        });
    }
}
