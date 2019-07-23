package videoplayer.vishcoder.com.kailashcakeshop.activity.Seller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.DashboardActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.LoginActivity;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.SellerLoginAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.LoginAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class Seller_LoginActivity extends AppCompatActivity {
    ImageView imgDrawer;
    private Toolbar toolbar;
    RelativeLayout lout_rmain;
    private EditText edt_password,edt_email;
    private Button btn_login;
    private String s_email,s_password;
    public EDProgressDialog progressDialog;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__login);

        SharedPreferences login = getSharedPreferences("sellerlogin", MODE_PRIVATE);
        User = login.getString("user", null);

        if (User.equals("")) {

            initView();
            FindViewById();
            setview();
        }
        else {
            startActivity(new Intent(Seller_LoginActivity.this,Seller_DashboardActivity.class));
            finish();
        }


    }

    private void FindViewById() {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        lout_rmain = findViewById(R.id.lout_rmain);
        btn_login = findViewById(R.id.btn_login);
    }
    private void setview() {

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
            new SellerLoginAsync(Seller_LoginActivity.this, requestModel);
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

        progressDialog = new EDProgressDialog(Seller_LoginActivity.this);

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
                SharedPreferences pref = getSharedPreferences("sellerlogin",MODE_PRIVATE);
                for (final ListData_Model model : response.getUser_detail()) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user",model.getEMAIL());
                    editor.putString("sellerid",model.getSELLER_ID());
                    //    editor.putInt("idName", 0);
                    editor.apply();
                    //  Toast.makeText(LoginActivity.this,model.getEMAIL(),Toast.LENGTH_LONG).show();
                }
            }
            progressDialog.dismiss();
            startActivity(new Intent(Seller_LoginActivity.this,Seller_DashboardActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(Seller_LoginActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }



}
