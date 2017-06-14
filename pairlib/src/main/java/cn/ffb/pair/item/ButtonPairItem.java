package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;

/**
 * Created by lingfei on 2017/6/11.
 */

public class ButtonPairItem extends Pair<ButtonPairItem> {
    private String text;

    public ButtonPairItem(Context context) {
        this(context, null);
    }

    public ButtonPairItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setItemViewLayoutId(R.layout.pair_widget_button);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ButtonPairItem);

        this.text = a.getString(R.styleable.ButtonPairItem_text);

        a.recycle();
    }

    public ButtonPairItem setText(String text) {
        this.text = text;
        this.notifyChange();
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        viewHolder.setText(R.id.button, text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick();
            }
        });
    }
}
