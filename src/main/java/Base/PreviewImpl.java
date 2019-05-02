package Base;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

public abstract class PreviewImpl {

    public
    interface Callback {
        void onSurfaceChanged();
    }

    private Callback mCallback;

    private int mWidth;
    private int mHeight;

    protected int mPreviewWidth;
    protected int mPreviewHeight;
    protected int mPreviewFormat;

    public
    void setCallback(Callback callback) {
        mCallback = callback;
    }

    public abstract
    Surface getSurface();

    abstract
    View getView();

    abstract Class getOutputClass();

    public abstract void setDisplayOrientation(int displayOrientation);

    public abstract boolean isReady();

    protected void dispatchSurfaceChanged() {
        mCallback.onSurfaceChanged();
    }

    public
    SurfaceHolder getSurfaceHolder() {
        return null;
    }

    SurfaceTexture getSurfaceTexture() {
        return null;
    }

    void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public
    int getWidth() {
        return mWidth;
    }

    public
    int getHeight() {
        return mHeight;
    }

    public abstract float getX();
    public abstract float getY();

    public
    void setPreviewParameters(final int width, final int height, final int format) {
        this.mPreviewWidth = width;
        this.mPreviewHeight = height;
        this.mPreviewFormat = format;
    }

    int getPreviewWidth() {
        return mPreviewWidth;
    }

    int getPreviewHeight() {
        return mPreviewHeight;
    }

    int getPreviewFormat() {
        return mPreviewFormat;
    }

}
