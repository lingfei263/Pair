package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ViewStubCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;


/**
 * Created by lingfei on 2017/6/11.
 */

public abstract class PairItem<T extends PairItem<T>> extends Pair<T> {
    private int icon;
    private Drawable iconDrawable;
    private String key;
    private String description;

    private View mWidgetView;

    public PairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setItemViewLayoutId(R.layout.pair_item_layout);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Pair);

        this.icon = a.getResourceId(R.styleable.Pair_icon, 0);
        this.key = a.getString(R.styleable.Pair_key);
        this.description = a.getString(R.styleable.Pair_description);

        a.recycle();
    }

    private void setAllEnable(ViewGroup itemView, boolean enable) {
        itemView.setEnabled(enable);
        for (int i = 0; i < itemView.getChildCount(); i++) {
            View v = itemView.getChildAt(i);
            v.setEnabled(enable);
            if (v instanceof ViewGroup) {
                setAllEnable((ViewGroup) v, enable);
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        ViewGroup itemView = (ViewGroup) viewHolder.itemView;

        itemView.setTag(R.id.pair_divider_allowed_above, true);
        itemView.setTag(R.id.pair_divider_allowed_below, true);

        // key值
        String key = this.getKey();
        viewHolder.setText(R.id.key, Html.fromHtml(key));
        // 图标
        viewHolder.bindResId(R.id.icon).setVisibility(View.VISIBLE);
        int iconRes = this.getIcon();
        if (iconRes != 0) {
            viewHolder.setImage(iconRes);
        } else if (this.getIconDrawable() != null) {
            viewHolder.setImage(this.getIconDrawable());
        } else {
            viewHolder.setVisibility(View.GONE);
        }
        viewHolder.bindResId(R.id.description).setVisibility(View.VISIBLE);
        String description = this.getDescription();
        if (TextUtils.isEmpty(description)) {
            viewHolder.setText(description).setVisibility(View.GONE);
        } else {
            viewHolder.setText(Html.fromHtml(description));
        }

        ViewStubCompat viewStub = viewHolder.getView(R.id.view_stub);
        viewStub.setLayoutResource(getWidgetLayoutResource());
        viewStub.setVisibility(View.VISIBLE);

        setAllEnable(itemView, isEnable());

        notifyPairViewChanged(viewHolder);
    }

    abstract public int getWidgetLayoutResource();

    public String getDescription() {
        return description;
    }

    public T setDescription(String description) {
        this.description = description;
        this.notifyChange();
        return (T) this;
    }

    public int getIcon() {
        return icon;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public T setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        this.notifyChange();
        return (T) this;
    }

    public T setIcon(int icon) {
        this.icon = icon;
        this.notifyChange();
        return (T) this;
    }

    public String getKey() {
        return key;
    }

    public T setKey(String key) {
        this.key = key;
        this.notifyChange();
        return (T) this;
    }

}
