package Types;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.gohool.repo.CameraKit.Constants.FLASH_AUTO;
import static com.gohool.repo.CameraKit.Constants.FLASH_OFF;
import static com.gohool.repo.CameraKit.Constants.FLASH_ON;
import static com.gohool.repo.CameraKit.Constants.FLASH_TORCH;

@Retention(RetentionPolicy.SOURCE)
@IntDef({FLASH_OFF, FLASH_ON, FLASH_AUTO, FLASH_TORCH})
public @interface Flash {
}