package cn.ffb.pair.item;


/**
 * Created by lingfei on 2017/6/11.
 */

public abstract class OnPairCreateListener {

    public abstract void onCreate(int id, IPair pair);

    protected <T extends IPair> T getPair(IPair pair) {
        return (T) pair;
    }

    protected TextPairItem getTextPairItem(IPair pair) {
        return (TextPairItem) pair;
    }

    protected ImagePairItem getImagePairItem(IPair pair) {
        return (ImagePairItem) pair;
    }

    protected FieldPairItem getFieldPairItem(IPair pair) {
        return (FieldPairItem) pair;
    }

    protected SwitchPairItem getSwitchPairItem(IPair pair) {
        return (SwitchPairItem) pair;
    }

    protected CheckPairItem getCheckPairItem(IPair pair) {
        return (CheckPairItem) pair;
    }

    protected ButtonPairItem getButtonPairItem(IPair pair) {
        return (ButtonPairItem) pair;
    }

    protected EditTextPairItem getEditTextPairItem(IPair pair) {
        return (EditTextPairItem) pair;
    }
}
