package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.DrawableWrapperApi14;
import android.util.Log;
import java.lang.reflect.Method;

@RequiresApi(21)
class DrawableWrapperApi21 extends DrawableWrapperApi19 {
    private static final String TAG = "DrawableWrapperApi21";
    private static Method sIsProjectedDrawableMethod;

    DrawableWrapperApi21(Drawable drawable) {
        super(drawable);
        findAndCacheIsProjectedDrawableMethod();
    }

    DrawableWrapperApi21(DrawableWrapperApi14.DrawableWrapperState state, Resources resources) {
        super(state, resources);
        findAndCacheIsProjectedDrawableMethod();
    }

    public void setHotspot(float x, float y) {
        this.mDrawable.setHotspot(x, y);
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        this.mDrawable.setHotspotBounds(left, top, right, bottom);
    }

    public void getOutline(Outline outline) {
        this.mDrawable.getOutline(outline);
    }

    public Rect getDirtyBounds() {
        return this.mDrawable.getDirtyBounds();
    }

    public void setTintList(ColorStateList tint) {
        if (isCompatTintEnabled()) {
            super.setTintList(tint);
        } else {
            this.mDrawable.setTintList(tint);
        }
    }

    public void setTint(int tintColor) {
        if (isCompatTintEnabled()) {
            super.setTint(tintColor);
        } else {
            this.mDrawable.setTint(tintColor);
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        if (isCompatTintEnabled()) {
            super.setTintMode(tintMode);
        } else {
            this.mDrawable.setTintMode(tintMode);
        }
    }

    public boolean setState(int[] stateSet) {
        if (!super.setState(stateSet)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isCompatTintEnabled() {
        if (Build.VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.mDrawable;
        if ((drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isProjected() {
        if (!(this.mDrawable == null || sIsProjectedDrawableMethod == null)) {
            try {
                return ((Boolean) sIsProjectedDrawableMethod.invoke(this.mDrawable, new Object[0])).booleanValue();
            } catch (Exception ex) {
                Log.w(TAG, "Error calling Drawable#isProjected() method", ex);
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public DrawableWrapperApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateLollipop(this.mState, (Resources) null);
    }

    private static class DrawableWrapperStateLollipop extends DrawableWrapperApi14.DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable DrawableWrapperApi14.DrawableWrapperState orig, @Nullable Resources res) {
            super(orig, res);
        }

        public Drawable newDrawable(@Nullable Resources res) {
            return new DrawableWrapperApi21(this, res);
        }
    }

    private void findAndCacheIsProjectedDrawableMethod() {
        if (sIsProjectedDrawableMethod == null) {
            try {
                sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception ex) {
                Log.w(TAG, "Failed to retrieve Drawable#isProjected() method", ex);
            }
        }
    }
}
