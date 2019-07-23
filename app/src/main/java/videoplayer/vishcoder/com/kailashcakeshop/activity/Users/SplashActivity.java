package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences login = getSharedPreferences("sellerlogin", MODE_PRIVATE);

        if (prefs.getBoolean("firsttime", true)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firsttime", false);
            editor.putString("user", "");
            editor.putString("userid", "");
            editor.apply();

            SharedPreferences.Editor logineditor = login.edit();
            logineditor.putString("user", "");
            logineditor.putString("sellerid", "");
            logineditor.apply();

        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                Global_App.User = pref.getString("user", null);
               // Log.e("AAAA", "" + Global_App.User);
                if (Global_App.User.length() <= 0) {

                    Intent in = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(in);
                    finish();

                } else {

                    Intent in = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(in);
                    finish();

                }

            }
        }, 1000);

    }
}
