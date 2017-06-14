package cn.ffb.pair.action;

import android.content.Context;

import cn.ffb.pair.item.Pair;
import cn.ffb.dialog.core.IDialog;

/**
 * Created by lingfei on 2017/6/11.
 */
public abstract class PairDialogAction<T extends Pair<T>> implements PairClickAction<T> {
    private IDialog dialog;
    private Context context;

    public PairDialogAction(Context context) {
        this.context = context;
    }

    @Override
    public boolean onClick(T pairItem) {
        dialog = onCreateDialog(context);
        dialog.show();
        return false;
    }

    protected abstract IDialog onCreateDialog(Context context);

}
