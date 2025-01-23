package com.mahmood.coffeeshop.Activities.MainActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mahmood.coffeeshop.Fragments.CartFragment;
import com.mahmood.coffeeshop.Fragments.FavouriteFragment;
import com.mahmood.coffeeshop.Fragments.HomeFragment;
import com.mahmood.coffeeshop.Fragments.ProfileFragment;
import com.mahmood.coffeeshop.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottom_navigation = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home){
                    loadFragment(new HomeFragment());
                } else if (id == R.id.navigation_cart) {
                    loadFragment(new CartFragment());
                } else if (id == R.id.navigation_favorites) {
                    loadFragment(new FavouriteFragment());
                }else {
                    loadFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}