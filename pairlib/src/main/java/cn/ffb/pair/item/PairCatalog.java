package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;

/**
 * Created by lingfei on 2017/6/11.
 */

public class PairCatalog extends PairGroup {
    private String title;

    public PairCatalog(Context context) {
        super(context);

        this.setItemViewLayoutId(R.layout.pair_catalog_layout);
        this.setEnable(false);
    }

    public PairCatalog(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setItemViewLayoutId(R.layout.pair_catalog_layout);
        this.setEnable(false);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.PairCatalog);

        this.title = a.getString(R.styleable.PairCatalog_title);

        a.recycle();

    }

    @Override
    public boolean isEnable() {
        return false;
    }

    public String getTitle() {
        return title;
    }

    public PairCatalog setTitle(String title) {
        this.title = title;
        this.notifyChange();
        return this;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        viewHolder.itemView.setTag(R.id.pair_divider_allowed_above, false);
        viewHolder.itemView.setTag(R.id.pair_divider_allowed_below, false);

        viewHolder.itemView.setEnabled(isEnable());

        viewHolder.bindResId(R.id.title)
                .setVisibility(TextUtils.isEmpty(this.getTitle()) ? View.GONE : View.VISIBLE)
                .setText(this.getTitle());

        notifyPairViewChanged(viewHolder);
    }

}
