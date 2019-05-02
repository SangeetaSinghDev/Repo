package androidx.annotation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

class SurfaceViewContainer extends View {
    public
    SurfaceViewContainer(Context context) {
        this ( context, null );
    }

    public
    SurfaceViewContainer(Context context, AttributeSet attrs) {
        this ( context, attrs, 0 );
    }

    public
    SurfaceViewContainer(Context context, AttributeSet attrs, int defStyle) {
        super ( context, attrs, defStyle );
    }
}
