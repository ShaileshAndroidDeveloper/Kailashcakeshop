package videoplayer.vishcoder.com.kailashcakeshop.util.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by admin on 8/24/2017.
 */
public class TextViewBoldRoboto extends AppCompatTextView {

    public TextViewBoldRoboto(Context context) {
        super(context);
        init();
    }

    public TextViewBoldRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewBoldRoboto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        if (isInEditMode()) {
            //
        } else {
            try {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Bold.ttf");
                setTypeface(tf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}