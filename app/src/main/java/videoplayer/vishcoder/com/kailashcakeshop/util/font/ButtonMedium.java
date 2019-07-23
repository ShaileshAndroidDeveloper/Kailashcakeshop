package videoplayer.vishcoder.com.kailashcakeshop.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class ButtonMedium extends AppCompatButton {
    public ButtonMedium(Context context) {
        super(context);
        init();
    }

    public ButtonMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        if (isInEditMode()) {

        } else {
            try {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Medium.ttf");
                setTypeface(tf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
