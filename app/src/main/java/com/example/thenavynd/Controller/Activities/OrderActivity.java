package com.example.thenavynd.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thenavynd.Models.Carts;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    ImageView returnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        returnPrevious = findViewById(R.id.return_previous);

        List<Carts> cartsList = (List<Carts>) getIntent().getSerializableExtra("cartsList");

        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(cartsList != null && cartsList.size()>0){
            for(Carts c : cartsList){
                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName", c.getProductName());
                cartMap.put("productImage", c.getProductImage());
                cartMap.put("productPrice", c.getProductPrice());
                cartMap.put("currentDate", c.getCurrentDate());
                cartMap.put("currentTime", c.getCurrentTime());
                cartMap.put("productCount", c.getProductCount());
                cartMap.put("totalPrice", c.getTotalPrice());

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(OrderActivity.this, "Ordered successfully !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}