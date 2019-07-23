package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CartListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.CartListnAsync;
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

public class CartActivity extends AppCompatActivity {

    ImageView imgDrawer,imgCart,imgMenu;
    private Toolbar toolbar;
    Utility utility;
    CartActivity activity;
    private RecyclerView rv_cart;
    ArrayList<ListData_Model> arrayList;
    CartListAdapter adapter;
    public EDProgressDialog progressDialog;
    private String s_user_id;
    TextView tv_finalamount;
    Button btn_buynow,btn_continue;
    PowerMenu Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        utility = new Utility();
        activity = CartActivity.this;

        initView();
        setView();
    }

    private void setView() {
        setmenu();
        Global_App.finaltotal = 0;
        rv_cart.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
        s_user_id = login.getString("userid",null);
//Log.e("s_user_id","" +s_user_id);
        if(s_user_id.equals("")) {

            startActivity(new Intent(CartActivity.this,LoginActivity.class));
            finish();
        }
        else
        {


        RequestModel requestModel = new RequestModel();
        requestModel.setU_id(s_user_id);
        showprogressdialog();
        new CartListnAsync(CartActivity.this, requestModel);

        }

        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(CartActivity.this,CheckOutActivity.class));
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,DashboardActivity.class));
                finish();
            }
        });

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
                    i = new Intent(CartActivity.this,SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "My Account":
                    //  i = new Intent(DashboardActivity.this,DashboardActivity.class);
                    //  startActivity(i);
                    //  finish();
                    break;

                case "My Order":
                    i = new Intent(CartActivity.this,MyOrderActivity.class);
                    startActivity(i);
                    finish();
                    break;

                case "Login / Signup":
                    i = new Intent(CartActivity.this,LoginActivity.class);
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
        setHeaderView("My Cart");
        rv_cart = (RecyclerView) findViewById(R.id.rv_cart);
        tv_finalamount = findViewById(R.id.tv_finalamount);
        btn_buynow = findViewById(R.id.btn_buynow);
        btn_continue = findViewById(R.id.btn_continue);
    }

    private void setHeaderView(String title) {


        View v = LayoutInflater.from(this).inflate(R.layout.actionbar_home, null);
        TextView action_bar_title = (TextView) v.findViewById(R.id.action_bar_title);
        action_bar_title.setText(title);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgCart = (ImageView)v.findViewById(R.id.imgCart);
        imgCart.setImageResource(R.drawable.ic_cart);
        imgMenu = (ImageView)v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);

        getSupportActionBar().setCustomView(v);
        imgCart.setVisibility(View.GONE);

        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgMenu.setVisibility(View.GONE);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Menu.showAsDropDown(v);
            }
        });
    }

    public void showprogressdialog() {

        progressDialog = new EDProgressDialog(CartActivity.this);

        progressDialog.show();
    }

    public void successresponse(ResponseModel response) {
     //   Log.e("carstatus","" + response.getStatus());
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            arrayList = new ArrayList<ListData_Model>();
            arrayList.addAll(response.getList());

            Log.e("cart","" + arrayList.size());
            adapter = new CartListAdapter(CartActivity.this, arrayList);
            rv_cart.setAdapter(adapter);
            for(int i = 0 ; i < arrayList.size() ; i++)
            {
                double rate = Integer.parseInt(arrayList.get(i).getCAKE_RATE());
                double quantity = Integer.parseInt(arrayList.get(i).getQUANTITY());
                double Subtotal = rate * quantity;
                Global_App.finaltotal += Subtotal;
            }
            tv_finalamount.setText(""+ Global_App.finaltotal);
          //  Toast.makeText(RegisterActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
        else
        {
            Toast.makeText(CartActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            RelativeLayout lout_nodata = (RelativeLayout)findViewById(R.id.lout_nodata);
            LinearLayout Lout_Bottom = (LinearLayout)findViewById(R.id.Lout_Bottom);
            lout_nodata.setVisibility(View.VISIBLE);
            Lout_Bottom.setVisibility(View.GONE);
        }
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void successresponseremove(ResponseModel response) {
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            Toast.makeText(CartActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
             startActivity(new Intent(CartActivity.this,CartActivity.class));
             finish();
        }
        else
        {
            Toast.makeText(CartActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

        }
    }
}
