package videoplayer.vishcoder.com.kailashcakeshop.activity.Seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.MyOrderActivity;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.MyOrderAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.AddOfferAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.SellCakeAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class OfferActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public EDProgressDialog progressDialog;
    ImageView imgDrawer,imgCart,imgMenu;
    EditText edt_offer;
   Button btn_submit;
   String offer;
   LinearLayout lout_rmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        initView();
        setview();

    }

    private void setview() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer = edt_offer.getText().toString().trim();
                Check_Validation(offer);
            }
        });

    }

    private void Check_Validation(String offer) {

         if (offer.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Offer.......");
        }
        else
         {  RequestModel requestModel = new RequestModel();
             requestModel.setOffer(offer);
             showprogressdialog();
             new AddOfferAsync(OfferActivity.this, requestModel);

         }
    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(OfferActivity.this);
        progressDialog.show();
    }
    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("Offer");
        edt_offer  = findViewById(R.id.edt_offer);
        btn_submit = findViewById(R.id.btn_submit);
        lout_rmain = findViewById(R.id.lout_rmain);

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


    public void successresponse(ResponseModel response) {

        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            Toast.makeText(OfferActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(OfferActivity.this,Seller_DashboardActivity.class));
            finish();

        }
        else
        {
            Toast.makeText(OfferActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

    }
}
