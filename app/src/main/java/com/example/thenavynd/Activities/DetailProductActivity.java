package com.example.thenavynd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thenavynd.Adapters.ProductAdapter;
import com.example.thenavynd.Models.Products;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailProductActivity extends AppCompatActivity {

    TextView name, id, price, soLuong, moTa, categoryId, countItem;
    ImageView image, addItem, removeItem,cart;
    Spinner spinner;
    Button addToCart;
    int totalCount = 1, totalPrice;
    Products product;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name_detail);
        id = findViewById(R.id.id_detail);
        price = findViewById(R.id.price_detail);
        soLuong = findViewById(R.id.soLuong_detail);
        moTa = findViewById(R.id.moTa_detail);
        image = findViewById(R.id.image_detail);
        categoryId = findViewById(R.id.category_detail);
        countItem = findViewById(R.id.count_item_detail);
        addItem = findViewById(R.id.add_item_button);
        removeItem = findViewById(R.id.remove_item_button);
        spinner = findViewById(R.id.size_detail);
        addToCart = findViewById(R.id.add_to_card_detail);
        cart = findViewById(R.id.cart_detail);

        //Hiển thị dropdown size
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailProductActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sizes));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //Nhận bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Products p = (Products) bundle.get("_product");
        product = p;
        name.setText(p.getName());
        id.setText("Product#" + String.valueOf(p.getId()));
        price.setText(p.getPrice() + " VNĐ");
        soLuong.setText("Số lượng : " + String.valueOf(p.getSoLuong()));
        moTa.setText("Mô tả sản phẩm : " + p.getMoTa());
        categoryId.setText("Danh mục : " + String.valueOf(p.getCategoryId()));

        new ProductAdapter.DownLoadImageTask(image).execute(p.getImage());
        totalPrice = Integer.parseInt(p.getPrice()) * totalCount;

        //Cài đặt thêm xóa sản phẩm
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCount < 10){
                    totalCount++;
                    countItem.setText(String.valueOf(totalCount));
                    totalPrice = Integer.parseInt(p.getPrice()) * totalCount;
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCount > 0){
                    totalCount--;
                    countItem.setText(String.valueOf(totalCount));
                    totalPrice = Integer.parseInt(p.getPrice()) * totalCount;
                }
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCart();
            }
        });


        //Cài đặt thêm vào giỏ hàng
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });
    }

    private void switchToCart() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName", product.getName());
        cartMap.put("productImage", product.getImage());
        cartMap.put("productPrice", product.getPrice());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("productCount", countItem.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailProductActivity.this, "Added to card !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}