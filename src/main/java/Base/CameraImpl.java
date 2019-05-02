package Base;

import android.support.annotation.Nullable;
import android.util.Size;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import com.gohool.repo.CameraProperties;
import java.io.File;

public abstract class CameraImpl {

    protected final EventDispatcher mEventDispatcher;
    protected final PreviewImpl mPreview;
    private EventDispatcher eventDispatcher = null;

    public
    CameraImpl(EventDispatcher eventDispatcher, PreviewImpl preview) {
        mEventDispatcher = this.eventDispatcher;
        mPreview = preview;
    }

    public abstract void start();
    public abstract void stop();

    public abstract void setDisplayAndDeviceOrientation(int displayOrientation, int deviceOrientation);

    public abstract void setFacing(int facing);
    public abstract void setFlash(int flash);
    public abstract void setFocus(int focus);
    public abstract void setMethod(int method);
    public abstract void setTextDetector(Detector<TextBlock> detector);

    public abstract void setVideoQuality(int videoQuality);
    public abstract void setVideoBitRate(int videoBitRate);
    public abstract void setLockVideoAspectRatio(boolean lockVideoAspectRatio);

    public abstract void setZoom(float zoomFactor);
    public abstract void modifyZoom(float modifier);
    public abstract void setFocusArea(float x, float y);

    public abstract void captureImage(ImageCapturedCallback callback);
    public
    interface ImageCapturedCallback {
        void imageCaptured(byte[] jpeg);
    }

    void captureVideo(File videoFile, VideoCapturedCallback callback) {
        captureVideo(videoFile, 0, callback);
    }

    public abstract void captureVideo(File videoFile, int maxDuration, VideoCapturedCallback callback);
    public
    interface VideoCapturedCallback {
        void videoCaptured(File file);
    }

    public abstract void stopVideo();

    public abstract
    android.util.Size getCaptureResolution();
    public abstract
    android.util.Size getVideoResolution();
    public abstract
    Size getPreviewResolution();
    public abstract boolean isCameraOpened();
    public abstract boolean frontCameraOnly();

    @Nullable
    public abstract
    CameraProperties getCameraProperties();

}
