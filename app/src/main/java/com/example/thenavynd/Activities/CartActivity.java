package com.example.thenavynd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thenavynd.Adapters.CartAdapter;
import com.example.thenavynd.Models.Carts;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView total;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Carts> cartsList;
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        cartsList = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this,cartsList);
        recyclerView = findViewById(R.id.recycler_view_cart);
        total = findViewById(R.id.total_price_cart);
        order = findViewById(R.id.order_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult().getDocuments()){

                        String documentId = document.getId();

                        Carts carts1 = document.toObject(Carts.class);
                        carts1.setDocumentId(documentId);
                        Log.d("Test", "Name : " + carts1.getProductName() + "..." + carts1.getProductCount() + "..." + carts1.getCurrentTime());
                        cartsList.add(carts1);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    //Toast.makeText(CartActivity.this, "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                    Log.d("Test", "Error : " + task.getException());
                }
            }
        });

        LocalBroadcastManager.getInstance(CartActivity.this)
                .registerReceiver(broadcastReceiver, new IntentFilter("TotalCarts"));

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                intent.putExtra("cartsList", (Serializable) cartsList);
                startActivity(intent);
            }
        });
    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalCarts = intent.getIntExtra("totalCarts", 0);
            total.setText(totalCarts + " VNĐ");
        }
    };

}