package cn.ffb.pair.item;

import cn.ffb.pair.action.PairAction;
import cn.ffb.adapter.recycler.ViewHolder;


/**
 * Created by lingfei on 2017/6/11.
 */

public interface IPair {

    int getId();

    int getItemViewLayoutId();

    void onBindViewHolder(ViewHolder viewHolder);

    void setOnPairChangeListener(OnPairChangeListener listener);

    void onCreated();

    boolean isVisible();

    boolean isEnable();

    void setAction(PairAction action);

    void setOnPairViewChangedListener(OnPairViewChangedListener listener);

}
