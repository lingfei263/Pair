package cn.ffb.pair.interceptor;

import android.text.TextUtils;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.MapContext;

import java.util.Locale;

import cn.ffb.pair.item.FieldPairItem;
import cn.ffb.pair.item.Interceptor;

/**
 * Created by lingfei on 2017/6/11.
 */

public class FieldParserInterceptor implements Interceptor<FieldPairItem> {
    private JexlContext jexlContext = new MapContext();
    private JexlEngine jexlEngine = new JexlBuilder().create();
    private JxltEngine jxltEngine = jexlEngine.createJxltEngine();

    @Override
    public FieldPairItem intercept(Chain<FieldPairItem> chain) throws Exception {
        FieldPairItem item = chain.getPair();
        Object entity = item.getEntity();
        if (entity == null) {
            return chain.handle(item);
        }
        String entityClazz = entity.getClass().getSimpleName().toLowerCase(Locale.getDefault());
        this.jexlContext.set(entityClazz, entity);
        String exp = item.getExp();
        if (TextUtils.isEmpty(exp)) {
            return chain.handle(item);
        }
        exp = exp.replaceFirst("\\$\\{", "\\$\\{" + entityClazz + ".");
        JxltEngine.Expression expression = jxltEngine.createExpression(exp);
        Object newValue = expression.evaluate(jexlContext);
        if (newValue != null) {
            item.setText(newValue);
        }
        return chain.handle(item);
    }
}
