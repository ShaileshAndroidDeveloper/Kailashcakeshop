package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CakeListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.CakeAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.RegistrationAsync;
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

public class CakeActivity extends AppCompatActivity {
    public EDProgressDialog progressDialog;
    ImageView imgDrawer,imgCart,imgMenu;
    private Toolbar toolbar;
    private RecyclerView rv_cake;
    CakeListAdapter adapter;
    ArrayList<ListData_Model> Cake_Array;
    Utility utility;
    CakeActivity activity;
     String Cake_Type;
     TextView tv_offer;
     RelativeLayout lout_nodata;
    PowerMenu Menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        utility = new Utility();
        activity = CakeActivity.this;

        initView();
        try {
            if (!getIntent().getStringExtra("Cake_Type").equals("")) {
                Cake_Type = getIntent().getStringExtra("Cake_Type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setview();
    }

    private void setview() {


        setmenu();
      //  Log.e("Cake_Type","" + Cake_Type);
        RequestModel requestModel = new RequestModel();

        showprogressdialog();

        new CakeAsync(CakeActivity.this, requestModel);

        rv_cake.setLayoutManager(new GridLayoutManager(this, 2));


    }

    private void setmenu() {


        String UserID;
        String User;
        SharedPreferences  login = getSharedPreferences("login",MODE_PRIVATE);
        User = login.getString("user",null);
        UserID = login.getString("userid",null);

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
                    i = new Intent(CakeActivity.this,SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "My Account":
                    //  i = new Intent(DashboardActivity.this,DashboardActivity.class);
                    //  startActivity(i);
                    //  finish();
                    break;

                case "My Order":
                    i = new Intent(CakeActivity.this,MyOrderActivity.class);
                    startActivity(i);
                    finish();
                    break;

                case "Login / Signup":
                    i = new Intent(CakeActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;

            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };



    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("KAILASH CAKE");
        rv_cake = (RecyclerView) findViewById(R.id.rv_cake);
        lout_nodata = findViewById(R.id.lout_nodata);
        tv_offer = findViewById(R.id.tv_offer);
        tv_offer.setText(Global_App.offer);

    }

    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgMenu = (ImageView)v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);
        imgCart = (ImageView)v.findViewById(R.id.imgCart);
        imgCart.setImageResource(R.drawable.ic_cart);
        getSupportActionBar().setCustomView(v);
        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Menu.showAsDropDown(v);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CakeActivity.this,CartActivity.class));
            }
        });

    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(CakeActivity.this);

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
            progressDialog.dismiss();
            Cake_Array = new ArrayList<ListData_Model>();
            Cake_Array.addAll(response.getList());
            Log.e("AAAAAAA","" +response.getList());
           // if(Cake_Array.size() != 0) {
                adapter = new CakeListAdapter(CakeActivity.this, Cake_Array);
                rv_cake.setAdapter(adapter);

        }
        else
        {
           // Toast.makeText(CakeActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            rv_cake.setVisibility(View.GONE);
            lout_nodata.setVisibility(View.VISIBLE);
        }
    }


}
