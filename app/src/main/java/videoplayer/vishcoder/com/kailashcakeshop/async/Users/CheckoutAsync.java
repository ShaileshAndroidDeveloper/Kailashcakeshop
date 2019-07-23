package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CheckOutActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class CheckoutAsync {
    private Activity activity;
    private RequestModel requestModel;


    public CheckoutAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put(Global_App.u_id,requestModel.getU_id());
                jsonObject.put(Global_App.FIRSTNAME,requestModel.getFirst_name());
                jsonObject.put(Global_App.LASTNAME,requestModel.getLast_name());
                jsonObject.put(Global_App.ADDRESS,requestModel.getAddress());
                jsonObject.put(Global_App.AREA,requestModel.getArea());
                jsonObject.put(Global_App.PINCODE,requestModel.getPINCODE());
                jsonObject.put(Global_App.DELIVERYDATE,requestModel.getDELIVERYDATE());
                jsonObject.put(Global_App.DELIVERYTIME,requestModel.getDELIVERYTIME());
                jsonObject.put(Global_App.MOBILE,requestModel.getMobile_no());

                Call<ResponseModel> call = apiService.getCheckoutResponse(jsonObject.toString());
               // Log.e("Register", "call.request=> " + call.request());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

//                        Log.d("AAAAAAA", "onResponse - Status : " + response.code() + response.errorBody().toString());
                        onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        ((CheckOutActivity) activity).progressDialogdismiss();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void onpostExecute(ResponseModel response) {

//        Toast.makeText(activity, response.getStatus(),Toast.LENGTH_LONG).show();
        if (response != null)
        {

            ((CheckOutActivity) activity).successresponsecheck(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((CheckOutActivity) activity).progressDialogdismiss();

        }

    }
}
