package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CartActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.OrdedetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class OrderdetailAsync {
    private Activity activity;
    private RequestModel requestModel;

    public OrderdetailAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put(Global_App.ORDERID,requestModel.getORDERID());
                Call<ResponseModel> call = apiService.getOrderdetailResponse(jsonObject.toString());
                Log.e("Register", "call.request=> " + call.request());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {


                        onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        ((CartActivity) activity).progressDialogdismiss();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void onpostExecute(ResponseModel response) {
        if (response != null)
        {

            ((OrdedetailActivity) activity).successresponse(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((OrdedetailActivity) activity).progressDialogdismiss();

        }
    }
}
