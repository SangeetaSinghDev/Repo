package Base;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.gohool.repo.R;


public class SurfaceViewPreview extends PreviewImpl {

    private Context mContext;
    private ViewGroup mParent;
    private SurfaceViewContainer mContainer;
    private SurfaceView mSurfaceView;

    private int mDisplayOrientation;

    public
    SurfaceViewPreview(final Context context, ViewGroup parent) {
        this.mContext = context;
        this.mParent = parent;

        final View view = View.inflate(context, R.layout.camera_surface_view, parent);
        mContainer = view.findViewById(R.id.surface_view_container);
        mContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                setSize(mContainer.getWidth(), mContainer.getHeight());
            }
        });


        mSurfaceView = mContainer.findViewById(R.id.surface_view);

        final SurfaceHolder holder = mSurfaceView.getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (isReady()) dispatchSurfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    @Override
    public
    SurfaceHolder getSurfaceHolder() {
        return mSurfaceView.getHolder();
    }

    @Override
    public
    Surface getSurface() {
        return getSurfaceHolder().getSurface();
    }

    @Override
    View getView() {
        return mContainer;
    }

    @Override
    Class getOutputClass() {
        return SurfaceHolder.class;
    }

    @Override
    public
    void setDisplayOrientation(int displayOrientation) {
        mDisplayOrientation = displayOrientation;
        mContainer.setDisplayOrientation(displayOrientation);
    }

    @Override
    public
    boolean isReady() {
        return getPreviewWidth() != 0 && getPreviewHeight() != 0;
    }

    @Override
    public
    float getX() {
        return mContainer.getChildAt(0).getX();
    }

    @Override
    public
    float getY() {
        return mContainer.getChildAt(0).getY();
    }

    @TargetApi(15)
    @Override
    public
    void setPreviewParameters(final int width, final int height, final int format) {
        super.setPreviewParameters(width, height, format);
        mContainer.setPreviewSize(new Size (width, height));
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                getSurfaceHolder().setFixedSize(getPreviewWidth(), getPreviewHeight());
            }
        });
    }

}