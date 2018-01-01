package io.github.leniumc.noteschool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private RankingFragment rankingFragment;
    private MeFragment meFragment;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MaterialSearchView searchView = findViewById(R.id.search_view);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance();
                    }
                    fragmentTransaction.replace(R.id.content, homeFragment);
                    fragmentTransaction.commit();
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_ranking:
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }
                    if (rankingFragment == null) {
                        rankingFragment = RankingFragment.newInstance();
                    }
                    fragmentTransaction.replace(R.id.content, rankingFragment);
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

        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("此App仍在开发中，该版本仅作为Demo使用，并未与服务器进行连接，大部分数据为本地生成。")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton(android.R.string.yes, null)
                .show();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        MaterialSearchView searchView = findViewById(R.id.search_view);
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
