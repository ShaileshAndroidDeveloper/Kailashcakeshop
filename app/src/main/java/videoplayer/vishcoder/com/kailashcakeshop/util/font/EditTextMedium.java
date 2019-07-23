package videoplayer.vishcoder.com.kailashcakeshop.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Milan on 25/8/2017.
 */

public class EditTextMedium extends AppCompatEditText {

    public EditTextMedium(Context context) {
        super(context);
        init();
    }

    public EditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextMedium(Context context, AttributeSet attrs, int defStyleAttr) {
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
