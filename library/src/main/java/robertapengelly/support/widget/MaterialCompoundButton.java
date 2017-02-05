package robertapengelly.support.widget;

import  android.content.Context;
import  android.content.res.ColorStateList;
import  android.content.res.TypedArray;
import  android.graphics.Canvas;
import  android.graphics.PorterDuff;
import  android.graphics.drawable.Drawable;
import  android.os.Build;
import  android.os.Parcel;
import  android.os.Parcelable;
import  android.util.AttributeSet;
import  android.view.Gravity;
import  android.view.accessibility.AccessibilityEvent;
import  android.view.accessibility.AccessibilityNodeInfo;
import  android.widget.Checkable;

import  robertapengelly.support.graphics.drawable.LollipopDrawable;
import  robertapengelly.support.graphics.drawable.LollipopDrawablesCompat;
import  robertapengelly.support.materialcompoundbutton.R;

public abstract class MaterialCompoundButton extends MaterialTextView implements Checkable {

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
    
    private boolean mBroadcasting, mChecked;
    private boolean mHasButtonTint = false, mHasButtonTintMode = false;
    
    private int mButtonResource;
    
    private Drawable mButtonDrawable;
    
    private OnCheckedChangeListener mOnCheckedChangeListener;
    
    private ColorStateList mButtonTintList;
    private PorterDuff.Mode mButtonTintMode;
    
    public MaterialCompoundButton(Context context) {
        this(context, null);
    }
    
    public MaterialCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public MaterialCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialCompoundButton, defStyleAttr, 0);
        
        final Drawable d;
        
        if (a.hasValue(R.styleable.MaterialCompoundButton_android_button))
            d = LollipopDrawablesCompat.getDrawable(context, a.getResourceId(R.styleable.MaterialCompoundButton_android_button, -1));
        else
            d = null;
        
        if (d != null)
            setButtonDrawable(d);
        
        if (a.hasValue(R.styleable.MaterialCompoundButton_buttonTint)) {
        
            mButtonTintList = a.getColorStateList(R.styleable.MaterialCompoundButton_buttonTint);
            mHasButtonTint = true;
        
        }
        
        if (a.hasValue(R.styleable.MaterialCompoundButton_buttonTintMode)) {
        
            mButtonTintMode = LollipopDrawable.parseTintMode(a.getInt(
                R.styleable.MaterialCompoundButton_buttonTintMode, -1), mButtonTintMode);
            
            mHasButtonTintMode = true;
        
        }
        
        setChecked(a.getBoolean(R.styleable.MaterialCompoundButton_android_checked, false));
        
        a.recycle();
        
        applyButtonTint();
    
    }
    
    private void applyButtonTint() {
    
        if ((mButtonDrawable != null) && (mHasButtonTint || mHasButtonTintMode)) {
        
            mButtonDrawable = mButtonDrawable.mutate();
            
            if (mHasButtonTint) {
            
                if (mButtonDrawable instanceof LollipopDrawable)
                    //noinspection RedundantCast
                    ((LollipopDrawable) mButtonDrawable).setTintList(mButtonTintList);
                else if (Build.VERSION.SDK_INT >= 21)
                    mButtonDrawable.setTintList(mButtonTintList);
            
            }
            
            if (mHasButtonTintMode) {
            
                if (mButtonDrawable instanceof LollipopDrawable)
                    //noinspection RedundantCast
                    ((LollipopDrawable) mButtonDrawable).setTintMode(mButtonTintMode);
                else if (Build.VERSION.SDK_INT >= 21)
                    mButtonDrawable.setTintMode(mButtonTintMode);
            
            }
        
        }
    
    }
    
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        
        if (mButtonDrawable != null) {
        
            if (mButtonDrawable instanceof LollipopDrawable)
                //noinspection RedundantCast
                ((LollipopDrawable) mButtonDrawable).setHotspot(x, y);
            else if (Build.VERSION.SDK_INT >= 21)
                mButtonDrawable.setHotspot(x, y);
        
        }
    
    }
    
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        
        if (mButtonDrawable != null) {
        
            int[] myDrawableState = getDrawableState();
            
            // Set the state of the Drawable
            mButtonDrawable.setState(myDrawableState);
            
            invalidate();
        
        }
    
    }
    
    /** @return the tint applied to the button drawable */
    public ColorStateList getButtonTintList() {
        return mButtonTintList;
    }
    
    /** @return the blending mode used to apply the tint to the button drawable */
    public PorterDuff.Mode getButtonTintMode() {
        return mButtonTintMode;
    }
    
    public int getHorizontalOffsetForDrawables() {
    
        final Drawable buttonDrawable = mButtonDrawable;
        return ((buttonDrawable != null) ? buttonDrawable.getIntrinsicWidth() : 0);
    
    }
    
    @Override
    public int getCompoundPaddingLeft() {
    
        int padding = super.getCompoundPaddingLeft();
        
        if (!isLayoutRtl()) {
        
            final Drawable buttonDrawable = mButtonDrawable;
            
            if (buttonDrawable != null)
                padding += buttonDrawable.getIntrinsicWidth();
        
        }
        
        return padding;
    
    }
    
    @Override
    public int getCompoundPaddingRight() {
    
        int padding = super.getCompoundPaddingRight();
        
        if (isLayoutRtl()) {
        
            final Drawable buttonDrawable = mButtonDrawable;
            
            if (buttonDrawable != null)
                padding += buttonDrawable.getIntrinsicWidth();
        
        }
        
        return padding;
    
    }
    
    @Override
    public boolean isChecked() {
        return mChecked;
    }
    
    public boolean isLayoutRtl() {
    
        //noinspection SimplifiableIfStatement
        if (Build.VERSION.SDK_INT >= 17)
            return (getLayoutDirection() == LAYOUT_DIRECTION_RTL);
        
        return false;
    
    }
    
    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        
        if (mButtonDrawable != null) {
        
            if (mButtonDrawable instanceof LollipopDrawable)
                //noinspection RedundantCast
                ((LollipopDrawable) mButtonDrawable).jumpToCurrentState();
            else if (Build.VERSION.SDK_INT >= 21)
                mButtonDrawable.jumpToCurrentState();
        
        }
    
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
    
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        
        return drawableState;
    
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    
        final Drawable buttonDrawable = mButtonDrawable;
        
        if (buttonDrawable != null) {
        
            final int verticalGravity = (getGravity() & Gravity.VERTICAL_GRAVITY_MASK);
            
            final int drawableHeight = buttonDrawable.getIntrinsicHeight();
            final int drawableWidth = buttonDrawable.getIntrinsicWidth();
            
            int top;
            
            switch (verticalGravity) {
            
                case Gravity.BOTTOM:
                    top = getHeight() - drawableHeight;
                    break;
                case Gravity.CENTER_VERTICAL:
                    top = (getHeight() - drawableHeight) / 2;
                    break;
                default:
                    top = 0;
            
            }
            
            int bottom = (top + drawableHeight);
            int left = (isLayoutRtl() ? (getWidth() - drawableWidth) : 0);
            int right = (isLayoutRtl() ? getWidth() : drawableWidth);
            
            buttonDrawable.setBounds(left, top, right, bottom);
            
            final Drawable background = getBackground();
            
            if (background != null) {
            
                if (Build.VERSION.SDK_INT < 21) {
                
                    int width = buttonDrawable.getIntrinsicWidth();
                    
                    left += (width / 4);
                    right -= (width / 4);
                
                }
                
                if (background instanceof LollipopDrawable)
                    //noinspection RedundantCast
                    ((LollipopDrawable) background).setHotspotBounds(left, top, right, bottom);
                else if (Build.VERSION.SDK_INT >= 21)
                    background.setHotspotBounds(left, top, right, bottom);
            
            }
        
        }
        
        super.onDraw(canvas);
        
        if (buttonDrawable != null) {
        
            final int scrollX = getScrollX();
            final int scrollY = getScrollY();
            
            if ((scrollX == 0) && (scrollY == 0))
                buttonDrawable.draw(canvas);
            else {
            
                canvas.translate(scrollX, scrollY);
                
                buttonDrawable.draw(canvas);
                
                canvas.translate(-scrollX, -scrollY);
            
            }
        
        }
    
    }
    
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        
        event.setClassName(MaterialCompoundButton.class.getName());
        event.setChecked(mChecked);
    
    }
    
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        
        if (Build.VERSION.SDK_INT < 14)
            return;
        
        info.setClassName(MaterialCompoundButton.class.getName());
        
        info.setCheckable(true);
        info.setChecked(mChecked);
    
    }
    
    @Override
    public void onRestoreInstanceState(Parcelable state) {
    
        SavedState ss = (SavedState) state;
        
        super.onRestoreInstanceState(ss.getSuperState());
        
        setChecked(ss.checked);
        requestLayout();
    
    }
    
    @Override
    public Parcelable onSaveInstanceState() {
    
        Parcelable superState = super.onSaveInstanceState();
        
        SavedState ss = new SavedState(superState);
        ss.checked = isChecked();
        
        return ss;
    
    }
    
    @Override
    public boolean performClick() {
    
        /*
         * XXX: These are tiny, need some surrounding 'expanded touch area',
         * which will need to be implemented in Button if we only override
         * performClick()
         */
        
        /* When clicked, toggle the state */
        toggle();
        return super.performClick();
    
    }
    
    /**
     * Set the button graphic to a given Drawable, identified by its resource id.
     *
     * @param resid the resource id of the drawable to use as the button graphic
     */
    public void setButtonDrawable(int resid) {
    
        if ((resid != 0) && (resid == mButtonResource))
            return;
        
        mButtonResource = resid;
        
        Drawable d = null;
        if (mButtonResource != 0)
            d = LollipopDrawablesCompat.getDrawable(getContext(), mButtonResource);
        
        setButtonDrawable(d);
    
    }
    
    /**
     * Set the button graphic to a given Drawable
     *
     * @param d The Drawable to use as the button graphic
     */
    public void setButtonDrawable(Drawable d) {
    
        if (mButtonDrawable != d) {
        
            if (mButtonDrawable != null) {
            
                mButtonDrawable.setCallback(null);
                unscheduleDrawable(mButtonDrawable);
            
            }
            
            mButtonDrawable = d;
            
            if (d != null) {
            
                d.setCallback(this);
                
                if (Build.VERSION.SDK_INT >= 23)
                    d.setLayoutDirection(getLayoutDirection());
                
                if (d.isStateful())
                    d.setState(getDrawableState());
                
                d.setVisible((getVisibility() == VISIBLE), false);
                
                setMinHeight(d.getIntrinsicHeight());
                //setMinWidth(d.getIntrinsicWidth() * 2);
                
                applyButtonTint();
            
            }
        
        }
    
    }
    
    /**
     * Applies a tint to the button drawable. Does not modify the current tint
     * mode, which is {@link PorterDuff.Mode#SRC_IN} by default.
     *
     * <p>Subsequent calls to {@link #setButtonDrawable(Drawable)} will
     * automatically mutate the drawable and apply the specified tint and tint
     * mode using {@link Drawable#setTintList(ColorStateList)}.</p>
     *
     * @param tint the tint to apply, may be {@code null} to clear tint
     */
    public void setButtonTintList(ColorStateList tint) {
    
        mButtonTintList = tint;
        mHasButtonTint = true;
        
        applyButtonTint();
    
    }
    
    /**
     * Specifies the blending mode used to apply the tint specified by
     * {@link #setButtonTintList(ColorStateList)}} to the button drawable. The
     * default mode is {@link PorterDuff.Mode#SRC_IN}.
     *
     * @param tintMode the blending mode used to apply the tint, may be
     *                 {@code null} to clear tint
     */
    public void setButtonTintMode(PorterDuff.Mode tintMode) {
    
        mButtonTintMode = tintMode;
        mHasButtonTintMode = true;
        
        applyButtonTint();
    
    }
    
    /**
     * <p>Changes the checked state of this button.</p>
     *
     * @param checked true to check the button, false to uncheck it
     */
    @Override
    public void setChecked(boolean checked) {
    
        if (mChecked != checked) {
        
            mChecked = checked;
            refreshDrawableState();
            
            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting)
                return;
            
            mBroadcasting = true;
            
            if (mOnCheckedChangeListener != null)
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            
            mBroadcasting = false;
        
        }
    
    }
    
    /**
     * Register a callback to be invoked when the checked state of this button changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
    
    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
    
    @Override
    protected boolean verifyDrawable(Drawable who) {
        return (super.verifyDrawable(who) || (who == mButtonDrawable));
    }
    
    static class SavedState extends BaseSavedState {
    
        boolean checked;
        
        /** Constructor called from {@link MaterialCompoundButton#onSaveInstanceState()} */
        SavedState(Parcelable superState) {
            super(superState);
        }
        
        @Override
        public String toString() {
            return "CompoundButton.SavedState{" + Integer.toHexString(System.identityHashCode(this))
                + " checked=" + checked + "}";
        }
        
        /** Constructor called from {@link #CREATOR} */
        private SavedState(Parcel in) {
            super(in);
            
            checked = (Boolean) in.readValue(null);
        
        }
        
        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            
            out.writeValue(checked);
        
        }
        
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        
        };
    
    }
    
    /**
     * Interface definition for a callback to be invoked when the checked state
     * of a compound button changed.
     */
    public interface OnCheckedChangeListener {
    
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(MaterialCompoundButton buttonView, boolean isChecked);
    
    }

}