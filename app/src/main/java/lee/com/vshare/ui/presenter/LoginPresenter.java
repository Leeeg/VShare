package lee.com.vshare.ui.presenter;

import android.view.View;

/**
 * CreateDate：19-1-22 on 下午3:23
 * Describe:
 * Coder: lee
 */
public interface LoginPresenter {

    void onFocusChange(View v, boolean hasFocus);

    void onTextChanged(CharSequence s, int start, int before, int count);

    void onLoginButtonClick();

    void onShowHistoryClick();

    void onRetrieveClick();

    void onSingUpClick();

}
