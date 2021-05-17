package com.crtf.weather.rewrite.view.text;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.crtf.weather.R;
import com.crtf.weather.util.log.Log;

import java.util.ArrayList;

/**
 * 一个帮助类可以自动调整{@link TextView}的{@code textSize}的大小以适应*在其范围内。
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
@Deprecated
public class AutofitHelper {

    private static final String TAG = "AutoFitTextHelper";
    private static final boolean SPEW = false;

    // 以像素为单位的最小文本大小
    private static final int DEFAULT_MIN_TEXT_SIZE = 20; //sp
    // 当达到目标textWidth尺寸时，我们希望有多精确
    private static final float DEFAULT_PRECISION = 0.5f;

    /**
     * 创建一个新实例{@code autofilper}，包装一个{@link TextView}，并允许自动调整文本大小以适应。
     */
    public static AutofitHelper create(TextView view) {
        return create(view, null, 0);
    }

    /**
     * 创建一个新实例{@code autofilper}，包装一个{@link TextView}，并允许自动调整文本大小以适应。
     */
    public static AutofitHelper create(TextView view, AttributeSet attrs) {
        return create(view, attrs, 0);
    }

    /**
     * 创建一个新实例{@code autofilper}，包装一个{@link TextView}，并允许*自动调整文本大小以适应。
     */
    public static AutofitHelper create(TextView view, AttributeSet attrs, int defStyle) {
        AutofitHelper helper = new AutofitHelper(view);
        boolean sizeToFit = true;
        if (attrs != null) {
            Context context = view.getContext();
            int minTextSize = (int) helper.getMinTextSize();
            float precision = helper.getPrecision();

            TypedArray ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutofitTextView,
                    defStyle,
                    0);
            sizeToFit = ta.getBoolean(R.styleable.AutofitTextView_sizeToFit, sizeToFit);
            minTextSize = ta.getDimensionPixelSize(R.styleable.AutofitTextView_minTextSize,
                    minTextSize);
            precision = ta.getFloat(R.styleable.AutofitTextView_precision, precision);
            ta.recycle();

            helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize)
                    .setPrecision(precision);
        }
        helper.setEnabled(sizeToFit);

        return helper;
    }

    /**
     * 重新大小的TextView的textSize，使文本适合在视图的边界。
     */
    private static void autofit(TextView view, TextPaint paint, float minTextSize, float maxTextSize,
                                int maxLines, float precision) {
        if (maxLines <= 0 || maxLines == Integer.MAX_VALUE) {
            // Don't auto-size since there's no limit on lines.
            return;
        }

        int targetWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        if (targetWidth <= 0) {
            return;
        }

        CharSequence text = view.getText();
        TransformationMethod method = view.getTransformationMethod();
        if (method != null) {
            text = method.getTransformation(text, view);
        }

        Context context = view.getContext();
        Resources r = Resources.getSystem();
        DisplayMetrics displayMetrics;

        float size = maxTextSize;
        float high = size;
        float low = 0;

        if (context != null) {
            r = context.getResources();
        }
        displayMetrics = r.getDisplayMetrics();

        paint.set(view.getPaint());
        paint.setTextSize(size);

        if ((maxLines == 1 && paint.measureText(text, 0, text.length()) > targetWidth)
                || getLineCount(text, paint, size, targetWidth, displayMetrics) > maxLines) {
            size = getAutofitTextSize(text, paint, targetWidth, maxLines, low, high, precision,
                    displayMetrics);
        }

        if (!(getLineCount(text, paint, size, targetWidth, displayMetrics) > maxLines)) {
            // TODO: 赤耳听风   2021年4月27日 星期二 0:30
            float abstractVertical = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
            float tempSize = abstractVertical / maxLines;
//            size = tempSize;
//            getAutofitTextSizeVertical()
            size = getAutofitTextSize(text,paint,targetWidth,maxLines,low,tempSize,precision,displayMetrics);

            size = (float) (size * 0.8);
        }

        if (size < minTextSize) {
            size = minTextSize;
        }

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    private static float getAutofitTextSizeVertical(CharSequence text, TextPaint paint, float size, float width,
                                                    DisplayMetrics displayMetrics) {
        return 0;
    }

    /**
     * 递归二分查找，以找到文本的最佳大小。
     */
    private static float getAutofitTextSize(CharSequence text, TextPaint paint,
                                            float targetWidth, int maxLines, float low, float high, float precision,
                                            DisplayMetrics displayMetrics) {
        float mid = (low + high) / 2.0f;
        int lineCount = 1;
        StaticLayout layout = null;

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid,
                displayMetrics));

        if (maxLines != 1) {
            layout = new StaticLayout(text, paint, (int) targetWidth, Layout.Alignment.ALIGN_NORMAL,
                    1.0f, 0.0f, true);
            lineCount = layout.getLineCount();
        }

        if (SPEW) Log.d(TAG, "low=" + low + " high=" + high + " mid=" + mid +
                " target=" + targetWidth + " maxLines=" + maxLines + " lineCount=" + lineCount);

        if (lineCount > maxLines) {
            // 对于' text '比' maxLines '有更多换行符的情况。
            if ((high - low) < precision) {
                return low;
            }
            return getAutofitTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                    displayMetrics);
        } else if (lineCount < maxLines) {
            return getAutofitTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                    displayMetrics);
        } else {
            float maxLineWidth = 0;
            if (maxLines == 1) {
                maxLineWidth = paint.measureText(text, 0, text.length());
            } else {
                for (int i = 0; i < lineCount; i++) {
                    if (layout.getLineWidth(i) > maxLineWidth) {
                        maxLineWidth = layout.getLineWidth(i);
                    }
                }
            }

            if ((high - low) < precision) {
                return low;
            } else if (maxLineWidth > targetWidth) {
                return getAutofitTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                        displayMetrics);
            } else if (maxLineWidth < targetWidth) {
                return getAutofitTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                        displayMetrics);
            } else {
                return mid;
            }
        }
    }

    private static int getLineCount(CharSequence text, TextPaint paint, float size, float width,
                                    DisplayMetrics displayMetrics) {
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size,
                displayMetrics));
        StaticLayout layout = new StaticLayout(text, paint, (int) width,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return layout.getLineCount();
    }

    private static int getMaxLines(TextView view) {
        int maxLines = -1; // 没有限制(整数。MAX VALUE也意味着没有限制)

        TransformationMethod method = view.getTransformationMethod();
        if (method != null && method instanceof SingleLineTransformationMethod) {
            maxLines = 1;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // setMaxLines()和getMaxLines()只适用于android-16+
            maxLines = view.getMaxLines();
        }

        return maxLines;
    }

    // 属性
    private TextView mTextView;
    private TextPaint mPaint;
    /**
     * TextView的原始textSize。
     */
    private float mTextSize;

    private int mMaxLines;
    private float mMinTextSize;
    private float mMaxTextSize;
    private float mPrecision;

    private boolean mEnabled;
    private boolean mIsAutofitting;

    private ArrayList<OnTextSizeChangeListener> mListeners;

    private TextWatcher mTextWatcher = new AutofitTextWatcher();

    private View.OnLayoutChangeListener mOnLayoutChangeListener =
            new AutofitOnLayoutChangeListener();

    private AutofitHelper(TextView view) {
        final Context context = view.getContext();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;

        mTextView = view;
        mPaint = new TextPaint();
        setRawTextSize(view.getTextSize());

        mMaxLines = getMaxLines(view);
        mMinTextSize = scaledDensity * DEFAULT_MIN_TEXT_SIZE;
        mMaxTextSize = mTextSize;
        mPrecision = DEFAULT_PRECISION;
    }

    /**
     * 当{@link TextView}的{@code textSize}改变时，其方法被调用*的列表中添加一个{@link OnTextSizeChangeListener}。
     */
    public AutofitHelper addOnTextSizeChangeListener(OnTextSizeChangeListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<OnTextSizeChangeListener>();
        }
        mListeners.add(listener);
        return this;
    }

    /**
     * 当{@link TextView}的{@code textSize}改变时，这些方法*会被调用，从列表中删除指定的{@link OnTextSizeChangeListener}。
     */
    public AutofitHelper removeOnTextSizeChangeListener(OnTextSizeChangeListener listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
        return this;
    }

    /**
     * 返回用于计算适合于其*范围的正确文本大小的精度。
     */
    public float getPrecision() {
        return mPrecision;
    }

    /**
     * 设置用于计算正确文本大小的精度，以适应其*范围。精度越低，精度越高，需要的时间也就越多。
     *
     * @param precision 精确的数量。
     */
    public AutofitHelper setPrecision(float precision) {
        if (mPrecision != precision) {
            mPrecision = precision;

            autofit();
        }
        return this;
    }

    /**
     * 返回文本的最小大小(以像素为单位)。
     */
    public float getMinTextSize() {
        return mMinTextSize;
    }

    /**
     * 将最小文本大小设置为给定值，解释为“缩放像素”单位。该大小*根据当前密度和用户字体大小首选项进行调整。
     *
     * @param size size缩放像素大小。
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public AutofitHelper setMinTextSize(float size) {
        return setMinTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 将最小文本大小设置为给定的单位和值。请参阅TypedValue获取可能的*维度单位。
     *
     * @param unit 所需的尺寸单位。
     * @param size 在给定单位内所期望的尺寸。
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public AutofitHelper setMinTextSize(int unit, float size) {
        Context context = mTextView.getContext();
        Resources r = Resources.getSystem();

        if (context != null) {
            r = context.getResources();
        }

        setRawMinTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()));
        return this;
    }

    private void setRawMinTextSize(float size) {
        if (size != mMinTextSize) {
            mMinTextSize = size;

            autofit();
        }
    }

    /**
     * 返回文本的最大大小(以像素为单位)。
     */
    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    /**
     * 将最大文本大小设置为给定值，解释为“缩放像素”单位。该大小*根据当前密度和用户字体大小首选项进行调整。
     *
     * @param size 缩放后的像素大小。
     * @attr ref android.R.styleable#TextView_textSize
     */
    public AutofitHelper setMaxTextSize(float size) {
        return setMaxTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 将最大文本大小设置为给定的单位和值。请参阅TypedValue获取可能的*维度单位。
     *
     * @param unit 所需的尺寸单位。
     * @param size 在给定单位内所期望的尺寸。
     * @attr ref android.R.styleable#TextView_textSize
     */
    public AutofitHelper setMaxTextSize(int unit, float size) {
        Context context = mTextView.getContext();
        Resources r = Resources.getSystem();

        if (context != null) {
            r = context.getResources();
        }

        setRawMaxTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()));
        return this;
    }

    private void setRawMaxTextSize(float size) {
        if (size != mMaxTextSize) {
            mMaxTextSize = size;

            autofit();
        }
    }

    /**
     * @see TextView#getMaxLines()
     */
    public int getMaxLines() {
        return mMaxLines;
    }

    /**
     * @see TextView#setMaxLines(int)
     */
    public AutofitHelper setMaxLines(int lines) {
        if (mMaxLines != lines) {
            mMaxLines = lines;

            autofit();
        }
        return this;
    }

    /**
     * 返回是否自动调整文本大小。
     */
    public boolean isEnabled() {
        return mEnabled;
    }

    /**
     * 设置自动调整文本大小的启用状态。
     */
    public AutofitHelper setEnabled(boolean enabled) {
        if (mEnabled != enabled) {
            mEnabled = enabled;

            if (enabled) {
                mTextView.addTextChangedListener(mTextWatcher);
                mTextView.addOnLayoutChangeListener(mOnLayoutChangeListener);

                autofit();
            } else {
                mTextView.removeTextChangedListener(mTextWatcher);
                mTextView.removeOnLayoutChangeListener(mOnLayoutChangeListener);

                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            }
        }
        return this;
    }

    /**
     * 返回视图的原始文本大小。
     *
     * @see TextView#getTextSize()
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * 返回视图的原始文本大小。
     *
     * @see TextView#setTextSize(float)
     */
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置视图的原始文本大小。
     *
     * @see TextView#setTextSize(int, float)
     */
    public void setTextSize(int unit, float size) {
        if (mIsAutofitting) {
            // 我们不希望在自动调整时更新TextView的实际textSize
            // 因为它将被设置为autofitTextSize
            return;
        }
        Context context = mTextView.getContext();
        Resources r = Resources.getSystem();

        if (context != null) {
            r = context.getResources();
        }

        setRawTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()));
    }

    private void setRawTextSize(float size) {
        if (mTextSize != size) {
            mTextSize = size;
        }
    }

    private void autofit() {
        float oldTextSize = mTextView.getTextSize();
        float textSize;

        mIsAutofitting = true;
        autofit(mTextView, mPaint, mMinTextSize, mMaxTextSize, mMaxLines, mPrecision);
        mIsAutofitting = false;

        textSize = mTextView.getTextSize();
        if (textSize != oldTextSize) {
            sendTextSizeChange(textSize, oldTextSize);
        }
    }

    private void sendTextSizeChange(float textSize, float oldTextSize) {
        if (mListeners == null) {
            return;
        }

        for (OnTextSizeChangeListener listener : mListeners) {
            listener.onTextSizeChange(textSize, oldTextSize);
        }
    }

    private class AutofitTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            autofit();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class AutofitOnLayoutChangeListener implements View.OnLayoutChangeListener {
        @Override
        public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            autofit();
        }
    }

    /**
     * 当一个类型的对象被附加到{@code autofilper}时，当{@code textSize}被更改时，它的方法将被调用*。
     */
    public interface OnTextSizeChangeListener {
        /**
         * 调用此方法来通知您文本的大小已更改为
         * {@code textSize} from {@code oldTextSize}.
         */
        public void onTextSizeChange(float textSize, float oldTextSize);
    }
}
