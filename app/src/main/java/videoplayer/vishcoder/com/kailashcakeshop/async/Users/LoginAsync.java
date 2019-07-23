package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.LoginActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class LoginAsync {
    Activity activity;
    RequestModel requestModel;
    public LoginAsync(final Activity activity, RequestModel requestModel) {

        this.activity = activity;
        this.requestModel = requestModel;
        if (Utility.checkInternetConnection(activity)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put(Global_App.EMAIL,requestModel.getEmail());
             //
                  jsonObject.put(Global_App.PASSWORD,requestModel.getPassword());

                Call<ResponseModel> call = apiService.getLoginResponse(jsonObject.toString());
                Log.e("Register", "call.request=> " + call.request());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

//                        Log.d("AAAAAAA", "onResponse - Status : " + response.code() + response.errorBody().toString());
                        onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("AAAAAAA", "onResponse - Status : " + t );
                        ((LoginActivity) activity).progressDialogdismiss();
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

            ((LoginActivity) activity).successresponse(response);

        }
        else
        {
          //  Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((LoginActivity) activity).progressDialogdismiss();

        }
    }

}
