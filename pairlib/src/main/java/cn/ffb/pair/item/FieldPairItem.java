package cn.ffb.pair.item;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cn.ffb.pair.interceptor.FieldParserInterceptor;
import cn.ffb.pair.R;

/**
 * Created by lingfei on 2017/6/11.
 */
public class FieldPairItem extends BaseTextPairItem<FieldPairItem> {
    private Object entity;
    private String exp;

    public FieldPairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.FieldPairItem);

        this.exp = a.getString(R.styleable.FieldPairItem_exp);

        a.recycle();

        this.addInterceptor(new FieldParserInterceptor());
    }

    public FieldPairItem(Context context) {
        this(context, null);
    }

    public Object getEntity() {
        return entity;
    }

    public FieldPairItem setEntity(Object entity) {
        this.entity = entity;
        this.notifyChange();
        return this;
    }

    public String getExp() {
        return exp;
    }

    public FieldPairItem setExp(String exp) {
        this.exp = exp;
        this.notifyChange();
        return this;
    }

}
