package com.ady.romedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_search);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CautareFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_search:
                            selectedFragment = new CautareFragment();
                            break;
                        case R.id.nav_personal:
                            selectedFragment = new DateFragment();
                            break;
                        case R.id.nav_feedback:
                            selectedFragment = new FeedbackFragment();
                            break;
                    }
                        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        if(!(activeFragment instanceof DateFragment && selectedFragment instanceof DateFragment)){
                            if(!(activeFragment instanceof CautareFragment && selectedFragment instanceof CautareFragment)){
                                if(!(activeFragment instanceof FeedbackFragment && selectedFragment instanceof FeedbackFragment)){
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            selectedFragment).commit();
                                }
                            }
                        }
                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("Prefs", 0);
        String date = prefs.getString("date", null);
        boolean afiseaza = prefs.getBoolean("afisare", false);
    }
}