package cn.ffb.pair.ui;


import android.os.Bundle;

import cn.ffb.pair.item.IPair;
import cn.ffb.pair.item.OnPairCreateListener;
import cn.ffb.pair.item.PairManager;
import cn.ffb.pair.xml.XmlPairFactory;
import cn.ffb.base.BaseActivity;
import cn.ffb.pair.R;


/**
 * Created by lingfei on 2017/6/11.
 */

public class PairActivity extends BaseActivity {
    private PairView mPairView;
    private PairManager mPairManager;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.pair_layout;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        this.mPairView = this.getView(R.id.pairview);
    }

    public void setPairXmlResId(int resId, OnPairCreateListener listener) {
        this.mPairManager = PairManager.create(this.getContext(), new XmlPairFactory(this.getContext(), resId));
        this.mPairManager.setOnPairCreateListener(listener);
        this.mPairManager.attach(this.mPairView);
    }

    public void setPairXmlResId(int resId) {
        setPairXmlResId(resId, null);
    }

    public <T extends IPair> T getPairById(int id) {
        if (this.mPairManager != null) {
            return this.mPairManager.getPairById(id);
        }
        return null;
    }

    public PairManager getPairManager() {
        return mPairManager;
    }

    public PairView getPairView() {
        return mPairView;
    }
}
