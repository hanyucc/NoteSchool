package space.leniumc.noteschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
            TextView titleTextView = (TextView) findViewById(R.id.title_text_view);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }
                    fragmentTransaction.replace(R.id.content, HomeFragment.newInstance("1", "2"));
                    fragmentTransaction.commit();
                    titleTextView.setText(R.string.title_home);
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_search:
                    fragmentTransaction.replace(R.id.content, SearchFragment.newInstance("1", "2"));
                    fragmentTransaction.commit();
                    titleTextView.setText(R.string.title_search);
                    invalidateOptionsMenu();
                    return true;
                case R.id.navigation_me:
                    if (searchView.isSearchOpen()) {
                        searchView.closeSearch();
                    }
                    fragmentTransaction.replace(R.id.content, MeFragment.newInstance("1", "2"));
                    fragmentTransaction.commit();
                    titleTextView.setText(R.string.title_me);
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, HomeFragment.newInstance("1", "2"));
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

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);

            if (navigationView.getSelectedItemId() == R.id.navigation_home ||
                    navigationView.getSelectedItemId() == R.id.navigation_me) {
                getMenuInflater().inflate(R.menu.top_menu_home, menu);
            } else {
                getMenuInflater().inflate(R.menu.top_menu_search, menu);

                MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
                searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        //Do some magic
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //Do some magic
                        return false;
                    }
                });

                searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                    @Override
                    public void onSearchViewShown() {
                        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
                        navigationView.setSelectedItemId(R.id.navigation_search);
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, SearchFragment.newInstance("1", "2"));
                        fragmentTransaction.commit();
                        //Do some magic
                    }

                    @Override
                    public void onSearchViewClosed() {
                        //Do some magic
                    }
                });

                final MenuItem item = menu.findItem(R.id.search);
                searchView.setMenuItem(item);
            }

        return true;
    }

}
