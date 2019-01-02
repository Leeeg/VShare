package lee.com.vshare.model;

import lee.com.vshare.model.ex.Home;

/**
 * CreateDate：18-12-29 on 下午5:17
 * Describe:
 * Coder: lee
 */
public class HomeModel implements Home {

    private String title;

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
