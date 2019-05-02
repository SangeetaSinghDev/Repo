package events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CameraKitImage extends CameraKitEvent {

    public byte[] jpeg;

    public
    CameraKitImage(byte[] jpeg) {
        super(TYPE_IMAGE_CAPTURED);
        this.jpeg = jpeg;
    }

    public byte[] getJpeg() {
        return jpeg;
    }

    public
    Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
    }

}
