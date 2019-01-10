package lee.com.vshare.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import lee.com.vshare.R;
import lee.com.vshare.ui.BaseActivity;
import lee.com.vshare.ui.fragment.LoginFragment;

/**
 * CreateDate：18-12-28 on 下午7:26
 * Describe:
 * Coder: lee
 */
public class LoginActivity extends BaseActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentManager = getSupportFragmentManager();

        loadFragment(savedInstanceState);
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

}
