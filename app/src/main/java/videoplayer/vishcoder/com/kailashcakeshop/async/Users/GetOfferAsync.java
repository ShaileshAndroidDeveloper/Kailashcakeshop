package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_OrderActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CartActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.DashboardActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class GetOfferAsync {
    private Activity activity;
    private RequestModel requestModel;

    public GetOfferAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put(Global_App.TOKEN,requestModel.getTOKEN());
                Call<ResponseModel> call = apiService.getOfferResponse(jsonObject.toString());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                        ((DashboardActivity) activity).progressDialogdismiss();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            ((DashboardActivity) activity).progressDialogdismiss();
             Toast.makeText(activity,"Check Internet Connection....",Toast.LENGTH_LONG).show();
        }

    }


    private void onpostExecute(ResponseModel response) {
        if (response != null)
        {

            ((DashboardActivity) activity).successresponse(response);

        }
        else
        {
            ((DashboardActivity) activity).progressDialogdismiss();

        }

    }
}
