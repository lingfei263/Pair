package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;
import cn.ffb.utils.DisplayUtils;


/**
 * Created by lingfei on 2017/6/11.
 */

public class ImagePairItem extends PairItem<ImagePairItem> {
    private int resId;
    private String url;
    private int width;
    private int height;

    public ImagePairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ImagePairItem);

        this.resId = a.getResourceId(R.styleable.ImagePairItem_resId, 0);
        this.url = a.getString(R.styleable.ImagePairItem_url);
        this.width = a.getDimensionPixelSize(R.styleable.ImagePairItem_width, DisplayUtils.dp2Px(getContext(), 32));
        this.height = a.getDimensionPixelSize(R.styleable.ImagePairItem_width, DisplayUtils.dp2Px(getContext(), 32));

        a.recycle();
    }

    public ImagePairItem(Context context) {
        this(context, null);
    }

    public int getResId() {
        return resId;
    }

    public ImagePairItem setResId(int resId) {
        this.resId = resId;
        this.notifyChange();
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ImagePairItem setUrl(String url) {
        this.url = url;
        this.notifyChange();
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ImagePairItem setWidth(int width) {
        this.width = width;
        this.notifyChange();
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ImagePairItem setHeight(int height) {
        this.height = height;
        this.notifyChange();
        return this;
    }

    @Override
    public int getWidgetLayoutResource() {
        return R.layout.pair_widget_imageview;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);

        ImageView imageView = viewHolder.bindResId(R.id.image).getView(R.id.image);

        if (TextUtils.isEmpty(this.getUrl())) {
            if (this.getResId() != 0) {
                viewHolder.setImage(this.getResId());
            }
        } else {
            viewHolder.setImage(this.getUrl());
        }

        int width = this.getWidth();
        int height = this.getHeight();

        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = height;
        params.width = width;
        imageView.setLayoutParams(params);

    }
}
