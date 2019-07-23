package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CartListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.MyOrderAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.CheckoutAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.MyOrderAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class MyOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView rv_myorder;
    ImageView imgDrawer,imgCart,imgMenu;
    MyOrderAdapter adapter;
    MyOrderActivity activity;
    ArrayList<ListData_Model> arrayList;
    public EDProgressDialog progressDialog;
    String s_user_id;
    LinearLayout lout_call,lout_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        activity = MyOrderActivity.this;
        arrayList = new ArrayList<>();
        initView();
        findViewById();
        setview();
    }

    private void findViewById() {
        lout_call = findViewById(R.id.lout_call);
        lout_number = findViewById(R.id.lout_number);
        lout_call.setVisibility(View.VISIBLE);

    }

    private void setview() {

        lout_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);
                        return;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + Global_App.Sales_Person_Mobile));
                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + Global_App.Sales_Person_Mobile));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                   startActivity(intent);
                }
            }
        });

        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        s_user_id = login.getString("userid", null);
        if (s_user_id.equals("")) {
            startActivity(new Intent(MyOrderActivity.this, LoginActivity.class));
            finish();
        }
        else
        {
            RequestModel requestModel = new RequestModel();
            requestModel.setU_id(s_user_id);
            showprogressdialog();
            new MyOrderAsync(MyOrderActivity.this, requestModel);
        }


    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("My Order");
        rv_myorder = (RecyclerView) findViewById(R.id.rv_myorder);
       // lout_nodata = findViewById(R.id.lout_nodata);

    }

    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgMenu = (ImageView)v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);
        getSupportActionBar().setCustomView(v);
        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgCart.setVisibility(View.GONE);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                }
        });

      imgMenu.setVisibility(View.GONE);

    }


    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(MyOrderActivity.this);

        progressDialog.show();
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public void successresponse(ResponseModel response) {
        //   Log.e("carstatus","" + response.getStatus());
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();

            arrayList = new ArrayList<ListData_Model>();
            arrayList.addAll(response.getList());

            rv_myorder.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            adapter = new MyOrderAdapter(activity,arrayList);
            rv_myorder.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(MyOrderActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            RelativeLayout lout_nodata = (RelativeLayout)findViewById(R.id.lout_nodata);
            lout_nodata.setVisibility(View.VISIBLE);

        }
    }

    //region Call Permission
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (10 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + Global_App.Sales_Person_Mobile));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                activity.startActivity(intent);
            }
        } else {
            Utility.toastView(activity, activity.getResources().getString(R.string.Permission_Denied));
        }
    }
    //endregion

}
