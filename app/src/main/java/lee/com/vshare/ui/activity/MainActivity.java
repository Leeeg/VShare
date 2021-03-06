package lee.com.vshare.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lee.com.vshare.R;
import lee.com.vshare.ui.BaseActivity;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.adapter.NavAdapter;
import lee.com.vshare.ui.fragment.BlogsFragment;
import lee.com.vshare.ui.fragment.HomeFragment;
import lee.com.vshare.ui.fragment.MessageFragment;
import lee.com.vshare.ui.fragment.RecreationalFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private FragmentManager fragmentManager;

    private int tabIndex;
    private final static int TAB_INDEX_HOME = 0;
    private final static int TAB_INDEX_BLOGS = 1;
    private final static int TAB_INDEX_RECREATION = 2;
    private final static int TAB_INDEX_MESSAGE = 3;
    private Map<Integer, BaseFragment> tabMap = new HashMap<>(4);

    private DrawerLayout drawer;
    private BottomNavigationView navigation;
    private LinearLayout navHeaderView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        fragmentManager = getSupportFragmentManager();

        loadFragment(savedInstanceState);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.INTERNET)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        Log.d(TAG, "permission.BLUETOOTH_PRIVILEGED  agreed");
                    } else {
                        Log.e(TAG, "permission.BLUETOOTH_PRIVILEGED  denied");
                    }
                });
    }

    private void init() {

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navHeaderView = (LinearLayout) navigationView.getHeaderView(0);
        navHeaderView.findViewById(R.id.nav_header_icon).setOnClickListener((view) -> {
            drawer.closeDrawer(GravityCompat.START);
        });

//        RecyclerView menuNav = navHeaderView.findViewById(R.id.nav_header_menu);
//        NavAdapter menuAdapter = new NavAdapter(this);
//        menuNav.setLayoutManager(new LinearLayoutManager(this));
//        menuNav.setAdapter(menuAdapter);

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
                    if (TAB_INDEX_HOME != tabIndex) {
                        showFragment(TAB_INDEX_HOME);
                    }
                    return true;
                case R.id.navigation_blogs:
                    if (TAB_INDEX_BLOGS != tabIndex) {
                        BlogsFragment blogsFragment = (BlogsFragment) fragmentManager.findFragmentByTag(BlogsFragment.TAG);
                        if (null == blogsFragment) {
                            blogsFragment = BlogsFragment.newInstance();
                            addFragment(blogsFragment, TAB_INDEX_BLOGS, BlogsFragment.TAG);
                        } else {
                            showFragment(TAB_INDEX_BLOGS);
                        }
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (TAB_INDEX_RECREATION != tabIndex) {
                        RecreationalFragment recreationalFragment = (RecreationalFragment) fragmentManager.findFragmentByTag(RecreationalFragment.TAG);
                        if (null == recreationalFragment) {
                            recreationalFragment = RecreationalFragment.newInstance();
                            addFragment(recreationalFragment, TAB_INDEX_RECREATION, RecreationalFragment.TAG);
                        } else {
                            showFragment(TAB_INDEX_RECREATION);
                        }
                    }
                    return true;
                case R.id.navigation_message:
                    if (TAB_INDEX_MESSAGE != tabIndex) {
                        MessageFragment messageFragment = (MessageFragment) fragmentManager.findFragmentByTag(MessageFragment.TAG);
                        if (null == messageFragment) {
                            messageFragment = MessageFragment.newInstance();
                            addFragment(messageFragment, TAB_INDEX_MESSAGE, MessageFragment.TAG);
                        } else {
                            showFragment(TAB_INDEX_MESSAGE);
                        }
                    }
                    return true;
            }
            return false;

        };

        navigation = findViewById(R.id.navigation);
        navigation.setEnabled(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    /**
     * 加载默认Fragment
     *
     * @param savedInstanceState
     */
    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            HomeFragment fragment = HomeFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, HomeFragment.TAG)
                    .commit();

            tabIndex = TAB_INDEX_HOME;
            navigation.setEnabled(true);

            tabMap.put(tabIndex, fragment);
        }
    }

    public void addFragment(BaseFragment fragment, int currentIndex, String tag) {
        Log.d("Lee_MainActivity", "addFragment");
        navigation.setEnabled(false);
        fragmentManager
                .beginTransaction()
                .hide(tabMap.get(tabIndex))
                .add(R.id.fragment_container, fragment, tag)
                .commit();

        tabIndex = currentIndex;
        navigation.setEnabled(true);

        tabMap.put(tabIndex, fragment);
    }

    public void showFragment(int currentIndex) {
        Log.d("Lee_MainActivity", "showFragment");
        navigation.setEnabled(false);
        fragmentManager
                .beginTransaction()
                .hide(tabMap.get(tabIndex))
                .show(tabMap.get(currentIndex))
                .commit();

        tabIndex = currentIndex;
        navigation.setEnabled(true);
    }


}
