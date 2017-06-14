package cn.ffb.pair.item;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lingfei on 2017/6/11.
 */

public class PairGroup extends Pair<PairGroup> {
    private List<IPair> pairs = new ArrayList<>();

    public PairGroup(Context context) {
        this(context, null);
    }

    public PairGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void removePair(IPair pair) {
        this.pairs.remove(pair);
        this.notifyHierarchyChange();
    }

    public void removeAllPair(List<IPair> pairs) {
        this.pairs.removeAll(pairs);
        this.notifyHierarchyChange();
    }

    public void addPair(IPair pair) {
        this.pairs.add(pair);
        this.notifyHierarchyChange();
    }

    public void addAllPair(List<IPair> pairs) {
        this.pairs.addAll(pairs);
        this.notifyHierarchyChange();
    }

    public <T extends IPair> T getPairByIndex(int index) {
        return (T) getAllVisiblePairs().get(index);
    }

    protected List<IPair> getPairs() {
        return pairs;
    }

    private void _getPairs(List<IPair> list, PairGroup pairGroup) {
        for (IPair pair : pairGroup.pairs) {
            list.add(pair);
            if (pair instanceof PairGroup) {
                _getPairs(list, (PairGroup) pair);
            }
        }
    }

    public List<IPair> getAllPairs() {
        List<IPair> list = new ArrayList<>();
        _getPairs(list, this);
        return list;
    }

    public List<IPair> getAllVisiblePairs() {
        List<IPair> list = new ArrayList<>();
        for (IPair pair : getAllPairs()) {
            if (pair.isVisible()) {
                list.add(pair);
            }
        }
        return list;
    }

    public <T extends IPair> T getPairById(int id) {
        for (IPair pair : getAllPairs()) {
            if (pair.getId() == id) {
                return (T) pair;
            }
        }
        return null;
    }

}
