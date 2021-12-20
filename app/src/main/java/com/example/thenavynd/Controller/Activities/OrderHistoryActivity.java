package com.example.thenavynd.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thenavynd.Controller.Adapters.OrderAdapter;
import com.example.thenavynd.Models.Carts;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    ImageView returnPrevious;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Carts> cartsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        cartsList = new ArrayList<>();
        orderAdapter = new OrderAdapter(OrderHistoryActivity.this,cartsList);
        recyclerView = findViewById(R.id.recycler_view_order_history);
        returnPrevious = findViewById(R.id.return_previous_order_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult().getDocuments()){

                        String documentId = document.getId();

                        Carts carts1 = document.toObject(Carts.class);
                        carts1.setDocumentId(documentId);
                        //Log.d("Test", "Name : " + carts1.getProductName() + "..." + carts1.getProductCount() + "..." + carts1.getCurrentTime());
                        cartsList.add(carts1);
                        orderAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(OrderHistoryActivity.this, "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                    //Log.d("Test", "Error : " + task.getException());
                }
            }
        });

        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}