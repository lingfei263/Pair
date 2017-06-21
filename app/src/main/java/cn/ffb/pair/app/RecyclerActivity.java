package cn.ffb.pair.app;

import android.content.Context;
import android.os.Bundle;

import cn.ffb.pair.action.PairActivityAction;
import cn.ffb.pair.action.PairDialogAction;
import cn.ffb.pair.action.PairPreferenceAction;
import cn.ffb.pair.interceptor.DateFormatInterceptor;
import cn.ffb.pair.item.Interceptor;
import cn.ffb.pair.item.PairFactory;
import cn.ffb.pair.item.PairGroup;
import cn.ffb.pair.item.PairManager;
import cn.ffb.pair.item.TextPairItem;
import cn.ffb.pair.ui.PairView;
import cn.ffb.base.BaseActivity;
import cn.ffb.dialog.core.DialogBuilder;
import cn.ffb.dialog.core.IDialog;

/**
 * Created by lingfei on 2017/6/21.
 */
public class RecyclerActivity extends BaseActivity {

    @Override
    protected void onConfig(Config config) {
        super.onConfig(config);
    }

    public static class User {
        public String username;
        public String password;
    }

    private User mUser;

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);

        mUser = new User();
        mUser.password = "123456";
        mUser.username = "694551594";

        PairView pairView = this.getView(R.id.pairview);

        PairManager.create(this, new PairFactory(this) {
            @Override
            protected PairGroup onCreatePairGroup(Context context) {
                this.newCatalog().setTitle("我是一组有图标的");
                this.newTextItem()
                        .setIcon(R.drawable.find_more_friend_scan)
                        .setKey("扫一扫");
                this.newTextItem()
                        .setVisible(false)
                        .setIcon(R.drawable.find_more_friend_photograph_icon)
                        .setKey("朋友圈");
                this.newTextItem()
                        .setIcon(R.drawable.find_more_friend_shake)
                        .setKey("摇一摇");

                this.newCatalog().setTitle("拦截器测试");
                this.newTextItem()
                        .setIcon(R.drawable.find_more_friend_photograph_icon)
                        .setText("朋友圈")
                        .setKey("拦截器设置的文本")
                        .addInterceptor(new Interceptor<TextPairItem>() {
                            @Override
                            public TextPairItem intercept(Chain<TextPairItem> chain) throws Exception {
                                chain.getPair().setText("我是拦截器设置的文本");
                                return chain.handle(chain.getPair());
                            }
                        }).setDescription("之前的文本为：朋友圈");
                this.newTextItem()
                        .setText("对话框")
                        .setKey("对话框")
                        .setDescription("点我可以打开对话框")
                        .setAction(new PairDialogAction(getContext()) {

                            @Override
                            protected IDialog onCreateDialog(Context context) {
                                return DialogBuilder
                                        .messageDialog(context)
                                        .setMessage("我是对话框哦")
                                        .create();
                            }
                        });
                this.newTextItem()
                        .setText("进入新的activity")
                        .setKey("activity")
                        .setAction(new PairActivityAction(getContext(), RecyclerActivity.class));

                this.newCatalog().setTitle("Preference测试");
                this.newSwitchItem()
                        .setKey("测试Preference")
                        .setAction(new PairPreferenceAction("test"));
                this.newSwitchItem()
                        .setChecked(true)
                        .setKey("开关");
                this.newCheckboxItem()
                        .setChecked(false)
                        .setKey("选择框");

                this.newCatalog().setTitle("没图标的");
                this.newTextItem()
                        .setKey("没有图标")
                        .setText("没有图标的文本哦").setEnable(false);
                this.newTextItem()
                        .setKey("这是一个日期格式化的例子")
                        .addInterceptor(new DateFormatInterceptor<TextPairItem>())
                        .setText(System.currentTimeMillis());
                this.newImageItem().setKey("后面是一张图片")
                        .setResId(R.drawable.ic_discovery_templet_shop);

                this.newCatalog().setTitle("这是一个数据绑定的例子");
                this.newFieldItem()
                        .setKey("用户名")
                        .setEntity(mUser)
                        .setExp("${username}");
                this.newFieldItem()
                        .setExp("${password}")
                        .setEntity(mUser)
                        .setKey("密码");

                return super.onCreatePairGroup(context);
            }
        }).attach(pairView);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.pair_layout;
    }
}
