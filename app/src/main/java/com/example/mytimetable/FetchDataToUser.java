package com.example.mytimetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class FetchDataToUser extends AppCompatActivity {
    private GetEntriesFragment getEntriesFragment;
    private EditEntriesFragment editEntriesFragment;
    private BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data_to_user);
        init();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(Utils.DAY_STRING_KEY, getIntent().getStringExtra(Utils.DAY_STRING_KEY));
        bundle.putString(Utils.WEEK_STRING_KEY, getIntent().getStringExtra(Utils.WEEK_STRING_KEY));
        getEntriesFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, getEntriesFragment).commit();

        bottomNavBar.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.getEntries:
                    selectedFragment = getEntriesFragment;
                    break;
                case R.id.editEntries:
                    selectedFragment = editEntriesFragment;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        });
    }

    private void init() {
        bottomNavBar = findViewById(R.id.bottom_navigation);
        editEntriesFragment = new EditEntriesFragment();
        getEntriesFragment = new GetEntriesFragment();
    }
}