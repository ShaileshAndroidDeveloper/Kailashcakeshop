package videoplayer.vishcoder.com.kailashcakeshop.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.DashboardActivity;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.MenuAnimation;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.OnMenuItemClickListener;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenu;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenuItem;
import videoplayer.vishcoder.com.kailashcakeshop.util.Snackbar.TSnackbar;

import static android.content.Context.MODE_PRIVATE;

public class Utility extends Application {

    public static boolean isValidMob(String mobile) {
        String mobilepattern = "^[6789]\\d{9}$";
        return mobile.matches(mobilepattern);
    }

    public static boolean IsLength(String text, Integer N) {
        return text.length() < N;
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}(\\.[a-zA-Z]{2,3}){0,1}";
        return email.matches(emailPattern);
    }

    //region Empty Value
    public static boolean isEmpty(String s) {
        return s.trim().equals("") || s == null || s.isEmpty() || s.equals("null") || s.length() == 0;
    }
    //endregion



    public static void SnackBar(View view, String Title) {
        TSnackbar snackbar = TSnackbar.make(view, "Snacking with VectorDrawable", TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#E10000"));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setText(Title);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static boolean checkInternetConnection(Activity context) {
        try {
            if (context != null) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm != null && cm.getActiveNetworkInfo() != null) {
                    return cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    //region Toast Bar
    public static void toastView(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }


}
