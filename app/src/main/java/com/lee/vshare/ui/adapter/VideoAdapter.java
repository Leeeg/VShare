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

package com.lee.vshare.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.listener.ItemClickListener;
import com.lee.vshare.model.ex.Video;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.lee.vshare.databinding.ItemVideoBinding;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<? extends Video> mVideoList;

    @Nullable
    private final ItemClickListener VideoItemClickListener;

    public VideoAdapter(@Nullable ItemClickListener itemClickListener) {
        Log.d("Lee_VideoAdapter", "VideoAdapter");
        VideoItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setVideoList(final List<? extends Video> VideoList) {
        Log.d("Lee_VideoAdapter", "setVideoList");
        if (mVideoList == null) {
            mVideoList = VideoList;
            Log.d("Lee_VideoAdapter", "notifyItemRangeInserted");
            notifyItemRangeInserted(0, VideoList.size());
        } else {
            Log.d("Lee_VideoAdapter", "calculateDiff");
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mVideoList.size();
                }

                @Override
                public int getNewListSize() {
                    return VideoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mVideoList.get(oldItemPosition).getVideoId() == VideoList.get(newItemPosition).getVideoId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Video newProduct = VideoList.get(newItemPosition);
                    Video oldProduct = mVideoList.get(oldItemPosition);
                    return newProduct.getVideoId() == oldProduct.getVideoId()
                            && newProduct.getVideoLikes() == oldProduct.getVideoLikes()
                            && newProduct.getVideoReads() == oldProduct.getVideoReads()
                            && Objects.equals(newProduct.getVideoTitle(), oldProduct.getVideoTitle());
                }
            });
            mVideoList = VideoList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_VideoAdapter", "onCreateViewHolder");
        ItemVideoBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video, parent, false);
        binding.setListener(VideoItemClickListener);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Log.d("Lee_VideoAdapter", "onBindViewHolder");
        holder.binding.setVideo(mVideoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_VideoAdapter", "getItemCount");
        return mVideoList == null ? 0 : mVideoList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_VideoAdapter", "getItemId position = " + position);
        return mVideoList.get(position).getVideoId();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        final ItemVideoBinding binding;

        public VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_VideoAdapter", "VideoViewHolder");
            this.binding = binding;
        }
    }
}
