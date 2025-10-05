package com.ss.recyclewaste;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Set default fragment
        loadFragment(new HomeFragment());

        // Handle navigation item selection with if-else
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_waste) {
                selectedFragment = new WasteTypeFragment();
            } else if (id == R.id.nav_food) {
                selectedFragment = new FoodDonationFragment();
            } else if (id == R.id.nav_centers) {
                selectedFragment = new CentersFragment();
            } else if (id == R.id.nav_rewards) {
                selectedFragment = new RewardsFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                highlightMenuItem(id);
                return true;
            } else {
                return false;
            }
        });

        highlightMenuItem(R.id.nav_home); // Highlight default
    }

    // Make methods public for access from fragments
    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.main_container, fragment);
        ft.commit();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void highlightMenuItem(int menuId) {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.getMenu().findItem(menuId).setChecked(true);
    }
}
