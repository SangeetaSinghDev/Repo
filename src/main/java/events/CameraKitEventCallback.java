package events;

public interface CameraKitEventCallback<T extends CameraKitEvent> {
    void callback(T event);
}