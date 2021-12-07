package com.example.thenavynd.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenavynd.Models.Products;
import com.example.thenavynd.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    private List<Products> productsList;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Products> list){
        this.productsList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products product = productsList.get(position);
        if(product == null){
            return;
        }

        holder.imageView.setImageResource(product.getImage());
        holder.textView.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        if(productsList != null){
            return productsList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

       private ImageView imageView;
       private TextView textView;

       public ProductViewHolder(@NonNull View itemView) {
           super(itemView);
           imageView = itemView.findViewById(R.id.item_image);
           textView = itemView.findViewById(R.id.item_text);
       }
   }
}
