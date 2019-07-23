package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CakeListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CartListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.AddToCartAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.AreaAsync;
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

public class CakeDetailActivity extends AppCompatActivity {
    private static ArrayList<ListData_Model> Area_Array;
    ImageView imgDrawer, imgCart, imgMenu;
    TextView Txt_Title, Txt_Back, Txt_Done;
    private Toolbar toolbar;
    Utility utility;
    CakeDetailActivity activity;
    static final int TIME_DIALOG_ID = 1111;
    private int hour;
    private int minute;
    EditText edt_time,edt_date,edt_areaname,edt_pincode,edt_area,edt_selectphoto,edt_cakemessage;
    ImageView img_cake,img_minus,img_plus;
    TextView tv_title, tv_price, tv_detail, tv_flavour,tv_quantity;
    String CAKE_ID, CAKE_TYPE, CAKE_TITLE, CAKE_DETAIL, CAKE_FLAVOUR, CAKE_RATE, CAKE_IMAGE;
    int counter = 1;
    Uri uri;
    int year;
    int month;
    int day;
    LinearLayout lout_time,lout_date,lout_pincode;
    RelativeLayout lout_rmain;
    private AreaListAdapter adapter;
    RecyclerView rv_area;
    public EDProgressDialog progressDialog;
    Button btn_addcart;
    private  String s_cakeid,s_quantity,s_pincode,s_area,s_deliverydate,s_deliverytime,s_meesage,s_user_id;
    LinearLayout lout_selectimage;
    PowerMenu Menu;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_detail);
        utility = new Utility();
        activity = CakeDetailActivity.this;


        initView();

        try {
            if (!getIntent().getStringExtra("CAKE_ID").equals("")
                    || !getIntent().getStringExtra("CAKE_TYPE").equals("")
                    || !getIntent().getStringExtra("CAKE_TITLE").equals("")
                    || !getIntent().getStringExtra("CAKE_DETAIL").equals("")
                    || !getIntent().getStringExtra("CAKE_FLAVOUR").equals("")
                    || !getIntent().getStringExtra("CAKE_RATE").equals("")
                    || !getIntent().getStringExtra("CAKE_IMAGE").equals(""))
            {
                CAKE_ID = getIntent().getStringExtra("CAKE_ID");
                CAKE_TYPE = getIntent().getStringExtra("CAKE_TYPE");
                CAKE_TITLE = getIntent().getStringExtra("CAKE_TITLE");
                CAKE_DETAIL = getIntent().getStringExtra("CAKE_DETAIL");
                CAKE_FLAVOUR = getIntent().getStringExtra("CAKE_FLAVOUR");
                CAKE_RATE = getIntent().getStringExtra("CAKE_RATE");
                CAKE_IMAGE = getIntent().getStringExtra("CAKE_IMAGE");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById();
        setView();


    }

    private void findViewById() {
        img_cake = findViewById(R.id.img_cake);
        tv_quantity = findViewById(R.id.tv_quantity);
        img_plus = findViewById(R.id.img_plus);
        img_minus = findViewById(R.id.img_minus);
        edt_time = findViewById(R.id.edt_time);
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        tv_detail = findViewById(R.id.tv_detail);
        tv_flavour = findViewById(R.id.tv_flavour);
        edt_date = findViewById(R.id.edt_date);
        lout_time = findViewById(R.id.lout_time);
        lout_date = findViewById(R.id.lout_date);
        lout_pincode = findViewById(R.id.lout_pincode);
        edt_areaname = findViewById(R.id.edt_areaname);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_area= findViewById(R.id.edt_areaname);
        btn_addcart=findViewById(R.id.btn_addcart);
        lout_rmain = findViewById(R.id.lout_rmain);
        edt_selectphoto = findViewById(R.id.edt_selectphoto);
        lout_selectimage = findViewById(R.id.lout_selectimage);
        edt_cakemessage  = findViewById(R.id.edt_cakemessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            uri = data.getData();
            if (uri != null) {
                String filePath = Utility.getRealPathFromURIPath(uri, activity);
                File file = new File(filePath);
                String filename = file.getName();
                edt_selectphoto.setText(filename);
            }

        }
    }

    private void setView() {
        setmenu();
        if(CAKE_TYPE.equals("PhotoCake"))
        {
            lout_selectimage.setVisibility(View.VISIBLE);
        }
        edt_selectphoto.setOnClickListener(new View.OnClickListener() {
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



        if (!Utility.isEmpty(CAKE_IMAGE)) {
            Picasso.with(activity)
                    .load(CAKE_IMAGE)
                    .error(R.drawable.ic_no_img)
                    .placeholder(R.drawable.ic_no_img)
                    .into(img_cake);
        } else {
            img_cake.setImageResource(R.drawable.ic_no_img);
        }

        tv_title.setText(CAKE_TITLE);
        tv_price.setText(activity.getResources().getString(R.string.RS) + CAKE_RATE);
        tv_detail.setText(CAKE_DETAIL);
        tv_flavour.setText(CAKE_FLAVOUR);

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  counter++;
                if(counter != 5)
                {
                    counter++;
                    tv_quantity.setText(Integer.toString(counter));
                }
                else
                    {
                        Toast.makeText(activity,"Maximum Quantity is 5",Toast.LENGTH_LONG).show();
                }
            }
        });


        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  counter++;
                if(counter != 1)
                {
                    counter--;
                    tv_quantity.setText(Integer.toString(counter));

                }
                else
                {
                    Toast.makeText(activity,"Minimum Quantity is 1",Toast.LENGTH_LONG).show();
                }
            }
        });
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  edt_time.setText(dateFormat);
                 showDialog(TIME_DIALOG_ID);
            }
        });

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                year=mcurrentDate.get(Calendar.YEAR);
                month=mcurrentDate.get(Calendar.MONTH);
                day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog   mDatePicker =new DatePickerDialog(CakeDetailActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        edt_date.setText(new StringBuilder().append(day).append("-").append(month+1).append("-").append(year));
                        int month_k=selectedmonth+1;

                    }
                },year, month, day);
                mDatePicker.setTitle("Please select date");
                // TODO Hide Future Date Here
              //  mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });


        edt_pincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodedialog();
            }
        });
        edt_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodedialog();
            }
        });

        btn_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  private  String s_cakeid,s_quantity,s_pincode,s_area,s_deliverydate,s_deliverytime,s_user_id;

                SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
                s_user_id = login.getString("userid",null);
                if(s_user_id.equals("")) {

                    startActivity(new Intent(CakeDetailActivity.this,LoginActivity.class));
                }
                else
                {
                s_cakeid = CAKE_ID;
                s_quantity = tv_quantity.getText().toString().trim();
                s_pincode = edt_pincode.getText().toString().trim();
                s_area = edt_area.getText().toString().trim();
                 s_meesage =   edt_cakemessage.getText().toString().trim();
                Check_Validation(s_cakeid, s_quantity,s_user_id,s_meesage);

                }

            }
        });

    }

    private void Check_Validation(String s_cakeid, String s_quantity, String s_user_id,String s_meesage) {
        if(CAKE_TYPE.equals("PhotoCake"))
        {
            if (s_cakeid.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            } else if (s_quantity.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            } else if (s_pincode.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Select Area And Pincode....");
            }else if (uri == null) {
                Utility.SnackBar(lout_rmain, "Select Image for Photo Cake....");
            }else if (s_meesage.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Enter Message On Cake...");
            }
            else if (s_user_id.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            }

            else
            {

                RequestModel requestModel = new RequestModel();
                requestModel.setQUANTITY(s_quantity);
                requestModel.setCAKE_ID(s_cakeid);
                requestModel.setU_id(s_user_id);
                requestModel.setCARTSTATUS(String.valueOf(1));
                showprogressdialog();
                new AddToCartAsync(CakeDetailActivity.this, requestModel,uri);
            }
        }
        else
        {
            if (s_cakeid.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            } else if (s_quantity.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            } else if (s_pincode.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Select Area And Pincode....");
            }

            else if (s_user_id.length() <= 0) {
                Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");

            }

            else
            {

                RequestModel requestModel = new RequestModel();
                requestModel.setQUANTITY(s_quantity);

                requestModel.setCAKE_ID(s_cakeid);
                requestModel.setU_id(s_user_id);
                requestModel.setCARTSTATUS(String.valueOf(1));
                requestModel.setMessage(s_meesage);
                showprogressdialog();
                new AddToCartAsync(CakeDetailActivity.this, requestModel,uri);


            }
        }


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
                    i = new Intent(CakeDetailActivity.this,SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "My Account":
                    //  i = new Intent(DashboardActivity.this,DashboardActivity.class);
                    //  startActivity(i);
                    //  finish();
                    break;

                case "My Order":
                    i = new Intent(CakeDetailActivity.this,MyOrderActivity.class);
                    startActivity(i);
                    finish();
                    break;

                case "Login / Signup":
                    i = new Intent(CakeDetailActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;

            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour   = hourOfDay;
            minute = minutes;

            updateTime(hour,minute);

        }

    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        edt_time.setText(aTime);
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
        imgDrawer.setImageResource(R.drawable.ic_back);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgCart.setImageResource(R.drawable.ic_cart);
        imgMenu = (ImageView) v.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.ic_menuright);

        getSupportActionBar().setCustomView(v);


        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                startActivity(new Intent(CakeDetailActivity.this,CartActivity.class));
            }
        });
    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(CakeDetailActivity.this);

        progressDialog.show();
    }
    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void pincodedialog() {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_caketype);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        Txt_Back = dialog.findViewById(R.id.Txt_Back);
        Txt_Done = dialog.findViewById(R.id.Txt_Done);
        Txt_Title = dialog.findViewById(R.id.Dialog_Title);
        Txt_Done.setVisibility(View.INVISIBLE);
        Txt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout lout_caketype = dialog.findViewById(R.id.lout_caketype);
        rv_area = dialog.findViewById(R.id.rv_area);
        rv_area.setVisibility(View.VISIBLE);
        lout_caketype.setVisibility(View.GONE);
       // ArrayList<String> arrayList = new ArrayList<>();
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        Display display = activity.getWindowManager().getDefaultDisplay();
       // int height =  (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        dialog.getWindow().setWindowAnimations(R.style.MaterialDialogSheetAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        RequestModel requestModel = new RequestModel();
        requestModel.setTOKEN(getString(R.string.TOKEN));
        showprogressdialog();
        String type = "CakeDetailActivity";
        new AreaAsync(CakeDetailActivity.this, requestModel,type);

    }

    public void successresponsecart(ResponseModel response) {

      //  Toast.makeText(activity,response.getStatus(),Toast.LENGTH_LONG).show();
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            startActivity(new Intent(CakeDetailActivity.this,CartActivity.class));
            if (!dialog.isShowing()) {
                dialog.show();
            }

        }
        else
        {
            Toast.makeText(CakeDetailActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

    }


    private class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.Holder> {
        Activity activity;
        ArrayList<ListData_Model> arrayList;

        public AreaListAdapter(Activity activity, ArrayList<ListData_Model> arrayList) {

            this.activity = activity;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(activity).inflate(R.layout.row_area, viewGroup, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, final int i) {

            holder.tv_pincode.setText(arrayList.get(i).getAREA_PINCODE());

            holder.tv_pincode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edt_areaname.setText(arrayList.get(i).getAREA_NAME());
                    edt_pincode.setText(arrayList.get(i).getAREA_PINCODE());
                    dialog.dismiss();
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView tv_pincode;

            public Holder(@NonNull View itemView) {
                super(itemView);
                tv_pincode = itemView.findViewById(R.id.tv_pincode);
            }
        }
    }



    public void successresponse(ResponseModel response) {

        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            Area_Array = new ArrayList<ListData_Model>();
            Area_Array.addAll(response.getList());
            //Log.e("Area_Array",""+  Area_Array.get(5).getAREA_NAME());

            rv_area.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            adapter = new AreaListAdapter(activity, Area_Array);
            rv_area.setAdapter(adapter);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
        else
        {
             Toast.makeText(CakeDetailActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}
