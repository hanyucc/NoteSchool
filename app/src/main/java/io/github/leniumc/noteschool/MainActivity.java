package io.github.leniumc.noteschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private MeFragment meFragment;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance();
                    }
                    fragmentTransaction.replace(R.id.content, homeFragment);
                    fragmentTransaction.commit();
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_search:
                    if (searchFragment == null) {
                        searchFragment = SearchFragment.newInstance();
                    }
                    fragmentTransaction.replace(R.id.content, searchFragment);
                    fragmentTransaction.commit();
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_me:
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }
                    if (meFragment == null) {
                        meFragment = MeFragment.newInstance();
                    }
                    fragmentTransaction.replace(R.id.content, meFragment);
                    fragmentTransaction.commit();
                    invalidateOptionsMenu();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
