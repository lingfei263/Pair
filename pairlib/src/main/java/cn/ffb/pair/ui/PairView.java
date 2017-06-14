package cn.ffb.pair.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import cn.ffb.pair.R;
import cn.ffb.utils.DisplayUtils;
import cn.ffb.widget.xrecyclerview.RecyclerListView;


/**
 * Created by lingfei on 2017/6/11.
 */

public class PairView extends RecyclerListView {

    public PairView(Context context) {
        super(context);
        initView();
    }

    public PairView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PairView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void initView() {
        this.setLayoutManager(new LinearLayoutManager(getContext()));
        this.setVerticalScrollBarEnabled(false);
        this.setBackgroundResource(android.R.color.white);
        this.setDividerDecoration(new PairView.DividerDecoration(getContext()));
    }

    private class DividerDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        private Drawable mDivider;
        private int mDividerHeight;

        public DividerDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();

            mDividerHeight = 1;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (mDivider == null) {
                return;
            }
            final int childCount = parent.getChildCount();
            final int width = parent.getWidth();
            for (int childViewIndex = 0; childViewIndex < childCount; childViewIndex++) {
                final View view = parent.getChildAt(childViewIndex);
                if (shouldDrawDividerBelow(view, parent)) {
                    int top = (int) ViewCompat.getY(view) + view.getHeight();
                    int offset = DisplayUtils.dp2Px(getContext(), 16);
                    mDivider.setBounds(0 + offset, top, width - offset, top + mDividerHeight);
                    mDivider.draw(c);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (shouldDrawDividerBelow(view, parent)) {
                //outRect.bottom = mDividerHeight;
            } else {
                outRect.bottom = 0;
            }
        }

        private boolean shouldDrawDividerBelow(View view, RecyclerView parent) {
            final RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            Boolean dividerAllowedBelow = (Boolean) holder.itemView.getTag(R.id.pair_divider_allowed_below);
            if (dividerAllowedBelow == null || !dividerAllowedBelow) {
                return false;
            }
            Boolean nextAllowed = true;
            int index = parent.indexOfChild(view);
            if (index < parent.getChildCount() - 1) {
                final View nextView = parent.getChildAt(index + 1);
                final RecyclerView.ViewHolder nextHolder = parent.getChildViewHolder(nextView);
                nextAllowed = (Boolean) nextHolder.itemView.getTag(R.id.pair_divider_allowed_above);
            }
            return nextAllowed == null || nextAllowed;
        }

        public void setDivider(Drawable divider) {
            if (divider != null) {
                mDividerHeight = divider.getIntrinsicHeight();
            } else {
                mDividerHeight = 0;
            }
            mDivider = divider;
            invalidateItemDecorations();
        }

        public void setDividerHeight(int dividerHeight) {
            mDividerHeight = dividerHeight;
            invalidateItemDecorations();
        }
    }


}
