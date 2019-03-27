package lee.com.vshare.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import lee.com.vshare.R;
import lee.com.vshare.ui.BaseActivity;
import lee.com.vshare.ui.BaseFragment;
import lee.com.vshare.ui.fragment.LoginFragment;
import lee.com.vshare.ui.fragment.RegisterFragment;
import lee.com.vshare.ui.fragment.ResetPasswordFragment;
import lee.com.vshare.ui.fragment.RetrieveFragment;
import lee.com.vshare.ui.fragment.SignUpFragment;
import lee.com.vshare.util.PropertyUtils;

/**
 * CreateDate：18-12-28 on 下午7:26
 * Describe:
 * Coder: lee
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "Lee_LoginActivity";

    public final static int TITLE_SIGNIN = 0;
    public final static int TITLE_REGISTER = 1;
    public final static int TITLE_RETRIEVE = 2;
    public final static int TITLE_SIGNUP = 3;

    private int titleIndex;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                    } else {
                    }
                });

        loadFragment(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (titleIndex == TITLE_SIGNIN) {
                finish();
            } else {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 加载默认Fragment
     *
     * @param savedInstanceState
     */
    private void loadFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            LoginFragment fragment = LoginFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, LoginFragment.TAG)
                    .commit();
        }
    }

    private void showFragment(BaseFragment fragment, String tag) {
        Log.d("Lee_LoginActivity", "addFragment");
        fragmentManager
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.fragment_container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void showRegister() {
        RegisterFragment registerFragment = (RegisterFragment) fragmentManager.findFragmentByTag(RegisterFragment.TAG);
        if (null == registerFragment) {
            registerFragment = RegisterFragment.newInstance();
        }
        showFragment(registerFragment, RegisterFragment.TAG);
    }

    public void showSignUp() {
        SignUpFragment signUpFragment = (SignUpFragment) fragmentManager.findFragmentByTag(SignUpFragment.TAG);
        if (null == signUpFragment) {
            signUpFragment = SignUpFragment.newInstance();
        }
        showFragment(signUpFragment, SignUpFragment.TAG);
    }

    public void showRetrieve() {
        RetrieveFragment retrieveFragment = (RetrieveFragment) fragmentManager.findFragmentByTag(RetrieveFragment.TAG);
        if (null == retrieveFragment) {
            retrieveFragment = RetrieveFragment.newInstance();
        }
        showFragment(retrieveFragment, RetrieveFragment.TAG);
    }

    public void showResetPasswordFragment() {
        ResetPasswordFragment resetPasswordFragment = (ResetPasswordFragment) fragmentManager.findFragmentByTag(ResetPasswordFragment.TAG);
        if (null == resetPasswordFragment) {
            resetPasswordFragment = ResetPasswordFragment.newInstance();
        }
        showFragment(resetPasswordFragment, ResetPasswordFragment.TAG);
    }

    @Override
    public void setTitle(int index) {
        if (titleIndex != index) {
            Log.d("Lee_LoginActivity", "setTitle ： " + index);
            titleIndex = index;
            if (index == TITLE_SIGNIN) {
                toolbar.setTitle(R.string.title_sign_in);
            } else if (index == TITLE_REGISTER || index == TITLE_SIGNUP) {
                toolbar.setTitle(R.string.title_sign_up);
            } else if (index == TITLE_RETRIEVE) {
                toolbar.setTitle(R.string.title_retrieve);
            }
        }
    }

}
