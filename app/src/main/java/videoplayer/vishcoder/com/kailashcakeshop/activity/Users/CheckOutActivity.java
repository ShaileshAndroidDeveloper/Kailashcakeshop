package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.Calendar;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.AreaAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.CheckoutAsync;
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

public class CheckOutActivity extends AppCompatActivity {
    private int hour;
    private int minute;
    private static ArrayList<ListData_Model> Area_Array;
    ImageView imgDrawer, imgCart, imgMenu;
    private Toolbar toolbar;
    Utility utility;
    CheckOutActivity activity;
    EditText edt_firstname, edt_lastname, edt_address, edt_area, edt_pincode, edt_deliverydate, edt_deliverytime, edt_mobile;
    PowerMenu Menu;

    static final int TIME_DIALOG_ID = 1111;
    int year;
    int month;
    int day;
    public EDProgressDialog progressDialog;
    private Dialog dialog;
    TextView Txt_Title, Txt_Back, Txt_Done;
    RecyclerView rv_area;
    private AreaListAdapter adapter;
    RelativeLayout lout_rmain;
    Button btn_continue;
    String s_firstname, s_lastname, s_address, s_area, s_pincode, s_date, s_time, s_mobile, s_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        utility = new Utility();
        activity = CheckOutActivity.this;

        initView();
        findViewById();
        setView();

    }

    private void findViewById() {

        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_address = findViewById(R.id.edt_address);
        edt_area = findViewById(R.id.edt_area);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_deliverydate = findViewById(R.id.edt_deliverydate);
        edt_deliverytime = findViewById(R.id.edt_deliverytime);
        edt_mobile = findViewById(R.id.edt_mobile);
        btn_continue = findViewById(R.id.btn_continue);
        lout_rmain = findViewById(R.id.lout_rmain);
    }

    private void setView() {
        setmenu();
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
                s_user_id = login.getString("userid", null);
                if (s_user_id.equals("")) {

                    startActivity(new Intent(CheckOutActivity.this, LoginActivity.class));
                    finish();
                } else {


                    s_firstname = edt_firstname.getText().toString().trim();
                    s_lastname = edt_lastname.getText().toString().trim();
                    s_address = edt_address.getText().toString().trim();
                    s_date = edt_deliverydate.getText().toString().trim();
                    s_time = edt_deliverytime.getText().toString().trim();
                    s_mobile = edt_mobile.getText().toString().trim();
                    s_pincode = edt_pincode.getText().toString().trim();
                    s_area = edt_area.getText().toString().trim();
                    Check_Validation(s_firstname, s_lastname, s_address, s_date, s_time, s_pincode, s_area, s_mobile);

                }

            }
        });


        edt_deliverytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  edt_time.setText(dateFormat);
                showDialog(TIME_DIALOG_ID);
            }
        });
        edt_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodedialog();
            }
        });

        edt_pincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincodedialog();
            }
        });

        edt_deliverydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                year = mcurrentDate.get(Calendar.YEAR);
                month = mcurrentDate.get(Calendar.MONTH);
                day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(CheckOutActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        edt_deliverydate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
                        int month_k = selectedmonth + 1;

                    }
                }, year, month, day);
                mDatePicker.setTitle("Please select date");
                // TODO Hide Future Date Here
                //  mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();
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
                    i = new Intent(CheckOutActivity.this,SplashActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case "My Account":
                    //  i = new Intent(DashboardActivity.this,DashboardActivity.class);
                    //  startActivity(i);
                    //  finish();
                    break;

                case "My Order":
                    i = new Intent(CheckOutActivity.this,MyOrderActivity.class);
                    startActivity(i);
                    finish();
                    break;

                case "Login / Signup":
                    i = new Intent(CheckOutActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;

            }

            Menu.setSelectedPosition(position); // change selected item
            Menu.dismiss();

        }
    };


    private void Check_Validation(String s_firstname, String s_lastname, String s_address, String s_date, String s_time, String s_pincode, String s_area, String s_mobile) {
        if (s_firstname.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter First Name....");
        } else if (s_lastname.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Last Name....");
        } else if (s_address.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Address.....");
        } else if (s_area.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Select Area....");
        } else if (s_pincode.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Select Pincode....");
        } else if (s_date.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Select Delivery Date");
        } else if (s_time.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Select Delivery Time....");
        }else if (s_mobile.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Enter Mobile Number");
        } else if (Utility.IsLength(s_mobile, 10)) {
            Utility.SnackBar(lout_rmain, "Enter 10 Digit Mobile Number");
        } else if (!Utility.isValidMob(s_mobile)) {
            Utility.SnackBar(lout_rmain, "Enter Valid Mobile Number");
        }else if (s_user_id.length() <= 0) {
            Utility.SnackBar(lout_rmain, "Some thing Went Wrong restart App...");
            startActivity(new Intent(CheckOutActivity.this, LoginActivity.class));
        }
        else {

            RequestModel requestModel = new RequestModel();
            requestModel.setFirst_name(s_firstname);
            requestModel.setLast_name(s_lastname);
            requestModel.setAddress(s_address);
            requestModel.setArea(s_area);
            requestModel.setPINCODE(s_pincode);

            requestModel.setDELIVERYDATE(s_date);
            requestModel.setDELIVERYTIME(s_time);
            requestModel.setMobile_no(s_mobile);
            requestModel.setU_id(s_user_id);

            showprogressdialog();
            new CheckoutAsync(CheckOutActivity.this, requestModel);

        }
    }


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
            hour = hourOfDay;
            minute = minutes;

            updateTime(hour, minute);

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

        edt_deliverytime.setText(aTime);
    }


    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("Checkout");
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


            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckOutActivity.this,CartActivity.class));
            }
        });


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
        String type = "CheckOutActivity";
        new AreaAsync(CheckOutActivity.this, requestModel,type);

    }

    public void successresponsecheck(ResponseModel response) {
        if(response.getStatus().equals(Global_App.STATUS_SUCCESS))
        {
            progressDialog.dismiss();
            Toast.makeText(CheckOutActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(CheckOutActivity.this,DashboardActivity.class));
            finish();
         //   startActivity(new Intent(CheckOutActivity.this,LoginActivity.class));
        }
        else
        {
            Toast.makeText(CheckOutActivity.this,response.getMessage(),Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    private class AreaListAdapter extends RecyclerView.Adapter<CheckOutActivity.AreaListAdapter.Holder> {
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
        public void onBindViewHolder(@NonNull final CheckOutActivity.AreaListAdapter.Holder holder, final int i) {

            holder.tv_pincode.setText(arrayList.get(i).getAREA_PINCODE());

            holder.tv_pincode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edt_area.setText(arrayList.get(i).getAREA_NAME());
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

        if (response.getStatus().equals(Global_App.STATUS_SUCCESS)) {
            progressDialog.dismiss();
            Area_Array = new ArrayList<ListData_Model>();
            Area_Array.addAll(response.getList());

            rv_area.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            adapter = new AreaListAdapter(activity, Area_Array);
            rv_area.setAdapter(adapter);
            if (!dialog.isShowing()) {
                dialog.show();
            }

        } else {
            Toast.makeText(CheckOutActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(CheckOutActivity.this);

        progressDialog.show();
    }

    public void progressDialogdismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
