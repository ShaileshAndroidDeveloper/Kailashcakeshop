package videoplayer.vishcoder.com.kailashcakeshop.adapter.Users;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CartActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.Holder> {
    Activity activity;
    ArrayList<ListData_Model> arrayList;

    public CartListAdapter(CartActivity activity, ArrayList<ListData_Model> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CartListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_cart, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.Holder holder, final int i) {


        //double finaltotal ;
        double rate = Integer.parseInt(arrayList.get(i).getCAKE_RATE());
        double quantity = Integer.parseInt(arrayList.get(i).getQUANTITY());
        double Subtotal = rate * quantity;
        // Global_App.finaltotal  += Subtotal;
        holder.tv_subtotal.setText(activity.getResources().getString(R.string.RS) + "" + Subtotal);


        holder.tv_title.setText(arrayList.get(i).getCAKE_TITLE());
        holder.tv_price.setText(activity.getResources().getString(R.string.RS) + arrayList.get(i).getCAKE_RATE());
        holder.tv_quantity.setText(arrayList.get(i).getQUANTITY());

        //holder.tv_title.setText(arrayList.get(i).getCAKE_RATE());
        String Image = arrayList.get(i).getCAKE_IMAGE();
        if (!Utility.isEmpty(arrayList.get(i).getCAKE_IMAGE())) {
            Picasso.with(activity)
                    .load(Image)
                    .error(R.drawable.ic_no_img)
                    .placeholder(R.drawable.ic_no_img)
                    .into(holder.img_cake);
        } else {
            holder.img_cake.setImageResource(R.drawable.ic_no_img);
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cart_Id = arrayList.get(i).getCART_ID();
                RequestModel requestModel = new RequestModel();
                requestModel.setCartid(Cart_Id);
                new RemoveCartAsync(activity, requestModel);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_price, tv_quantity, tv_subtotal;
        ImageView img_cake, img_delete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_subtotal = itemView.findViewById(R.id.tv_subtotal);
            img_cake = itemView.findViewById(R.id.img_cake);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }

    private class RemoveCartAsync {
        private Activity activity;
        private RequestModel requestModel;

        public RemoveCartAsync(final Activity activity, RequestModel requestModel) {
            this.activity = activity;
            this.requestModel = requestModel;
            if (Utility.checkInternetConnection(activity)) {
                ((CartActivity) activity).showprogressdialog();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put(Global_App.CART_ID,requestModel.getCartid());
                    Call<ResponseModel> call = apiService.getRemoveCartResponse(jsonObject.toString());
                   // Log.e("Register", "call.request=> " + call.request());
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

//                    Log.d("AAAAAAA", "onResponse - Status : " + response.code() + response.errorBody().toString());
                            onpostExecute(response.body());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                           // Log.d("AAAAAAA", "onResponse - Status : " + t );
                            ((CartActivity) activity).progressDialogdismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onpostExecute(ResponseModel response) {

        if (response != null)
        {

            ((CartActivity) activity).successresponseremove(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((CartActivity) activity).progressDialogdismiss();

        }

    }
}
