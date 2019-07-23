package videoplayer.vishcoder.com.kailashcakeshop.activity.Seller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.LoginActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.async.Seller.SellCakeAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.RegistrationAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.MenuAnimation;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.OnMenuItemClickListener;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenu;
import videoplayer.vishcoder.com.kailashcakeshop.util.Powermenu.PowerMenuItem;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class Seller_SellCakeActivity extends AppCompatActivity {
    public EDProgressDialog progressDialog;
    TextView tv_birthday, tv_engagement, tv_photocake, tv_marriagecake, tv_anniversarycake, tv_functioncake;
    TextView Txt_Title, Txt_Back, Txt_Done;
    ImageView imgDrawer, imgCart, imgMenu;
    private Toolbar toolbar;
    PowerMenu Menu;
    Seller_SellCakeActivity activity;
    RelativeLayout lout_image, lout_plus;
    ImageView img_cake;
    Uri uri;
    EditText edt_caketype, edt_caketitle, edt_cakedetail, edt_cakeflavour, edt_cakerate;
    Button btn_submit;
    String s_caketype, s_caketitle, s_cakedetail, s_cakeflavour, s_cakerate;
    RelativeLayout lout_rmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_cake);
        activity = Seller_SellCakeActivity.this;
        initView();
        FindViewById();
        clearRequestData();
        setview();
    }

    private void clearRequestData() {
        edt_caketype.setText("");
        edt_caketitle.setText("");
        edt_cakedetail.setText("");
        edt_cakeflavour.setText("");
        edt_cakerate.setText("");
        img_cake.setVisibility(View.GONE);
        lout_plus.setVisibility(View.VISIBLE);
        uri = Uri.parse("");
    }

    private void FindViewById() {
        lout_image = findViewById(R.id.lout_image);
        lout_plus = findViewById(R.id.lout_plus);
        img_cake = findViewById(R.id.img_cake);
        edt_caketype = findViewById(R.id.edt_caketype);
        edt_caketitle = findViewById(R.id.edt_caketitle);
        edt_cakedetail = findViewById(R.id.edt_cakedetail);
        edt_cakeflavour = findViewById(R.id.edt_cakeflavour);
        edt_cakerate = findViewById(R.id.edt_cakerate);
        btn_submit = findViewById(R.id.btn_submit);
        lout_rmain = findViewById(R.id.lout_rmain);
    }

    private void setview() {

        setmenu();

        lout_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {

                    if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                    }
                }

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");


                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 100);
            }
        });

        edt_caketype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogcaketype();

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String k = edittext.getText().toString().replace(' ', '');
                s_caketype = edt_caketype.getText().toString().replace(" ", "");
                Toast.makeText(activity, s_caketype, Toast.LENGTH_LONG).show();
                s_caketitle = edt_caketitle.getText().toString().trim();
                s_cakedetail = edt_cakedetail.getText().toString().trim();
                s_cakeflavour = edt_cakeflavour.getText().toString().trim();
                s_cakerate = edt_cakerate.getText().toString().trim();
                Check_Validation(s_caketype, s_caketitle, s_cakedetail, s_cakeflavour, s_cakerate);
            }
        });
    }

    private void Check_Validation(String s_caketype, String s_caketitle, String s_cakedetail, String s_cakeflavour, String s_cakerate) {

        if (uri == null) {
            Utility.SnackBar(lout_rmain, "Select Cake Image");
        } else if (s_caketype.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Select Cake Type");
        } else if (s_caketitle.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Cake Title");
        } else if (s_cakedetail.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Cake Details");
        } else if (s_cakeflavour.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Cake Flavour");
        } else if (s_cakerate.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Cake Rate");
        } else {

            RequestModel requestModel = new RequestModel();
            requestModel.setCaketype(s_caketype);
            requestModel.setCaketitle(s_caketitle);
            requestModel.setCakedetail(s_cakedetail);
            requestModel.setCakeflavour(s_cakeflavour);
            requestModel.setCakerate(s_cakerate);
            requestModel.setSellerid(String.valueOf(1));
            showprogressdialog();
            new SellCakeAsync(Seller_SellCakeActivity.this, requestModel,uri);

        }

    }

    private void dialogcaketype() {


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_caketype);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        Txt_Back = dialog.findViewById(R.id.Txt_Back);
        Txt_Done = dialog.findViewById(R.id.Txt_Done);
        Txt_Title = dialog.findViewById(R.id.Dialog_Title);

        tv_birthday = dialog.findViewById(R.id.tv_birthday);
        tv_engagement = dialog.findViewById(R.id.tv_engagement);
        tv_photocake = dialog.findViewById(R.id.tv_photocake);
        tv_marriagecake = dialog.findViewById(R.id.tv_marriagecake);
        tv_anniversarycake = dialog.findViewById(R.id.tv_anniversarycake);
        tv_functioncake = dialog.findViewById(R.id.tv_functioncake);

        Txt_Done.setVisibility(View.INVISIBLE);

        tv_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.birthday_cake);
                dialog.dismiss();
            }
        });


        tv_engagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.engagement_cake);
                dialog.dismiss();
            }
        });

        tv_photocake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.photo_cake);
                dialog.dismiss();
            }
        });

        tv_marriagecake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.marriage_cake);
                dialog.dismiss();
            }
        });

        tv_anniversarycake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.anniversary_cake);
                dialog.dismiss();
            }
        });

        tv_functioncake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_caketype.setText(R.string.function_cake);
                dialog.dismiss();
            }
        });


        Txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        Display display = activity.getWindowManager().getDefaultDisplay();
        int height =  (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        dialog.getWindow().setWindowAnimations(R.style.MaterialDialogSheetAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            uri = data.getData();
            if (uri != null) {
                img_cake.setImageURI(uri);
                img_cake.setVisibility(View.VISIBLE);
                lout_plus.setVisibility(View.GONE);
            }

        }
    }


    private void setmenu() {


        Menu = new PowerMenu.Builder(activity)
                // list has "Novel", "Poerty", "Art"
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
                    editor.commit();
                    Toast.makeText(getBaseContext(), Global_App.User, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Seller_SellCakeActivity.this, Seller_LoginActivity.class);
                    startActivity(i);

                    break;

            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };


    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(Seller_SellCakeActivity.this);
        progressDialog.show();
    }

    public void successresponse(ResponseModel response) {

        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            Toast.makeText(Seller_SellCakeActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            clearRequestData();
          //  startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
        else
        {
            Toast.makeText(Seller_SellCakeActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
