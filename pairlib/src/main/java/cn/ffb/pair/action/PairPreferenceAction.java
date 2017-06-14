package cn.ffb.pair.action;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cn.ffb.pair.R;
import cn.ffb.utils.PreferencesUtils;

/**
 * Created by lingfei on 2017/6/11.
 */
public class PairPreferenceAction implements PairAction {
    private String key;

    public static PairPreferenceAction parseAction(Context context, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException {
        TypedArray a = context.getResources().obtainAttributes(attrs,
                R.styleable.PreferenceAction);
        String key = a.getString(R.styleable.PreferenceAction_key);
        a.recycle();

        PairPreferenceAction action = new PairPreferenceAction(key);
        return action;
    }

    public PairPreferenceAction(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public boolean onSavePreference(Context context, Object value) {
        return PreferencesUtils.savePreferences(context, key, value);
    }

    public Object getPreference(Context context) {
        return PreferencesUtils.getAllValue(context).get(key);
    }

}
