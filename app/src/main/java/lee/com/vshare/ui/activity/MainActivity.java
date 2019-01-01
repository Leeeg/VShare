package lee.com.vshare.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import lee.com.vshare.R;
import lee.com.vshare.ui.BaseActivity;
import lee.com.vshare.ui.fragment.HomeFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ConstraintLayout contentView = findViewById(R.id.layout_activity_main_content);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                                     @Override
                                     public void onDrawerSlide(View drawerView, float slideOffset) {
                                         //设置主布局随菜单滑动而滑动
                                         int drawerViewWidth = drawerView.getWidth();
                                         contentView.setTranslationX(drawerViewWidth * slideOffset);
                                     }

                                     @Override
                                     public void onDrawerOpened(View drawerView) {

                                     }

                                     @Override
                                     public void onDrawerClosed(View drawerView) {

                                     }

                                     @Override
                                     public void onDrawerStateChanged(int newState) {

                                     }
                                 }
        );

        mOnNavigationItemSelectedListener = (item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Snackbar.make(contentView, "Replace with your own action", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
                case R.id.navigation_message:

                    return true;
            }
            return false;

        };

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 加载默认Fragment
     *
     * @param savedInstanceState
     */
    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.fragment_container, fragment, HomeFragment.TAG)
                    .commit();
        }
    }

    /**
     * 切换Fragment
     * @param view
     */
    public void show(View view) {

    }

}
