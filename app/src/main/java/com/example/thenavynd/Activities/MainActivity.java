package com.example.thenavynd.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.thenavynd.Fragments.AccessoriesFragment;
import com.example.thenavynd.Fragments.HandbagsFragment;
import com.example.thenavynd.Fragments.LatestArrivedFragment;
import com.example.thenavynd.Fragments.ReadyToWearFragment;
import com.example.thenavynd.Fragments.ShoesFragment;
import com.example.thenavynd.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;

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
                    break;
                case R.id.order_history:
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                    break;
                case R.id.log_out:
                    break;
                default:
                    return true;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }
}