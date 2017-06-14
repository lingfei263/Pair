package cn.ffb.pair.action;


import cn.ffb.pair.item.Pair;

/**
 * Created by lingfei on 2017/6/11.
 */
public interface PairClickAction<T extends Pair<T>> extends PairAction {

    boolean onClick(T pair);

}
