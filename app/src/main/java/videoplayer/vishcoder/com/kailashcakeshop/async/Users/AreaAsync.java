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
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CakeDetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CheckOutActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class AreaAsync {

    private Activity activity;
    String type;
    private RequestModel requestModel;

    public AreaAsync(final Activity activity, RequestModel requestModel,String type) {
        this.type = type;
        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Global_App.TOKEN,requestModel.getTOKEN());
                Call<ResponseModel> call = apiService.getAreaResponse(jsonObject.toString());
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
                        ((CheckOutActivity) activity).progressDialogdismiss();
                    }
                });
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void onpostExecute(ResponseModel response) {
        if (response != null)
        {
            if(type.equals("CheckOutActivity"))
            {
                ((CheckOutActivity) activity).successresponse(response);
            }
            else
            {
                ((CakeDetailActivity) activity).successresponse(response);
            }


        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();

            if(type.equals("CheckOutActivity"))
            {
                ((CheckOutActivity) activity).progressDialogdismiss();
            }
            else
            {
                ((CakeDetailActivity) activity).progressDialogdismiss();
            }
         //   ((CheckOutActivity) activity).progressDialogdismiss();

        }
    }
}
