package videoplayer.vishcoder.com.kailashcakeshop.util;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;

public interface ApiInterface {


    //region Header
    String Json_Header = "Content-Type: application/json;charset=UTF-8";
    String Query_Header = "Content-Type: application/x-www-form-urlencoded";
    //endregion


    @Headers("Content-Type: application/json")
    @POST("myorder")
    Call<ResponseModel> getMyOrderListResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("Register")
    Call<ResponseModel> getRegisterResponse(@Body String body);

    @Headers({Json_Header})
    @POST("Login")
    Call<ResponseModel> getLoginResponse(@Body String body);

    @Headers({Json_Header})
    @POST("Sellerlogin")
    Call<ResponseModel> getSellerLoginResponse(@Body String body);


    @Multipart
  //  @Headers({Json_Header})
    @POST("Sellcake")
    Call<ResponseModel> getSellCakeResponse(@Part MultipartBody.Part file, @Part("name") RequestBody name,@Part("body") String body);


    @Headers({Json_Header})
    @POST("Cakelist")
    Call<ResponseModel> getCakeResponse(@Body String body);

    @Headers({Json_Header})
    @POST("arealist")
    Call<ResponseModel> getAreaResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("addtocart")
    Call<ResponseModel> getAddToCartResponse(@Body String body);



    @Headers("Content-Type: application/json")
    @POST("cartlist")
    Call<ResponseModel> getCartListResponse(@Body String body);


    @Headers("Content-Type: application/json")
    @POST("orderdetail")
    Call<ResponseModel> getOrderdetailResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("ordercancel")
    Call<ResponseModel> getOrdercancelResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("removecart")
    Call<ResponseModel> getRemoveCartResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("checkout")
    Call<ResponseModel> getCheckoutResponse(@Body String body);

    @Multipart
    //  @Headers({Json_Header})
    @POST("addtocart")
    Call<ResponseModel> getAddToCartPhotoCakeResponse(@Part MultipartBody.Part file, @Part("name") RequestBody name,@Part("body") String body);

    @Headers("Content-Type: application/json")
    @POST("sellerorder")
    Call<ResponseModel> getsellerOrderListResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("sellercakelist")
    Call<ResponseModel> getsellerCakeListResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("removecake")
    Call<ResponseModel> getRemoveCakeResponse(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("addoffer")
    Call<ResponseModel> getAddOfferResponse(@Body String body);


    @Headers("Content-Type: application/json")
    @POST("getoffer")
    Call<ResponseModel> getOfferResponse(@Body String body);

}
