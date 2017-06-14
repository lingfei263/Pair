package cn.ffb.pair.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cn.ffb.adapter.recycler.ViewHolder;
import cn.ffb.pair.R;


/**
 * Created by lingfei on 2017/6/11.
 */

public class EditTextPairItem extends PairItem<EditTextPairItem> {
    private String text;
    private String hint;

    private EditText mEditText;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            text = s.toString();
        }
    };

    public EditTextPairItem(Context context) {
        this(context, null);
    }

    public EditTextPairItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.EdiTextPairItem);

        this.text = a.getString(R.styleable.BaseTextPairItem_text);
        this.hint = a.getString(R.styleable.EdiTextPairItem_hint);

        a.recycle();
    }

    public String getText() {
        return text;
    }

    public String getHint() {
        return hint;
    }

    public EditTextPairItem setHint(String hint) {
        this.hint = hint;
        this.notifyChange();
        return this;
    }

    public EditTextPairItem setText(String text) {
        this.text = text;
        this.notifyChange();
        return this;
    }

    public EditTextPairItem setText(Object text) {
        this.text = text.toString();
        return setText(this.text);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        super.onBindViewHolder(viewHolder);
        String value = this.getText();
        viewHolder.bindResId(R.id.text)
                .setText(TextUtils.isEmpty(value) ? "" : Html.fromHtml(value));

        mEditText = viewHolder.getView(R.id.text);
        mEditText.setHint(getHint());
        mEditText.addTextChangedListener(mTextWatcher);
    }

    @Override
    public int getWidgetLayoutResource() {
        return R.layout.pair_widget_edittext;
    }

    @Override
    public boolean performClick() {
        boolean flag = super.performClick();
        if (!flag) {
            if (mEditText != null) {
                mEditText.requestFocus();
                mEditText.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager mInputManager = (InputMethodManager) mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mInputManager.showSoftInput(mEditText, 0);
                    }
                });
            }
        }
        return flag;
    }
}
