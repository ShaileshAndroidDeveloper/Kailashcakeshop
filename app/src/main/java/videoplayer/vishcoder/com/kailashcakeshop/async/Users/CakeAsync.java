package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CakeActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class CakeAsync {
    private Activity activity;
    private RequestModel requestModel;

    public CakeAsync(final Activity activity, RequestModel requestModel) {
        this.activity = activity;
        this.requestModel = requestModel;

        if (Utility.checkInternetConnection(activity)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Global_App.CAKE_TYPE,requestModel.getCaketype());
                Call<ResponseModel> call = apiService.getCakeResponse(jsonObject.toString());
                Log.e("call", "call.request=> " + call.request());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    //  Log.d("AAAAAAA", "onResponse - Status : " + response.code() + response.errorBody().toString());
                        onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                  //      Log.d("AAAAAAA", "onResponse - Status : " + response.code() + response.errorBody().toString());
                       Log.d("AAAAAAA", "onResponse - Status : " + t );
                        ((CakeActivity) activity).progressDialogdismiss();
                    }
                });
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void onpostExecute(ResponseModel response) {

//        Toast.makeText(activity, response.getStatus(),Toast.LENGTH_LONG).show();
        if (response != null)
        {

            ((CakeActivity) activity).successresponse(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((CakeActivity) activity).progressDialogdismiss();

        }

    }
}
