package com.example.thenavynd.Controller.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenavynd.Models.Carts;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    private List<Carts> cartsList;
    int totalCarts = 0;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<Carts> cartsList) {
        this.context = context;
        this.cartsList = cartsList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Carts cart = cartsList.get(position);
        if(cart == null){
            return;
        }
        //Đổ dữ liệu ra adapter
        holder.productName.setText(cart.getProductName());
        holder.currentDate.setText("Ngày đặt : " + cart.getCurrentDate());
        holder.productCount.setText("Số lượng : " + cart.getProductCount());
        holder.totalPrice.setText(String.valueOf(cart.getTotalPrice()) + " VNĐ");

        if(cart.getProductImage() != null){
            new ProductAdapter.DownLoadImageTask(holder.productImage).execute(cart.getProductImage());
        }
        else{
            holder.productImage.setImageResource(R.drawable.image_6);
        }

        //Chuyền biến tổng cart sang cart activity
        totalCarts = totalCarts + cart.getTotalPrice();
        Intent intent = new Intent("TotalCarts");
        intent.putExtra("totalCarts", totalCarts);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        //Bắt sự kiện xóa item
        holder.deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cart.getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    cartsList.remove(cart);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleted !", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "Error + " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        if(cartsList != null){
            return cartsList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        //Ánh xạ
        TextView productName, currentDate, productCount, totalPrice;
        ImageView productImage, deleteCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.img_product_cart);
            productName = itemView.findViewById(R.id.name_product_cart);
            currentDate = itemView.findViewById(R.id.size_product_cart);
            productCount = itemView.findViewById(R.id.count_product_cart);
            totalPrice = itemView.findViewById(R.id.price_product_cart);
            deleteCart = itemView.findViewById(R.id.delete_cart);
        }
    }
}
