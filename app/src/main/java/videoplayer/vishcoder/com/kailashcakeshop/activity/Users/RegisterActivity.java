package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.RegistrationAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class RegisterActivity extends AppCompatActivity {
    public EDProgressDialog progressDialog;
    ImageView imgDrawer;
    private Toolbar toolbar;
    EditText edt_firstname, edt_lastname, edt_mobile, edt_email, edt_password, edt_cpassword;
    String s_firstname, s_lastname, s_mobile, s_email, s_password, s_cpassword;
    Button btn_signup;
    RelativeLayout lout_rmain;
    TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        }

        initView();
        FindViewById();
        setview();

    }

    private void setview() {
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_firstname = edt_firstname.getText().toString().trim();
                s_lastname = edt_lastname.getText().toString().trim();
                s_mobile = edt_mobile.getText().toString().trim();
                s_email = edt_email.getText().toString().trim();
                s_password = edt_password.getText().toString().trim();
                s_cpassword = edt_cpassword.getText().toString().trim();
                Check_Validation(s_firstname, s_lastname, s_mobile, s_password, s_cpassword, s_email);
            }
        });
    }

    private void Check_Validation(String s_firstname, String s_lastname, String s_mobile, String s_password, String s_cpassword, String s_email) {

        if (s_firstname.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter First Name");
        } else if (s_lastname.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Last Name");
        } else if (s_mobile.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Mobile Number");
        } else if (Utility.IsLength(s_mobile, 10)) {
            Utility.SnackBar(lout_rmain, "Enter 10 Digit Mobile Number");
        } else if (!Utility.isValidMob(s_mobile)) {
            Utility.SnackBar(lout_rmain, "Enter Valid Mobile Number");
        } else if (!Utility.isValidEmail(s_email)) {
            Utility.SnackBar(lout_rmain, "Enter Valid Email");
        } else if (s_password.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Password");
        } else if (s_password.length() < 6) {
            Utility.SnackBar(lout_rmain, "Password should have 6 character");
        } else if (s_cpassword.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Confirm Password");
        } else if (!s_password.equals(s_cpassword)) {
            Utility.SnackBar(lout_rmain, "Confirm Password Not Match...");
        } else {
            RequestModel requestModel = new RequestModel();
            requestModel.setFirst_name(s_firstname);
            requestModel.setLast_name(s_lastname);
            requestModel.setMobile_no(s_mobile);
            requestModel.setEmail(s_email);
            requestModel.setPassword(s_password);
            showprogressdialog();
            new RegistrationAsync(RegisterActivity.this, requestModel);

        }

    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(RegisterActivity.this);

        progressDialog.show();
    }

    private void FindViewById() {
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_cpassword = findViewById(R.id.edt_cpassword);
        btn_signup = findViewById(R.id.btn_signup);
        lout_rmain = findViewById(R.id.lout_rmain);
        tv_signup = findViewById(R.id.tv_signup);

    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("REGISTER");
    }

    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
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


    public void successresponse(ResponseModel response) {

            if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
            {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
               startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
            else
            {
                Toast.makeText(RegisterActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
