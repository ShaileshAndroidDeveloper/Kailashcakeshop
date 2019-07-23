package videoplayer.vishcoder.com.kailashcakeshop.activity.Seller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.DashboardActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.SplashActivity;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.MenuAnimation;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.OnMenuItemClickListener;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenu;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenuItem;

public class Seller_DashboardActivity extends AppCompatActivity {
    ImageView imgDrawer, imgCart, imgMenu;
    private Toolbar toolbar;
    PowerMenu Menu;
    Seller_DashboardActivity activity;
    private LinearLayout lout_sellcake,lout_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__dashboard);
        activity = Seller_DashboardActivity.this;

        initView();
        FindViewById();
        setview();
    }

    private void FindViewById() {
        lout_sellcake = findViewById(R.id.lout_sellcake);
        lout_order = findViewById(R.id.lout_order);
    }

    private void setview() {

        setmenu();

        lout_sellcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Seller_DashboardActivity.this,Seller_SellCakeActivity.class));
            }
        });

        lout_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Seller_DashboardActivity.this,Seller_OrderActivity.class));
            }
        });

    }

    private void setmenu() {


        Menu = new PowerMenu.Builder(activity)
                // list has "Novel", "Poerty", "Art"
                .addItem(new PowerMenuItem("Cake", false)) // aad an item list.
                .addItem(new PowerMenuItem("Offer", false)) // aad an item list.
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
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgDrawer = (ImageView) v.findViewById(R.id.imgNavigation);
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgDrawer.setVisibility(View.GONE);
        imgMenu = (ImageView) v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);
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

        imgCart.setVisibility(View.GONE);
    }

    private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            //   Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            String Item = item.getTitle();
            switch (Item) {
                case "Log out":
                    SharedPreferences pref = getSharedPreferences("sellerlogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user", "");
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(Seller_DashboardActivity.this, SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "Cake":
                    Intent in = new Intent(Seller_DashboardActivity.this, Seller_CakelistActivity.class);
                    startActivity(in);
                    break;
                case "Offer":
                    Intent inn = new Intent(Seller_DashboardActivity.this, OfferActivity.class);
                    startActivity(inn);
                    break;


            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };


}
