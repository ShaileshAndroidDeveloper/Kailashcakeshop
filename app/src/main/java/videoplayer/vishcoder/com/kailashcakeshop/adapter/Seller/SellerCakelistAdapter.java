package videoplayer.vishcoder.com.kailashcakeshop.adapter.Seller;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_CakelistActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CartActivity;
import videoplayer.vishcoder.com.kailashcakeshop.adapter.Users.CartListAdapter;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.model.RequestModel;
import videoplayer.vishcoder.com.kailashcakeshop.model.ResponseModel;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiClient;
import videoplayer.vishcoder.com.kailashcakeshop.util.ApiInterface;
import videoplayer.vishcoder.com.kailashcakeshop.util.Global_App;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class SellerCakelistAdapter extends RecyclerView.Adapter<SellerCakelistAdapter.Holder> {
    Activity activity;
    ArrayList<ListData_Model> arrayList;

    public SellerCakelistAdapter(Activity activity, ArrayList<ListData_Model> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SellerCakelistAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_sellercakelist, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerCakelistAdapter.Holder holder, final int i) {

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

        holder.tv_cakeid.setText(arrayList.get(i).getCAKE_ID());
        holder.tv_caketitle.setText(arrayList.get(i).getCAKE_TITLE());
        holder.tv_caketype.setText(arrayList.get(i).getCAKE_TYPE());
        holder.tv_cakeprice.setText(arrayList.get(i).getCAKE_RATE());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cart_Id = arrayList.get(i).getCAKE_ID();
                RequestModel requestModel = new RequestModel();
                requestModel.setCAKE_ID(Cart_Id);
                new RemoveCakeAsync(activity, requestModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_cakeid, tv_caketitle, tv_caketype, tv_cakeprice;
        Button btn_delete;
        ImageView img_cake;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_cakeid = itemView.findViewById(R.id.tv_cakeid);
            tv_caketitle = itemView.findViewById(R.id.tv_caketitle);
            tv_caketype = itemView.findViewById(R.id.tv_caketype);
            tv_cakeprice = itemView.findViewById(R.id.tv_cakeprice);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            img_cake = itemView.findViewById(R.id.img_cake);
        }
    }

    private class RemoveCakeAsync {
        private Activity activity;
        private RequestModel requestModel;
        public RemoveCakeAsync(final Activity activity, RequestModel requestModel) {
            this.activity = activity;
            this.requestModel = requestModel;
            if (Utility.checkInternetConnection(activity)) {
                ((Seller_CakelistActivity) activity).showprogressdialog();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put(Global_App.CAKE_ID,requestModel.getCAKE_ID());
                    Call<ResponseModel> call = apiService.getRemoveCakeResponse(jsonObject.toString());
                    // Log.e("Register", "call.request=> " + call.request());
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {


                            onpostExecute(response.body());
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            // Log.d("AAAAAAA", "onResponse - Status : " + t );
                            ((Seller_CakelistActivity) activity).progressDialogdismiss();
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

            ((Seller_CakelistActivity) activity).successresponseremove(response);

        }
        else
        {
            Toast.makeText(activity, "fails",Toast.LENGTH_LONG).show();
            ((Seller_CakelistActivity) activity).progressDialogdismiss();

        }

    }
}
