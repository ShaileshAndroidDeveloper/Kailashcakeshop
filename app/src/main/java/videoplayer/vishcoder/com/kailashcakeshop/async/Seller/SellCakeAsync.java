package videoplayer.vishcoder.com.kailashcakeshop.async.Seller;

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
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_SellCakeActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.RegisterActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

import static android.content.ContentValues.TAG;

public class SellCakeAsync {

    private Activity activity;
    private RequestModel requestModel;
    private Uri uri;

    public SellCakeAsync(final Activity activity, RequestModel requestModel, Uri uri) {
        this.activity = activity;
        this.requestModel = requestModel;
        this.uri = uri;

        if (Utility.checkInternetConnection(activity)) {


            String filePath = Utility.getRealPathFromURIPath(uri, activity);


            File file = new File(filePath);
            Log.d("filePath", "Filename " +filePath
            );
            Log.d("Filename", "Filename " + file.getName());
            //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put(Global_App.CAKE_TYPE,requestModel.getCaketype());
                jsonObject.put(Global_App.CAKE_TITLE,requestModel.getCaketitle());
                jsonObject.put(Global_App.CAKE_DETAIL,requestModel.getCakedetail());
                jsonObject.put(Global_App.CAKE_FLAVOUR,requestModel.getCakeflavour());
                jsonObject.put(Global_App.CAKE_RATE,requestModel.getCakerate());
                jsonObject.put(Global_App.Seller_Id,requestModel.getSellerid());
                //  jsonObject.put(Global_App.FIRST_NAME,requestModel.getFirst_name());
                Call<ResponseModel> call = apiService.getSellCakeResponse(fileToUpload,filename,jsonObject.toString());
                Log.e("aaaaaaa", "call.request=> " + call.request());
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                  //  Log.d("onResponse", "onRespeons - Status : " + response.code() + response.errorBody().toString());
                        ((Seller_SellCakeActivity) activity).progressDialogdismiss();

                         onpostExecute(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("onFailure", "onResponse - Status : " + t );
                        ((Seller_SellCakeActivity) activity).progressDialogdismiss();
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

           ((Seller_SellCakeActivity) activity).successresponse(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((Seller_SellCakeActivity) activity).progressDialogdismiss();
        }

    }


}
