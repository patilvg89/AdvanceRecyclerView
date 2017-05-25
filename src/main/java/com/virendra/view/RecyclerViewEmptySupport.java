package com.virendra.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
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

        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewEmptySupport);

            //No record found text available
            emptyTextViewAttrAvailable = a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_list_text);
            if (emptyTextViewAttrAvailable) {
                emptyTextView.setText(a.getString(R.styleable.RecyclerViewEmptySupport_empty_list_text));
            }

            //Empty image available
            emptyImageViewAttrAvailable = a.hasValue(R.styleable.RecyclerViewEmptySupport_empty_image_drawable);
            if (emptyImageViewAttrAvailable) {
                emptyImageView.setImageDrawable(a.getDrawable(R.styleable.RecyclerViewEmptySupport_empty_image_drawable));
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
}
