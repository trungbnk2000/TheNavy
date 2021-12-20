package com.example.thenavynd.Controller.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thenavynd.Controller.Activities.DetailProductActivity;
import com.example.thenavynd.Models.Products;
import com.example.thenavynd.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    Context context;
    private List<Products> productsList;
    private List<Products> productsListOld;


    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Products> list){
        this.productsList = list;
        this.productsListOld = list;
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
        new DownLoadImageTask(holder.imageView).execute(product.getImage());
        holder.textView.setText(product.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchToDetail(product);
            }
        });
    }

    private void SwitchToDetail(Products product) {
        Intent intent = new Intent(context, DetailProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("_product", product);
        intent.putExtras(bundle);
        context.startActivity(intent);
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
       private CardView cardView;

       public ProductViewHolder(@NonNull View itemView) {
           super(itemView);
           cardView = itemView.findViewById(R.id.card_item);
           imageView = itemView.findViewById(R.id.item_image);
           textView = itemView.findViewById(R.id.item_text);
       }
   }

    public static class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    productsList = productsListOld;
                }
                else{
                    List<Products> list = new ArrayList<>();
                    for(Products p : productsListOld){
                        if(p.getName().toLowerCase().contains(search.toLowerCase())){
                            list.add(p);
                        }
                    }
                    productsList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productsList = (List<Products>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
