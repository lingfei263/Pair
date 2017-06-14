package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;

/**
 * Created by lingfei on 2017/6/11.
 */

public class BaseTextPairItem<T extends BaseTextPairItem<T>> extends PairItem<T> {
    private String text;

    public BaseTextPairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.BaseTextPairItem);

        this.text = a.getString(R.styleable.BaseTextPairItem_text);

        a.recycle();
    }

    public String getText() {
        return text;
    }

    public T setText(String text) {
        this.text = text;
        this.notifyChange();
        return (T) this;
    }

    public T setText(Object text) {
        this.text = text.toString();
        return setText(this.text);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        String value = this.getText();
        viewHolder.bindResId(R.id.text)
                .setText(TextUtils.isEmpty(value) ? "" : Html.fromHtml(value));
    }

    @Override
    public int getWidgetLayoutResource() {
        return R.layout.pair_widget_textview;
    }
}
