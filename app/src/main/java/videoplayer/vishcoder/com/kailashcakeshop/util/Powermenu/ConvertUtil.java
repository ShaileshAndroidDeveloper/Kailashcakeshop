package videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public class ConvertUtil {
  protected static int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    return Math.round(
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
  }
}