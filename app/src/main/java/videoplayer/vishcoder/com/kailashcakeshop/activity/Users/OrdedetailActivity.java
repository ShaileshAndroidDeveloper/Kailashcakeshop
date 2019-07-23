package videoplayer.vishcoder.com.kailashcakeshop.activity.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.CartListnAsync;
import videoplayer.vishcoder.com.kailashcakeshop.async.Users.OrderdetailAsync;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.Custom.EDProgressDialog;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;

public class OrdedetailActivity extends AppCompatActivity {
    ImageView imgDrawer,imgCart,imgMenu;
    private Toolbar toolbar;
    String ORDER_ID;
    public EDProgressDialog progressDialog;
    TextView tv_orderid,tv_deliverytime,tv_deliverydate,tv_orderdatetime,tv_caketitle,tv_caketype,tv_cakeflavour,tv_cakerate,
            tv_quantity,tv_subtotal,tv_firstname,tv_lastname,tv_address,tv_area,tv_pincode,tv_mobile,tv_cakemassage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordedetail);
        initView();
        findViewById();
        Cleartdata();
        try {
            if (!getIntent().getStringExtra("ORDER_ID").equals(""))
            {
                ORDER_ID = getIntent().getStringExtra("ORDER_ID");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        setview();
    }

    private void Cleartdata() {
        tv_orderid.setText("");
        tv_deliverytime.setText("");
        tv_deliverydate.setText("");
        tv_orderdatetime.setText("");
        tv_caketitle.setText("");
        tv_caketype.setText("");
        tv_cakeflavour.setText("");
        tv_cakerate.setText("");
        tv_quantity.setText("");
        tv_subtotal.setText("");
        tv_firstname.setText("");
        tv_lastname.setText("");
        tv_address.setText("");
        tv_area.setText("");
        tv_pincode.setText("");
        tv_mobile.setText("");
        tv_cakemassage.setText("");
    }

    private void setview() {

        if(ORDER_ID.equals(""))
        {
           Intent i = new Intent(OrdedetailActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            RequestModel requestModel = new RequestModel();
            requestModel.setORDERID(ORDER_ID);
            showprogressdialog();
            new OrderdetailAsync(OrdedetailActivity.this, requestModel);

        }

    }

    private void findViewById() {


        tv_orderid =findViewById(R.id.tv_orderid);
        tv_deliverytime =findViewById(R.id.tv_deliverytime);
        tv_deliverydate =findViewById(R.id.tv_deliverydate);
        tv_orderdatetime =findViewById(R.id.tv_orderdatetime);
        tv_caketitle =findViewById(R.id.tv_caketitle);
        tv_caketype =findViewById(R.id.tv_caketype);
        tv_cakeflavour =findViewById(R.id.tv_cakeflavour);
        tv_cakerate =findViewById(R.id.tv_cakerate);
        tv_quantity =findViewById(R.id.tv_quantity);
        tv_subtotal =findViewById(R.id.tv_subtotal);
        tv_firstname =findViewById(R.id.tv_firstname);
        tv_lastname =findViewById(R.id.tv_lastname);
        tv_address =findViewById(R.id.tv_address);
        tv_area =findViewById(R.id.tv_area);
        tv_pincode =findViewById(R.id.tv_pincode);
        tv_mobile =findViewById(R.id.tv_mobile);
        tv_cakemassage=findViewById(R.id.tv_cakemassage);


    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHeaderView("ORDER DETAIL");
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
                onBackPressed();
            }
        });
imgMenu.setVisibility(View.GONE);
        imgCart.setVisibility(View.GONE);
    }

    private void showprogressdialog() {

        progressDialog = new EDProgressDialog(OrdedetailActivity.this);

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
            tv_orderid.setText(response.getORDERID());
            tv_deliverytime.setText(response.getDELIVERYTIME());
            tv_deliverydate.setText(response.getDELIVERYDATE());
            tv_orderdatetime.setText(response.getORDERDATETIME());
            tv_caketitle.setText(response.getCAKE_TITLE());
            tv_caketype.setText(response.getCAKE_TYPE());
            tv_cakeflavour.setText(response.getCAKE_FLAVOUR());
            tv_cakerate.setText(response.getCAKE_RATE());
            tv_quantity.setText(response.getQUANTITY());
            tv_cakemassage.setText(response.getMESSAGE());

            double rate = Integer.parseInt(response.getCAKE_RATE());
            double quantity = Integer.parseInt(response.getQUANTITY());
            double Subtotal = rate * quantity;
            tv_subtotal.setText(getResources().getString(R.string.RS) + "" + Subtotal);

            tv_firstname.setText(response.getFIRSTNAME());
            tv_lastname.setText(response.getLASTNAME());
            tv_address.setText(response.getADDRESS());
            tv_area.setText(response.getAREA());
            tv_pincode.setText(response.getPINCODE());
            tv_mobile.setText(response.getMOBILE());

        }
        else
        {
            progressDialog.dismiss();
        }
    }

}
