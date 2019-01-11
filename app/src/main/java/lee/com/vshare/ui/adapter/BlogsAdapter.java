/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lee.com.vshare.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import lee.com.vshare.R;
import lee.com.vshare.listener.BlogsItemClickListener;
import lee.com.vshare.model.ex.Blogs;
import lee.com.vshare.databinding.ItemBlogsBinding;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder> {

    List<? extends Blogs> mBlogsList;

    @Nullable
    private final BlogsItemClickListener blogsItemClickListener;

    public BlogsAdapter(@Nullable BlogsItemClickListener itemClickListener) {
        Log.d("Lee_BlogsAdapter", "BlogsAdapter");
        blogsItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setBlogsList(final List<? extends Blogs> blogsList) {
        Log.d("Lee_BlogsAdapter", "setLoginHistoryList");
        if (mBlogsList == null) {
            mBlogsList = blogsList;
            notifyItemRangeInserted(0, blogsList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mBlogsList.size();
                }

                @Override
                public int getNewListSize() {
                    return blogsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mBlogsList.get(oldItemPosition).getBlogId() == blogsList.get(newItemPosition).getBlogId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Blogs newProduct = blogsList.get(newItemPosition);
                    Blogs oldProduct = mBlogsList.get(oldItemPosition);
                    return newProduct.getBlogId() == oldProduct.getBlogId()
                            && newProduct.getBlogLikes() == oldProduct.getBlogLikes()
                            && newProduct.getBlogReads() == oldProduct.getBlogReads()
                            && Objects.equals(newProduct.getBlogTitle(), oldProduct.getBlogTitle());
                }
            });
            mBlogsList = blogsList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public BlogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_BlogsAdapter", "onCreateViewHolder");
        ItemBlogsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_blogs, parent, false);
        binding.setListener(blogsItemClickListener);
        return new BlogsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BlogsViewHolder holder, int position) {
        Log.d("Lee_BlogsAdapter", "onBindViewHolder");
        holder.binding.setBlogs(mBlogsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_BlogsAdapter", "getItemCount");
        return mBlogsList == null ? 0 : mBlogsList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_BlogsAdapter", "getItemId position = " + position);
        return mBlogsList.get(position).getBlogId();
    }

    static class BlogsViewHolder extends RecyclerView.ViewHolder {

        final ItemBlogsBinding binding;

        public BlogsViewHolder(ItemBlogsBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_BlogsAdapter", "BlogsViewHolder");
            this.binding = binding;
        }
    }
}
