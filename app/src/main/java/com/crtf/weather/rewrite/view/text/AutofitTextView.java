package com.crtf.weather.rewrite.view.text;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 自动调整 TextView
 */
@Deprecated
public class AutofitTextView extends android.widget.TextView implements AutofitHelper.OnTextSizeChangeListener{


    private AutofitHelper mHelper;

    public AutofitTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AutofitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AutofitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mHelper = AutofitHelper.create(this, attrs, defStyle)
                .addOnTextSizeChangeListener(this);
    }

    // Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (mHelper != null) {
            mHelper.setTextSize(unit, size);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLines(int lines) {
        super.setLines(lines);
        if (mHelper != null) {
            mHelper.setMaxLines(lines);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        if (mHelper != null) {
            mHelper.setMaxLines(maxLines);
        }
    }

    /**
     * 返回该视图的{@link AutofitHelper}
     */
    public AutofitHelper getAutofitHelper() {
        return mHelper;
    }

    /**
     * 返回文本是否会自动调整大小以适应其约束。
     */
    public boolean isSizeToFit() {
        return mHelper.isEnabled();
    }

    /**
     * 设置这个字段的属性(sizeToFit)，自动调整文本大小以适应它的约束。
     */
    public void setSizeToFit() {
        setSizeToFit(true);
    }

    /**
     * 如果为true，文本将自动调整大小以适应其约束;如果为false，它将像一个正常的TextView。
     *
     * @param sizeToFit
     */
    public void setSizeToFit(boolean sizeToFit) {
        mHelper.setEnabled(sizeToFit);
    }

    /**
     * 返回此视图中文本的最大大小(以像素为单位)。
     */
    public float getMaxTextSize() {
        return mHelper.getMaxTextSize();
    }

    /**
     * 将最大文本大小设置为给定值，解释为“缩放像素”单位。该大小根据当前密度和用户字体大小首选项进行调整。
     *
     * @param size 缩放像素大小。
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(float size) {
        mHelper.setMaxTextSize(size);
    }

    /**
     * 将最大文本大小设置为给定的单位和值。请参阅TypedValue获取可能的维度单位。
     *
     * @param unit 所需的尺寸单位。
     * @param size 在给定单位内所期望的尺寸。
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(int unit, float size) {
        mHelper.setMaxTextSize(unit, size);
    }

    /**
     * 返回此视图中文本的最小大小(以像素为单位)。
     */
    public float getMinTextSize() {
        return mHelper.getMinTextSize();
    }

    /**
     * 将最小文本大小设置为给定值，解释为“缩放像素”单位。该大小根据当前密度和用户字体大小首选项进行调整。
     *
     * @param minSize 缩放后的像素大小。
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    /**
     * 将最小文本大小设置为给定的单位和值。请参阅TypedValue获取可能的维度单位。
     *
     * @param unit 在给定单位内所期望的尺寸。
     * @param minSize 在给定单位内所期望的尺寸。
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int unit, float minSize) {
        mHelper.setMinTextSize(unit, minSize);
    }

    /**
     * 返回用于计算适合于其范围的正确文本大小的精度。
     */
    public float getPrecision() {
        return mHelper.getPrecision();
    }

    /**
     * 设置用于计算正确文本大小的精度，以适应其范围。精度越低，精度越高，需要的时间也就越多。
     *
     * @param precision 精确的数量
     */
    public void setPrecision(float precision) {
        mHelper.setPrecision(precision);
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
        // 什么也不做
//        setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }
}
