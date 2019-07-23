package videoplayer.vishcoder.com.kailashcakeshop.async.Users;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CakeDetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class AddToCartAsync {
    private Activity activity;
    private RequestModel requestModel;
    Uri uri;

    public AddToCartAsync(final Activity activity, RequestModel requestModel,Uri uri) {
        this.activity = activity;
        this.requestModel = requestModel;
        this.uri = uri;
        if (Utility.checkInternetConnection(activity)) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put(Global_App.QUANTITY,requestModel.getQUANTITY());
                jsonObject.put(Global_App.MESSAGE,requestModel.getMessage());
                jsonObject.put(Global_App.CAKE_ID,requestModel.getCAKE_ID());
                jsonObject.put(Global_App.u_id,requestModel.getU_id());
                jsonObject.put(Global_App.CARTSTATUS,requestModel.getCARTSTATUS());
                if(uri != null)
                {
                    String filePath = Utility.getRealPathFromURIPath(uri, activity);
                    File file = new File(filePath);
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                    Call<ResponseModel> call = apiService.getAddToCartPhotoCakeResponse(fileToUpload,filename,jsonObject.toString());
                    Log.e("addtocart", "call.request=> " + call.request());
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                             Log.d("onResponse", "onRespeons - Status : " + response.code() + response.errorBody().toString());
                            onpostExecute(response.body());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                          Toast.makeText(activity,"Check Your Internet Connection....",Toast.LENGTH_LONG).show();
                            ((CakeDetailActivity) activity).progressDialogdismiss();
                        }
                    });
                }
                else
                    {
                        Call<ResponseModel> call = apiService.getAddToCartResponse(jsonObject.toString());
                        call.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                                onpostExecute(response.body());
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                             //   Log.d("onFailure", "onResponse - Status : " + t );
                                Toast.makeText(activity,"Check Your Internet Connection....",Toast.LENGTH_LONG).show();
                                ((CakeDetailActivity) activity).progressDialogdismiss();
                            }
                        });
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void onpostExecute(ResponseModel response) {
        if (response != null)
        {

            ((CakeDetailActivity) activity).successresponsecart(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((CakeDetailActivity) activity).progressDialogdismiss();

        }
    }
}
