package cn.ffb.pair.xml;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import cn.ffb.pair.action.PairActivityAction;
import cn.ffb.pair.action.PairIntentAction;
import cn.ffb.pair.action.PairPreferenceAction;
import cn.ffb.pair.item.IPair;
import cn.ffb.pair.item.PairGroup;


/**
 * Created by lingfei on 2017/6/11.
 */

public class PairInflater {
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{
            Context.class, AttributeSet.class};
    private static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap<>();
    private final Context mContext;
    private final Object[] mConstructorArgs = new Object[2];
    private String[] mDefaultPackages;
    private static final String INTENT_TAG_NAME = "intent";
    private static final String EXTRA_TAG_NAME = "extra";
    private static final String PREFERENCE_ACTION_TAG_NAME = "PairPreferenceAction";
    private static final String INTENT_ACTION_TAG_NAME = "PairIntentAction";
    private static final String ACTIVITY_ACTION_TAG_NAME = "PairActivityAction";
    private static final String DIALOG_ACTION_TAG_NAME = "PairDialogAction";

    public PairInflater(Context context) {
        mContext = context;
        mConstructorArgs[0] = context;
        setDefaultPackages(new String[]{"cn.ffb.pair.item."});
    }

    public void setDefaultPackages(String[] defaultPackage) {
        mDefaultPackages = defaultPackage;
    }

    public String[] getDefaultPackages() {
        return mDefaultPackages;
    }

    public IPair inflate(int resource) {
        XmlResourceParser parser = mContext.getResources().getXml(resource);
        try {
            return inflate(parser);
        } finally {
            parser.close();
        }
    }

    private IPair createItem(@NonNull String name, @Nullable String[] prefixes,
                             AttributeSet attrs)
            throws ClassNotFoundException, InflateException {
        Constructor constructor = CONSTRUCTOR_MAP.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real,
                // and try to add it
                final ClassLoader classLoader = mContext.getClassLoader();
                Class<?> clazz = null;
                if (prefixes == null || prefixes.length == 0) {
                    clazz = classLoader.loadClass(name);
                } else {
                    ClassNotFoundException notFoundException = null;
                    for (final String prefix : prefixes) {
                        try {
                            clazz = classLoader.loadClass(prefix + name);
                            break;
                        } catch (final ClassNotFoundException e) {
                            notFoundException = e;
                        }
                    }
                    if (clazz == null) {
                        if (notFoundException == null) {
                            throw new InflateException(attrs
                                    .getPositionDescription()
                                    + ": Error inflating class " + name);
                        } else {
                            throw notFoundException;
                        }
                    }
                }
                constructor = clazz.getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor.setAccessible(true);
                CONSTRUCTOR_MAP.put(name, constructor);
            }

            Object[] args = mConstructorArgs;
            args[1] = attrs;
            return (IPair) constructor.newInstance(args);

        } catch (ClassNotFoundException e) {
            // If loadClass fails, we should propagate the exception.
            throw e;
        } catch (Exception e) {
            final InflateException ie = new InflateException(attrs
                    .getPositionDescription() + ": Error inflating class " + name);
            ie.initCause(e);
            throw ie;
        }
    }

    protected IPair onCreateItem(String name, AttributeSet attrs)
            throws ClassNotFoundException {
        return createItem(name, mDefaultPackages, attrs);
    }

    private IPair createItemFromTag(String name, AttributeSet attrs) {
        try {
            final IPair item;

            if (-1 == name.indexOf('.')) {
                item = onCreateItem(name, attrs);
            } else {
                item = createItem(name, null, attrs);
            }

            return item;

        } catch (InflateException e) {
            throw e;

        } catch (ClassNotFoundException e) {
            final InflateException ie = new InflateException(attrs
                    .getPositionDescription()
                    + ": Error inflating class (not found)" + name);
            ie.initCause(e);
            throw ie;

        } catch (Exception e) {
            final InflateException ie = new InflateException(attrs
                    .getPositionDescription()
                    + ": Error inflating class " + name);
            ie.initCause(e);
            throw ie;
        }
    }

    public IPair inflate(XmlPullParser parser) {
        AttributeSet attrs = Xml.asAttributeSet(parser);
        try {
            int type;
            do {
                type = parser.next();
            } while ((type != 2) && (type != 1));

            if (type != 2) {
                throw new InflateException(parser.getPositionDescription() + ": No start tag found!");
            }

            IPair pair = createItemFromTag(parser.getName(), attrs);
            rInflate(parser, pair, attrs);
            return pair;

        } catch (InflateException e) {
            throw e;
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            InflateException ex = new InflateException(parser
                    .getPositionDescription() + ": " + e
                    .getMessage());
            ex.initCause(e);
            throw ex;
        }
    }

    private void rInflate(XmlPullParser parser, IPair parent, final AttributeSet attrs)
            throws XmlPullParserException, IOException {
        final int depth = parser.getDepth();

        int type;
        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            final String name = parser.getName();
            if (INTENT_ACTION_TAG_NAME.equals(name)) {

            } else if (ACTIVITY_ACTION_TAG_NAME.equals(name)) {
                PairActivityAction action = PairActivityAction.parseAction(mContext, parser, attrs);
                parent.setAction(action);
            } else if (INTENT_TAG_NAME.equals(name)) {
                final Intent intent;
                try {
                    intent = Intent.parseIntent(mContext.getResources(), parser, attrs);
                } catch (IOException e) {
                    XmlPullParserException ex = new XmlPullParserException(
                            "Error parsing preference");
                    ex.initCause(e);
                    throw ex;
                }
                parent.setAction(new PairIntentAction(intent));
            } else if (PREFERENCE_ACTION_TAG_NAME.equals(name)) {
                PairPreferenceAction action = PairPreferenceAction.parseAction(mContext, parser, attrs);
                parent.setAction(action);
            } else {
                final IPair item = createItemFromTag(name, attrs);
                ((PairGroup) parent).addPair(item);
                rInflate(parser, item, attrs);
            }

        }

    }

    private static void skipCurrentTag(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int outerDepth = parser.getDepth();
        int type;
        do {
            type = parser.next();
        } while (type != XmlPullParser.END_DOCUMENT
                && (type != XmlPullParser.END_TAG || parser.getDepth() > outerDepth));
    }
}
