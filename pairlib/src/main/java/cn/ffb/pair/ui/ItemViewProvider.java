package cn.ffb.pair.ui;

import cn.ffb.pair.item.IPair;
import cn.ffb.adapter.recycler.ItemViewProvider1;
import cn.ffb.adapter.recycler.ViewHolder;


/**
 * Created by lingfei on 2017/6/11.
 */

class ItemViewProvider extends ItemViewProvider1<IPair> {
    private int layout;

    public ItemViewProvider(int layout) {
        this.layout = layout;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position, IPair entity) {
        super.onBindViewHolder(viewHolder, position, entity);
        entity.onBindViewHolder(viewHolder);
    }

    @Override
    public int getItemViewLayoutId() {
        return layout;
    }

}
