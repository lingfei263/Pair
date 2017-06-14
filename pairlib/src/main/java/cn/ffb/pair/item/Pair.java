package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.ffb.pair.action.PairAction;
import cn.ffb.pair.action.PairClickAction;
import cn.ffb.adapter.recycler.OnRecyclerViewItemClickListener;
import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;


/**
 * Created by lingfei on 2017/6/11.
 */

public abstract class Pair<T extends Pair<T>> implements IPair {
    private int mId;
    private boolean mEnable = true;
    private boolean mVisible = true;
    private Context mContext;
    private OnPairChangeListener mOnPairChangeListener;
    private List<Interceptor<T>> interceptors = new ArrayList<>();
    private boolean isIntercept = true;
    private boolean isCreated = false;
    private int mItemViewLayoutId;
    private PairAction action;
    private OnRecyclerViewItemClickListener mOnClickListener = new OnRecyclerViewItemClickListener() {

        @Override
        public void onRecyclerViewItemClick(View itemView, int position) {
            performClick();
        }

    };

    private OnPairViewChangedListener mOnPairViewChangedListener;

    public Pair(Context context, AttributeSet attrs) {
        this.mContext = context;

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Pair);

        this.mId = a.getResourceId(R.styleable.Pair_id, 0);
        this.mEnable = a.getBoolean(R.styleable.Pair_enable, true);
        this.mVisible = a.getBoolean(R.styleable.Pair_visible, true);

        a.recycle();
    }

    @Override
    public void setOnPairViewChangedListener(OnPairViewChangedListener listener) {
        this.mOnPairViewChangedListener = listener;
    }

    public T setClickAction(PairClickAction<T> action) {
        this.setAction(action);
        return (T) this;
    }

    @Override
    public void setAction(PairAction action) {
        this.action = action;
    }

    public PairAction getAction() {
        return action;
    }

    public boolean performClick() {
        if (action != null) {
            if (action instanceof PairClickAction) {
                return ((PairClickAction) action).onClick(this);
            }
        }
        return false;
    }

    public T setEnable(boolean enable) {
        this.mEnable = enable;
        this.notifyChange();
        return (T) this;
    }

    @Override
    public boolean isEnable() {
        return mEnable;
    }

    public T setVisible(boolean visible) {
        this.mVisible = visible;
        this.notifyHierarchyChange();
        return (T) this;
    }

    @Override
    public boolean isVisible() {
        return mVisible;
    }

    @Override
    public int getId() {
        return mId;
    }

    public Context getContext() {
        return mContext;
    }

    public void setId(int id) {
        this.mId = id;
    }

    private void intercept() {
        try {
            this.getDataWithInterceptorChain((T) this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private T getDataWithInterceptorChain(T item) throws Exception {
        Interceptor.Chain<T> chain = new DefaultInterceptor(0, item);
        return chain.handle(item);
    }

    class DefaultInterceptor implements Interceptor.Chain<T> {
        private final int index;
        private T pair;

        DefaultInterceptor(int index, T pair) {
            this.index = index;
            this.pair = pair;
        }

        @Override
        public T getPair() {
            return pair;
        }

        @Override
        public T handle(T pair) throws Exception {
            try {
                if (index < interceptors.size()) {
                    Interceptor.Chain<T> chain = new DefaultInterceptor(index + 1, pair);
                    Interceptor<T> intercept = interceptors.get(index);
                    pair.setIntercept(false);
                    T interceptData = intercept.intercept(chain);
                    if (interceptData == null) {
                        throw new NullPointerException("intercept " + intercept + " returned null");
                    }
                    return interceptData;
                }
                return pair;
            } finally {
                pair.setIntercept(true);
            }
        }
    }

    public T addInterceptor(Interceptor<T> interceptor) {
        this.interceptors.add(interceptor);
        if (isCreated) {
            notifyChange();
        }
        return (T) this;
    }

    public void notifyHierarchyChange() {
        if (!isCreated) {
            return;
        }
        if (mOnPairChangeListener != null) {
            mOnPairChangeListener.onPairHierarchyChange(this);
        }
    }

    public void notifyChange() {
        if (!isCreated) {
            return;
        }
        if (isIntercept) {
            this.intercept();
        }
        if (mOnPairChangeListener != null) {
            mOnPairChangeListener.onPairChange(this);
        }
    }

    @Override
    public void onCreated() {
        this.isCreated = true;
        this.notifyChange();
    }

    void setIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        viewHolder.setOnRecyclerViewItemClickListener(mOnClickListener);
    }

    protected void notifyPairViewChanged(ViewHolder viewHolder) {
        if (mOnPairViewChangedListener != null) {
            mOnPairViewChangedListener.onPairViewChanged(viewHolder);
        }
    }

    @Override
    public void setOnPairChangeListener(OnPairChangeListener listener) {
        this.mOnPairChangeListener = listener;
    }

    public void setItemViewLayoutId(int itemViewLayoutId) {
        this.mItemViewLayoutId = itemViewLayoutId;
    }

    @Override
    public final int getItemViewLayoutId() {
        return mItemViewLayoutId;
    }

}
