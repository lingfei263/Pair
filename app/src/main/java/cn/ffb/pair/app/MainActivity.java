package cn.ffb.pair.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.ffb.base.BaseActivity;
/**
 * Created by lingfei on 2017/6/21.
 */
public class MainActivity extends BaseActivity {

    static {
        CustomActivityOnCrash.setShowErrorDetails(true);
    }

    @Override
    protected void onConfig(Config config) {
        super.onConfig(config);
        config.setSwipeBackWrapper(false);
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        Button btn1 = this.getView(R.id.button);
        Button btn2 = this.getView(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RecyclerActivity.class);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PairLayoutActivity.class);
            }
        });
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }
}
