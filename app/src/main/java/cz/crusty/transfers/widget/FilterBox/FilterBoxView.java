package cz.crusty.transfers.widget.FilterBox;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioGroup;

import cz.crusty.transfers.BuildConfig;
import cz.crusty.transfers.R;

/**
 *
 */
public class FilterBoxView extends ConstraintLayout {

    private static final String TAG = FilterBoxView.class.getSimpleName();

    private FilterBoxListener mListener;

    public FilterBoxView(Context context) {
        super(context);
        init(null, 0);
    }

    public FilterBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FilterBoxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FilterBox, defStyle, 0);


        a.recycle();

        LayoutInflater.from(getContext()).inflate(R.layout.view_filter_box, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(BuildConfig.DEBUG)
            Log.d(TAG, "onFinishInflate");

        EditText et = findViewById(R.id.search);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mListener.onTextChanged(s.toString());
            }
        });

        RadioGroup rg = findViewById(R.id.group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mListener.onTypeChanged(checkedId);
            }
        });
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        // TODO FilterBox saveInstanceState
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // TODO FilterBox restoreInstanceState
        super.onRestoreInstanceState(state);
    }

    public void setChangeListener(FilterBoxListener listener) {
        mListener = listener;
    }
}
