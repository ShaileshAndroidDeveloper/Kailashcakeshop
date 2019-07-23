package videoplayer.vishcoder.com.kailashcakeshop.async.Seller;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_CakelistActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_OrderActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CartActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class SellerCakelistAsync {
    private Activity activity;
    private RequestModel requestModel;
    public SellerCakelistAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put(Global_App.TOKEN,requestModel.getTOKEN());
                Call<ResponseModel> call = apiService.getsellerCakeListResponse(jsonObject.toString());
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

            ((Seller_CakelistActivity) activity).successresponse(response);

        }
        else
        {
            ((Seller_CakelistActivity) activity).progressDialogdismiss();

        }

    }
}
