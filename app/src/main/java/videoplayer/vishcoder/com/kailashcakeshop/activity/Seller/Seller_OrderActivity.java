package videoplayer.vishcoder.com.kailashcakeshop.activity.Seller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.LoginActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.MyOrderActivity;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Seller.SellerOrderAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.MyOrderAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.SellerOrderAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.MyOrderAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;

public class Seller_OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView rv_myorder;
    ImageView imgDrawer,imgCart,imgMenu;
    SellerOrderAdapter adapter;
    Seller_OrderActivity activity;
    ArrayList<ListData_Model> arrayList;
    public EDProgressDialog progressDialog;
    String s_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        activity = Seller_OrderActivity.this;
        arrayList = new ArrayList<>();
        initView();
        setview();
    }

    private void setview() {

            RequestModel requestModel = new RequestModel();
            requestModel.setTOKEN("FPpT5QU5Xyf7sGnWjGbQnvDKJEp7ogCe");
            showprogressdialog();
            new SellerOrderAsync(Seller_OrderActivity.this, requestModel);
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("My Order");
        rv_myorder = (RecyclerView) findViewById(R.id.rv_myorder);

    }

    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgMenu = (ImageView)v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);
        getSupportActionBar().setCustomView(v);
        imgCart.setVisibility(View.GONE);
        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        imgMenu.setVisibility(View.GONE);

    }


    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(Seller_OrderActivity.this);

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
            adapter = new SellerOrderAdapter(activity,arrayList);
            rv_myorder.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(Seller_OrderActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

            RelativeLayout lout_nodata = (RelativeLayout)findViewById(R.id.lout_nodata);
            lout_nodata.setVisibility(View.VISIBLE);

        }
    }

}


