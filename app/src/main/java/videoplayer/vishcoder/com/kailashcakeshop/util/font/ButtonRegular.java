package videoplayer.vishcoder.com.kailashcakeshop.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class ButtonRegular extends AppCompatButton {
    public ButtonRegular(Context context) {
        super(context);
        init();
    }

    public ButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        if (isInEditMode()) {

        } else {
            try {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");
                setTypeface(tf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
