package com.example.thenavynd.Controller.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenavynd.Models.Carts;
import com.example.thenavynd.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    Context context;
    private List<Carts> cartsList;
    int totalCarts = 0;

    public OrderAdapter(Context context, List<Carts> cartsList) {
        this.context = context;
        this.cartsList = cartsList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Carts cart = cartsList.get(position);
        if(cart == null){
            return;
        }
        //Đổ dữ liệu ra adapter
        holder.name.setText(cart.getProductName());
        holder.date.setText("Ngày đặt : " + cart.getCurrentDate());
        holder.count.setText("Số lượng : " + cart.getProductCount());
        holder.price.setText(cart.getProductPrice() + " VNĐ");

        if(cart.getProductImage() != null){
            new ProductAdapter.DownLoadImageTask(holder.img).execute(cart.getProductImage());
        }
        else{
            holder.img.setImageResource(R.drawable.image_6);
        }

        //Chuyền biến tổng cart sang cart activity
        totalCarts = totalCarts + cart.getTotalPrice();
        Intent intent = new Intent("TotalCarts");
        intent.putExtra("totalCarts", totalCarts);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        if(cartsList != null){
            return cartsList.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name, count, date, price;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_product_history_order);
            name = itemView.findViewById(R.id.name_product_history_order);
            count = itemView.findViewById(R.id.count_product_history_order);
            date = itemView.findViewById(R.id.date_history_order);
            price = itemView.findViewById(R.id.price_product_history_order);
        }
    }
}
