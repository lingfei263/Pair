package cn.ffb.pair.action;

import android.content.Intent;

import cn.ffb.pair.item.Pair;

/**
 * Created by lingfei on 2017/6/11.
 */
public class PairIntentAction<T extends Pair<T>> implements PairClickAction<T> {
    private Intent intent;

    public PairIntentAction(Intent intent) {
        this.intent = intent;
    }

    @Override
    public boolean onClick(T pairItem) {
        pairItem.getContext().startActivity(intent);
        return false;
    }

}
