package videoplayer.vishcoder.com.kailashcakeshop.adapter.Users;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import videoplayer.vishcoder.com.kailashcakeshop.R;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CakeActivity;
import videoplayer.vishcoder.com.kailashcakeshop.activity.Users.CakeDetailActivity;
import videoplayer.vishcoder.com.kailashcakeshop.model.ListData_Model;
import videoplayer.vishcoder.com.kailashcakeshop.util.Utility;

public class CakeListAdapter extends RecyclerView.Adapter<CakeListAdapter.Holder> {

    Activity activity;
    ArrayList<ListData_Model> cake_array;

    public CakeListAdapter(CakeActivity activity, ArrayList<ListData_Model> cake_array) {
        this.activity = activity;
        this.cake_array = cake_array;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_cake, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        holder.tv_title.setText(cake_array.get(i).getCAKE_TITLE());
        holder.tv_price.setText(activity.getResources().getString(R.string.RS) + cake_array.get(i).getCAKE_RATE());
        String Image = cake_array.get(i).getCAKE_IMAGE();
        if (!Utility.isEmpty(cake_array.get(i).getCAKE_IMAGE())) {
            Picasso.with(activity)
                    .load(Image)
                    .error(R.drawable.ic_no_img)
                    .placeholder(R.drawable.ic_no_img)
                    .into(holder.img_cake);
        } else {
            holder.img_cake.setImageResource(R.drawable.ic_no_img);
        }

        holder.lout_cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, CakeDetailActivity.class);
                in.putExtra("CAKE_ID", cake_array.get(i).getCAKE_ID());
                in.putExtra("CAKE_TYPE", cake_array.get(i).getCAKE_TYPE());
                in.putExtra("CAKE_TITLE", cake_array.get(i).getCAKE_TITLE());
                in.putExtra("CAKE_DETAIL", cake_array.get(i).getCAKE_DETAIL());
                in.putExtra("CAKE_FLAVOUR", cake_array.get(i).getCAKE_FLAVOUR());
                in.putExtra("CAKE_RATE", cake_array.get(i).getCAKE_RATE());
                in.putExtra("CAKE_IMAGE", cake_array.get(i).getCAKE_IMAGE());
                activity.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cake_array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_price;
        public ImageView img_cake;
        public LinearLayout lout_cake;
        public Holder(@NonNull View itemView) {
            super(itemView);
            lout_cake = itemView.findViewById(R.id.lout_cake);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            img_cake = itemView.findViewById(R.id.img_cake);
        }
    }
}
