package com.lee.vshare.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.lee.vshare.R;
import com.lee.vshare.ui.BaseActivity;
import com.lee.vshare.ui.fragment.ChatInfoFragment;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

/**
 * Created by Lee on 2019/5/26.
 */

public class ContainActivity extends BaseActivity {

    public static final String TAG = "ContainActivity";

    public static final String START_KEY = "ContainActivityStartKey";
    public static final String START_TITLE = "ContainActivityStartTitle";
    private String startKey;
    private String startTitle;

    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        loadFragment(savedInstanceState);
    }

    public static void start(Context context, String key, String title) {
        Intent starter = new Intent(context, ContainActivity.class);
        starter.putExtra(START_KEY, key);
        starter.putExtra(START_TITLE, title);
        context.startActivity(starter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 加载Fragment
     *
     * @param savedInstanceState
     */
    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            startKey = getIntent().getStringExtra(START_KEY);
            startTitle = getIntent().getStringExtra(START_TITLE);
            if (startKey.equals(ChatInfoFragment.TAG)) {
                toolbar.setTitle(startTitle);
                ChatInfoFragment fragment = ChatInfoFragment.newInstance();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment, ChatInfoFragment.TAG)
                        .commit();
            }
        }
    }

}
