package com.example.thenavynd.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thenavynd.Controller.Adapters.CategoryAdapter;
import com.example.thenavynd.Controller.Fragments.AccessoriesFragment;
import com.example.thenavynd.Controller.Fragments.HandbagsFragment;
import com.example.thenavynd.Controller.Fragments.LatestArrivedFragment;
import com.example.thenavynd.Controller.Fragments.ReadyToWearFragment;
import com.example.thenavynd.Controller.Fragments.ShoesFragment;
import com.example.thenavynd.Models.Categories;
import com.example.thenavynd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    List<Categories> categoriesList;
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView currentEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            int id = menuItem.getItemId();
            Fragment fragment = null;
            Intent intent = null;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            switch (id){
                case R.id.latest_arrived:
                    fragment = new LatestArrivedFragment();
                    loadFragment(fragment);
                    break;
                case R.id.handbags:
                    fragment = new HandbagsFragment();
                    loadFragment(fragment);
                    break;
                case R.id.ready_to_wear:
                    fragment = new ReadyToWearFragment();
                    loadFragment(fragment);
                    break;
                case R.id.accessories:
                    fragment = new AccessoriesFragment();
                    loadFragment(fragment);
                    break;
                case R.id.shoes:
                    fragment = new ShoesFragment();
                    loadFragment(fragment);
                    break;
                case R.id.change_password:

                    if(user != null){
                        intent = new Intent(this, OrderHistoryActivity.class);
                        startActivity(intent);
                    }
                    else{
                        SwitchToLogin();
                    }
                    break;
                case R.id.order_history:
                    if(user != null){
                        intent = new Intent(this, CartActivity.class);
                        startActivity(intent);
                    }
                    else{
                        SwitchToLogin();
                    }
                    break;
                case R.id.log_out:
                    auth.signOut();
                    SwitchToMain();
                    break;
                default:
                    return true;
            }
            return true;
        });

        recyclerView = findViewById(R.id.recycler_view_category);
        categoryAdapter = new CategoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoriesList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        db.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Categories c = document.toObject(Categories.class);
                                categoriesList.add(c);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        categoryAdapter.setData(categoriesList);
        recyclerView.setAdapter(categoryAdapter);

        View viewHeader = navigationView.getHeaderView(0);
        currentEmailUser = viewHeader.findViewById(R.id.current_user_email);

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            currentEmailUser.setText(auth.getCurrentUser().getEmail());
        }
        else{
            currentEmailUser.setText("Guest");
        }




    }

    private void SwitchToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }

    private void SwitchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}