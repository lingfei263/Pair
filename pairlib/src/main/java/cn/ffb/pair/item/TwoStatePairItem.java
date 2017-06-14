package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cn.ffb.pair.action.PairAction;
import cn.ffb.pair.action.PairPreferenceAction;
import cn.ffb.pair.R;


/**
 * Created by lingfei on 2017/6/11.
 */
public abstract class TwoStatePairItem<T extends TwoStatePairItem<T>> extends PreferencePairItem<T> {
    private boolean checked;

    public TwoStatePairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.TwoStatePairItem);

        this.checked = a.getBoolean(R.styleable.TwoStatePairItem_checked, false);

        a.recycle();
    }

    public boolean isChecked() {
        return checked;
    }

    public T setChecked(boolean checked) {
        if (this.checked == checked) {
            return (T) this;
        }
        this.checked = checked;
        this.notifyChange();
        return (T) this;
    }

    @Override
    public void notifyChange() {
        this.onSavePreference(checked);
        super.notifyChange();
    }

    public T toggle() {
        return this.setChecked(!this.isChecked());
    }

    @Override
    public boolean performClick() {
        boolean handle = super.performClick();
        if (!handle) {
            this.toggle();
        }
        return handle;
    }

    @Override
    public void setAction(PairAction action) {
        super.setAction(action);
        if (action instanceof PairPreferenceAction) {
            this.addInterceptor(new Interceptor<T>() {
                @Override
                public T intercept(Chain<T> chain) throws Exception {
                    boolean checked = (boolean) getPreference(false);
                    chain.getPair().setChecked(checked);
                    return chain.handle(chain.getPair());
                }
            });
        }
    }

}
