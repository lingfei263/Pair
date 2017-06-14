package cn.ffb.pair.item;

import android.content.Context;
import android.util.AttributeSet;

import cn.ffb.pair.action.PairPreferenceAction;


/**
 * Created by lingfei on 2017/6/11.
 */

public abstract class PreferencePairItem<T extends PreferencePairItem<T>> extends PairItem<T> {

    public PreferencePairItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onSavePreference(Object value) {
        if (this.getAction() != null) {
            if (this.getAction() instanceof PairPreferenceAction) {
                ((PairPreferenceAction) this.getAction()).onSavePreference(getContext(), value);
            }
        }
    }

    public Object getPreference(Object defaultValue) {
        if (this.getAction() != null) {
            if (this.getAction() instanceof PairPreferenceAction) {
                Object value = ((PairPreferenceAction) this.getAction()).getPreference(getContext());
                if (value == null) {
                    return defaultValue;
                } else {
                    return value;
                }
            }
        }
        return defaultValue;
    }

    public T setPreferenceAction(PairPreferenceAction action) {
        this.setAction(action);
        return (T) this;
    }

}
