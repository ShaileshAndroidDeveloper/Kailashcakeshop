package videoplayer.vishcoder.com.kailashcakeshop.util.Custom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import videoplayer.vishcoder.com.kailashcakeshop.R;

public class EDProgressDialog {

    private Activity activity;
    private ProgressDialog progressDialog;

    public EDProgressDialog(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog = ProgressDialog.show(activity, null, null, true);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null);
        ImageView imgF = view.findViewById(R.id.imgE);
        imgF.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade));

        progressDialog.setContentView(view);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void show() {
        progressDialog.show();
    }

    public void dismiss() {
        progressDialog.dismiss();
    }

    public void setProgress(int progress) {
        progressDialog.setProgress(progress);
    }

    public void setText(String text) {
        // progressDialog.setLabel(text);
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }


}
