package lee.com.vshare.listener;

import lee.com.vshare.model.ex.Blogs;

/**
 * CreateDate：19-1-3 on 上午10:27
 * Describe:
 * Coder: lee
 */
public interface BlogsItemClickListener<T> {
    void onClick(Blogs blogs);
}
