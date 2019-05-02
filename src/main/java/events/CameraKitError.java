package events;

import android.support.annotation.Nullable;

public class CameraKitError extends CameraKitEvent {


    private String type;
    private String message;
    private Exception exception;

    public
    CameraKitError() {
        super ( ( String ) TYPE_ERROR );
    }

    public
    CameraKitError(Exception exception) {
        super( ( String ) TYPE_ERROR );
        this.exception = exception;
    }

    @Nullable
    public Exception getException() {
        return exception;
    }

}