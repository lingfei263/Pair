package cn.ffb.pair.action;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cn.ffb.pair.item.Pair;
import cn.ffb.pair.R;

/**
 * Created by lingfei on 2017/6/11.
 */
public class PairActivityAction<T extends Pair<T>> extends PairIntentAction<T> {

    public PairActivityAction(Context context, Class<?> activityClass) {
        super(new Intent(context, activityClass));
    }

    public PairActivityAction(Context context, Class<?> activityClass, Bundle bundle) {
        super(new Intent(context, activityClass).putExtras(bundle));
    }

    public static PairActivityAction parseAction(Context context, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException {

        TypedArray a = context.getResources().obtainAttributes(attrs,
                R.styleable.ActivityAction);
        String className = a.getString(R.styleable.ActivityAction_targetClass);

        a.recycle();

        PairActivityAction action = null;
        try {
            action = new PairActivityAction(context, Class.forName(className));
        } catch (ClassNotFoundException e) {
        }
        return action;
    }

}
