package cn.ffb.pair.ui;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.ffb.pair.item.IPair;
import cn.ffb.pair.item.OnPairChangeListener;
import cn.ffb.pair.item.PairGroup;
import cn.ffb.adapter.recycler.IItemViewProviderKeyPolicy;
import cn.ffb.adapter.recycler.RecyclerListAdapter;


/**
 * Created by lingfei on 2017/6/11.
 */
public class PairAdapter extends RecyclerListAdapter<IPair> {
    private List<PairLayout> mLayouts = new ArrayList<>();
    private PairGroup mPairGroup;

    static class PairLayout {
        int itemLayout;
        String name;

        PairLayout(String name, int itemLayout) {
            this.itemLayout = itemLayout;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PairLayout)) {
                return false;
            }
            final PairLayout other = (PairLayout) o;
            return itemLayout == other.itemLayout
                    && name == other.name;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + name.hashCode();
            result = 31 * result + itemLayout;
            return result;
        }
    }

    private void setup() {
        List<IPair> list = mPairGroup.getAllVisiblePairs();
        this.getListData().clear();
        this.getListData().addAll(list);
        for (int i = 0; i < this.getItemCount(); i++) {
            IPair pair = this.getItem(i);
            pair.setOnPairChangeListener(new OnPairChangeListener() {
                @Override
                public void onPairChange(IPair pair) {
                    // 这里要考虑header count的情况
                    // int index = getListData().indexOf(pair);
                    notifyDataSetChanged();
                }

                @Override
                public void onPairHierarchyChange(IPair pair) {
                    setup();
                }
            });

            PairLayout layout = new PairLayout(pair.getClass().getName(), pair.getItemViewLayoutId());
            if (!mLayouts.contains(layout)) {
                mLayouts.add(layout);
            }
        }

        for (int i = 0; i < mLayouts.size(); i++) {
            PairLayout layout = mLayouts.get(i);
            this.register(i, new ItemViewProvider(layout.itemLayout));
        }

        this.setItemViewProviderKeyPolicy(new IItemViewProviderKeyPolicy<IPair>() {
            @Override
            public int getItemViewProviderKey(int position, IPair entity) {
                PairLayout layout = new PairLayout(entity.getClass().getName(), entity.getItemViewLayoutId());
                return mLayouts.indexOf(layout);
            }
        });

        notifyDataSetChanged();
    }

    public PairAdapter(Context context, PairGroup listData) {
        super(context);
        this.mPairGroup = listData;
        setup();
    }

}
