package cn.ffb.pair.xml;

import android.content.Context;

import cn.ffb.pair.item.PairFactory;
import cn.ffb.pair.item.PairGroup;

/**
 * Created by lingfei on 2017/6/11.
 */

public class XmlPairFactory extends PairFactory {
    private int xmlResId;
    private PairInflater inflater;

    public XmlPairFactory(Context context, int xmlResId) {
        super(context);
        this.inflater = new PairInflater(context);
        this.xmlResId = xmlResId;
    }

    @Override
    protected PairGroup onCreatePairGroup(Context context) {
        return (PairGroup) inflater.inflate(this.xmlResId);
    }

}
