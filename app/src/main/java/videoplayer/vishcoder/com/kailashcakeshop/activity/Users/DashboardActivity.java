package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.OfferActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_OrderActivity;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Seller.SellerOrderAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.AddOfferAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.SellerOrderAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.GetOfferAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.MenuAnimation;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.OnMenuItemClickListener;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenu;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenuItem;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class DashboardActivity extends AppCompatActivity  {
    ImageView imgDrawer,imgCart,imgMenu;
    private Toolbar toolbar;
    PowerMenu Menu;
    public EDProgressDialog progressDialog;

    RelativeLayout lout_birthcake,lout_engagementcake,lout_photocake,lout_marricake,lout_anniversarycake,lout_functioncake;
    public  static DashboardActivity activity;
   // PowerMenu powerMenu;
    Utility utility;

    List<PowerMenuItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        activity = DashboardActivity.this;
        utility = new Utility();
        initView();
        FindViewById();
        setView();

    }

    private void FindViewById() {

        lout_birthcake = findViewById(R.id.lout_birthcake);
        lout_engagementcake = findViewById(R.id.lout_engagementcake);
        lout_photocake = findViewById(R.id.lout_photocake);
        lout_marricake = findViewById(R.id.lout_marricake);
        lout_anniversarycake = findViewById(R.id.lout_anniversarycake);
        lout_functioncake = findViewById(R.id.lout_functioncake);

    }
    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(DashboardActivity.this);

        progressDialog.show();
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void setView() {
        setmenu();

        RequestModel requestModel = new RequestModel();
        requestModel.setTOKEN("FPpT5QU5Xyf7sGnWjGbQnvDKJEp7ogCe");
        showprogressdialog();
        new GetOfferAsync(DashboardActivity.this, requestModel);

        lout_birthcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "BirthdayCake");
                startActivity(i);
            }
        });
        lout_engagementcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "EngagementCake");
                startActivity(i);
            }
        });
        lout_photocake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "PhotoCake");
                startActivity(i);
            }
        });

        lout_marricake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "MarriageCake");
                startActivity(i);
            }
        });
        lout_anniversarycake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "AnniversaryCake");
                startActivity(i);
            }
        });

        lout_functioncake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CakeActivity.class);
                i.putExtra("Cake_Type", "FunctionCake");
                startActivity(i);

            }
        });

    }

    private void setmenu() {
         String UserID;
        String User;
        SharedPreferences  login = getSharedPreferences("login",MODE_PRIVATE);
        User = login.getString("user",null);
        UserID = login.getString("userid",null);

      //  Log.e("s_user_id","" +UserID +User );
        if(User.equals("")) {
            Menu = new PowerMenu.Builder(activity)
                    // list has "Novel", "Poerty", "Art"
                    .addItem(new PowerMenuItem("Login / Signup", false)) // add an item.
                    .addItem(new PowerMenuItem("About Us", false)) // aad an item list.
                    .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) // Animation start point (TOP | LEFT).
                    .setMenuRadius(10f) // sets the corner radius.
                    .setMenuShadow(10f) // sets the shadow.
                    .setTextColor(activity.getResources().getColor(R.color.black))
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(activity.getResources().getColor(R.color.colorPrimary))
                    .setOnMenuItemClickListener(onMenuItemClickListener)
                    .build();
            }
        else
        {


            Menu = new PowerMenu.Builder(activity)
                    // list has "Novel", "Poerty", "Art"
                    .addItem(new PowerMenuItem("My Account", false)) // add an item.
                    .addItem(new PowerMenuItem("My Order", false)) // add an item.
                    .addItem(new PowerMenuItem("About Us", false)) // aad an item list.
                    .addItem(new PowerMenuItem("Log out", false)) // aad an item list.
                    .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) // Animation start point (TOP | LEFT).
                    .setMenuRadius(10f) // sets the corner radius.
                    .setMenuShadow(10f) // sets the shadow.
                    .setTextColor(activity.getResources().getColor(R.color.black))
                    .setSelectedTextColor(Color.WHITE)
                    .setMenuColor(Color.WHITE)
                    .setSelectedMenuColor(activity.getResources().getColor(R.color.colorPrimary))
                    .setOnMenuItemClickListener(onMenuItemClickListener)
                    .build();
        }
    }


    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("KAILASH CAKE");
    }
    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_menu);
        imgCart = (ImageView)v.findViewById(R.id.imgCart);
        imgCart.setImageResource(R.drawable.ic_cart);
        imgMenu = (ImageView)v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);

        getSupportActionBar().setCustomView(v);


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Menu.showAsDropDown(v);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,CartActivity.class));
            }
        });


    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
         //   Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            String Item = item.getTitle();
            Intent i;
            switch (Item) {
                case "Log out":
                    SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user","");
                    editor.clear();
                    editor.commit();
                     i = new Intent(DashboardActivity.this,SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "My Account":
                   //  i = new Intent(DashboardActivity.this,DashboardActivity.class);
                  //  startActivity(i);
                  //  finish();
                    break;

                case "My Order":
                    i = new Intent(DashboardActivity.this,MyOrderActivity.class);
                    startActivity(i);
                 //  finish();
                    break;

                case "Login / Signup":
                    i = new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;

            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };


    public void successresponse(ResponseModel response) {
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {

            progressDialog.dismiss();
            Global_App.offer = response.getOFFER();
        }
        else
        {
            Toast.makeText(DashboardActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

        }
    }
}
