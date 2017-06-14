package cn.ffb.pair.item;


/**
 * Created by lingfei on 2017/6/11.
 */

public interface Interceptor<T extends IPair> {

    T intercept(Interceptor.Chain<T> chain) throws Exception;

    interface Chain<T extends IPair> {
        T getPair();

        T handle(T pair) throws Exception;

    }
}

