package videoplayer.vishcoder.com.kailashcakeshop.adapter.Seller;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Seller.Seller_OrderdetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.OrdedetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class SellerOrderAdapter extends RecyclerView.Adapter<SellerOrderAdapter.Holder> {
    Activity activity;
    ArrayList<ListData_Model> arrayList;

    public SellerOrderAdapter(Activity activity, ArrayList<ListData_Model> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SellerOrderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_sellerorder, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerOrderAdapter.Holder holder, final int i) {
            holder.tv_ordertitle.setText(arrayList.get(i).getCAKE_TITLE());
            holder.tv_orderno.setText(arrayList.get(i).getORDERID());
              holder.tv_caketype.setText(arrayList.get(i).getCAKE_TYPE());

        int status = Integer.parseInt(arrayList.get(i).getORDERSTATUS());

        if (status == 0)
        {
            holder.lout_status.setVisibility(View.VISIBLE);
        }else
        {
            holder.lout_status.setVisibility(View.GONE);
        }

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

        holder.Lout_MyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, Seller_OrderdetailActivity.class);
                in.putExtra("ORDER_ID",arrayList.get(i).getORDERID());

                activity.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_ordertitle,tv_orderno,tv_caketype;
        Button Lout_MyOrder;
        ImageView img_cake;
        LinearLayout lout_status;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_ordertitle = itemView.findViewById(R.id.tv_ordertitle);
            tv_orderno = itemView.findViewById(R.id.tv_orderno);
            Lout_MyOrder = itemView.findViewById(R.id.Lout_MyOrder);
            img_cake = itemView.findViewById(R.id.img_cake);
            tv_caketype = itemView.findViewById(R.id.tv_caketype);
            lout_status = itemView.findViewById(R.id.lout_status);
        }
    }
}
