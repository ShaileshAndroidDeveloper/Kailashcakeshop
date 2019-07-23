package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_LoginActivity;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.LoginAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.RegistrationAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class LoginActivity extends AppCompatActivity {
    ImageView imgDrawer;
    private Toolbar toolbar;
    RelativeLayout lout_rmain;
    private EditText edt_password,edt_email;
    private Button btn_login;
    private String s_email,s_password;
    public EDProgressDialog progressDialog;
    int Count = 0;
    LinearLayout lout_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences login = getSharedPreferences("sellerlogin", MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firsttime", false);
            editor.putString("user", "");
            editor.putString("userid", "");
            editor.putString("userpassword","");
            editor.apply();

            SharedPreferences.Editor logineditor = login.edit();
            logineditor.putString("user", "");
            logineditor.putString("sellerid", "");
            logineditor.apply();


        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        }
        initView();
        FindViewById();
        setview();

    }

    private void FindViewById() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        lout_rmain = findViewById(R.id.lout_rmain);
        btn_login = findViewById(R.id.btn_login);
        lout_signup= findViewById(R.id.lout_signup);
    }

    private void setview() {
        lout_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_email = edt_email.getText().toString().trim();
                s_password = edt_password.getText().toString().trim();
                Check_Validation(s_email,s_password);
            }
        });

    }

    private void Check_Validation(String s_email, String s_password) {


        if (!Utility.isValidEmail(s_email)) {
            Utility.SnackBar(lout_rmain, "Enter Valid Email");
        }else if (s_password.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Password");
        }
        else
        {
            RequestModel requestModel = new RequestModel();
            requestModel.setEmail(s_email);
            requestModel.setPassword(s_password);
            showprogressdialog();
            new LoginAsync(LoginActivity.this, requestModel);
        }

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("LOGIN");
    }
    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);

        action_bar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Count++;
                if(Count == 5 )
                {
                    startActivity(new Intent(LoginActivity.this,Seller_LoginActivity.class));
                    finish();
                    Count = 0;
                }

            }
        });
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_back);

        getSupportActionBar().setCustomView(v);
        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(LoginActivity.this);

        progressDialog.show();
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public void successresponse(ResponseModel response) {

        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            if (response.getUser_detail() != null && response.getUser_detail().size() > 0) {
                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                for (final ListData_Model model : response.getUser_detail()) {

                    //Log.e("user_id","" +model.getUSER_ID());
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user",model.getFIRST_NAME());
                    editor.putString("userid",model.getU_id());
                    editor.apply();
                }
            }
            progressDialog.dismiss();
            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(LoginActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}
