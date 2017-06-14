package cn.ffb.pair.interceptor;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.ffb.pair.item.BaseTextPairItem;
import cn.ffb.pair.item.Interceptor;

/**
 * Created by lingfei on 2017/6/11.
 */

public class DateFormatInterceptor<T extends BaseTextPairItem<T>> implements Interceptor<T> {
    private final static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy年MM月dd日",
            Locale.getDefault());
    private SimpleDateFormat mSimpleDateFormat;

    public DateFormatInterceptor(SimpleDateFormat simpleDateFormat) {
        this.mSimpleDateFormat = simpleDateFormat;
    }

    public DateFormatInterceptor() {
        this(mDateFormat);
    }

    @Override
    public T intercept(Chain<T> chain) throws Exception {
        String text = chain.getPair().getText();
        if (!TextUtils.isEmpty(text) && TextUtils.isDigitsOnly(text)) {
            long time = Long.parseLong(text);
            chain.getPair().setText(mSimpleDateFormat.format(new Date(time)));
        }
        return chain.handle(chain.getPair());
    }

}
