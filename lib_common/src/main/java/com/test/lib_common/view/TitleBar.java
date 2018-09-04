package com.test.lib_common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.lib_common.R;
import com.test.lib_common.base.BaseMvpActivity;


public class TitleBar extends LinearLayout {

    private LayoutInflater mInflater;

    private View mView;
    private TextView mTvTitle;
    private TextView mTvRight;
    private TextView mTvLeft;
    private TextView mTvTwoLeft;
    private View mLine;

    private Context mContext;

    private int background = 0XFFFFFFFF;
    private int textColor = 0XFF1E1E1E;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        //setContentInsetsRelative(10, 10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.TitleBar, defStyleAttr, 0);

            final Drawable leftIcon = a.getDrawable(R.styleable.TitleBar_leftIcon);
            final Drawable leftTwoIcon = a.getDrawable(R.styleable.TitleBar_leftTwoIcon);
            if (leftIcon != null) {
                setLeftIcon(leftIcon);
                mTvLeft.setVisibility(VISIBLE);
            }

            if (leftTwoIcon != null) {
                setLeftTwoIcon(leftTwoIcon);
                mTvTwoLeft.setVisibility(VISIBLE);
            }

            CharSequence leftText = a.getText(R.styleable.TitleBar_leftText);
            if (leftText != null) {
                setLeftText(leftText);
                mTvLeft.setVisibility(VISIBLE);
            }

            int leftTextColor = a.getColor(R.styleable.TitleBar_leftTextColor,
                    textColor);
            setLeftTextColor(leftTextColor);

            CharSequence title = a.getText(R.styleable.TitleBar_title);
            if (title != null) {
                setTitle(title);
            }

            int titleColor = a.getColor(R.styleable.TitleBar_TitleColor,
                    textColor);
            setTitleColor(titleColor);

            final Drawable rightIcon = a.getDrawable(R.styleable.TitleBar_rightIcon);
            if (rightIcon != null) {
                setRightIcon(rightIcon);
                mTvRight.setVisibility(VISIBLE);
            }

            CharSequence rightButtonText = a.getText(R.styleable.TitleBar_rightText);
            if (rightButtonText != null) {
                setRightText(rightButtonText);
                mTvRight.setVisibility(VISIBLE);
            }

            int rightTextColor = a.getColor(R.styleable.TitleBar_leftTextColor,
                    textColor);
            setRightTextColor(rightTextColor);

            background = a.getColor(R.styleable.TitleBar_titleBackground, background);
            setBackground(background);

            setHideLine(a.getInt(R.styleable.TitleBar_hideLine, 0));

            a.recycle();
        }

    }


    private void initView() {

        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.widget_title_bar, null);

            mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
            mTvRight = (TextView) mView.findViewById(R.id.tv_right);
            mTvLeft = (TextView) mView.findViewById(R.id.tv_left);
            mTvTwoLeft = (TextView) mView.findViewById(R.id.tv_twoleft);

            mLine = mView.findViewById(R.id.v_line);

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            addView(mView, lp);

            mTvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        ((BaseMvpActivity) mContext).onBackPressed();
                    } catch (Exception e) {
                        Log.i("TitleBar",e.getMessage());
                    }


                }
            });
        }

    }

    public void setRightIcon(Drawable icon) {
        if (mTvRight != null) {
            mTvRight.setBackgroundDrawable(icon);
            mTvRight.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftIcon(Drawable icon) {
        if (mTvLeft != null) {
//            mTvLeft.setBackground(icon);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            mTvLeft.setCompoundDrawables(icon, null, null, null);
            mTvLeft.setVisibility(VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftTwoIcon(Drawable icon) {
        if (mTvTwoLeft != null) {
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            mTvTwoLeft.setCompoundDrawables(icon, null, null, null);
            mTvTwoLeft.setVisibility(VISIBLE);
        }
    }

    public void setTitle(CharSequence text) {
        if (!isInEditMode()) {

            //do something
            //造成 error code
        }

        if (mTvTitle != null) {
            mTvTitle.setText(text);
            mTvTitle.setVisibility(VISIBLE);
        }
    }

    private void setHideLine(int visibility) {
        mLine.setVisibility(visibility);
    }

    private void setBackground(int background) {
        mView.setBackgroundColor(background);
    }

    public void setTitleColor(int textColor) {
        mTvTitle.setTextColor(textColor);
    }

    public void setLeftTextColor(int textColor) {
        mTvLeft.setTextColor(textColor);
    }

    public void setRightTextColor(int textColor) {
        mTvRight.setTextColor(textColor);
    }

    public void setRightIcon(int icon) {
        setRightIcon(getResources().getDrawable(icon));
    }

    public void setRightOnClickListener(OnClickListener li) {
        mTvRight.setOnClickListener(li);
    }

    public void setLeftOnClickListener(OnClickListener li) {
        mTvLeft.setOnClickListener(li);
    }

    public void setLeftTwoOnClickListener(OnClickListener li) {
        mTvTwoLeft.setOnClickListener(li);
    }

    public void setLeftText(CharSequence text) {
        mTvLeft.setText(text);
        mTvLeft.setVisibility(VISIBLE);
    }

    public void setRightText(CharSequence text) {
        mTvRight.setText(text);
        mTvRight.setVisibility(VISIBLE);
    }

    public void setRightText(int id) {
        setRightText(getResources().getString(id));
    }

    public TextView getRightButton() {
        return this.mTvRight;
    }

}
