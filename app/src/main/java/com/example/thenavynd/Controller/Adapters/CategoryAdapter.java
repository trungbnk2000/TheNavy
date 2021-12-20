package com.example.thenavynd.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thenavynd.Controller.Fragments.AccessoriesFragment;
import com.example.thenavynd.Controller.Fragments.HandbagsFragment;
import com.example.thenavynd.Controller.Fragments.LatestArrivedFragment;
import com.example.thenavynd.Controller.Fragments.ShoesFragment;
import com.example.thenavynd.Models.Categories;
import com.example.thenavynd.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Categories> categoriesList;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Categories> list){
        categoriesList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Categories category = categoriesList.get(position);

        if(category == null){
            return;
        }

        new ProductAdapter.DownLoadImageTask(holder.img).execute(category.getImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test","" + category.getId() + "..." + category.getName());
                if(category.getId() == 0){
                    Fragment fragment = new HandbagsFragment();
                    loadFragment(fragment);
                }
                else if(category.getId() == 2){
                    Fragment fragment = new LatestArrivedFragment();
                    loadFragment(fragment);
                }
                else if(category.getId() == 5){
                    Fragment fragment = new ShoesFragment();
                    loadFragment(fragment);
                }
                else if(category.getId() == 4){
                    Fragment fragment = new AccessoriesFragment();
                    loadFragment(fragment);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView name;
        private CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_category);
            img = itemView.findViewById(R.id.image_category);
            name = itemView.findViewById(R.id.name_category);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
