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
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import lee.com.vshare.R;
import lee.com.vshare.databinding.ItemPictureBinding;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Picture;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private Context mContext;
    List<? extends Picture> mPictureList;

    @Nullable
    private final ItemClickListener pictureItemClickListener;

    Integer[] banners = {
            R.mipmap.banner1,
            R.mipmap.banner2,
            R.mipmap.banner3,
            R.mipmap.banner4,
            R.mipmap.banner5
    };
    Random random = new Random();

    public PictureAdapter(Context context, @Nullable ItemClickListener itemClickListener) {
        Log.d("Lee_PictureAdapter", "PictureAdapter");
        pictureItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setPictureList(final List<? extends Picture> PictureList) {
        Log.d("Lee_PictureAdapter", "setPictureList");
        if (mPictureList == null) {
            mPictureList = PictureList;
            Log.d("Lee_PictureAdapter", "notifyItemRangeInserted");
            notifyItemRangeInserted(0, PictureList.size());
        } else {
            Log.d("Lee_PictureAdapter", "calculateDiff");
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPictureList.size();
                }

                @Override
                public int getNewListSize() {
                    return PictureList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPictureList.get(oldItemPosition).getPictureId() == PictureList.get(newItemPosition).getPictureId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Picture newProduct = PictureList.get(newItemPosition);
                    Picture oldProduct = mPictureList.get(oldItemPosition);
                    return newProduct.getPictureId() == oldProduct.getPictureId()
                            && newProduct.getPictureLikes() == oldProduct.getPictureLikes()
                            && newProduct.getPictureReads() == oldProduct.getPictureReads()
                            && Objects.equals(newProduct.getPictureTitle(), oldProduct.getPictureTitle());
                }
            });
            mPictureList = PictureList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_PictureAdapter", "onCreateViewHolder");
        ItemPictureBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_picture, parent, false);
        binding.setListener(pictureItemClickListener);
        return new PictureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Log.d("Lee_PictureAdapter", "onBindViewHolder");
        holder.binding.setPicture(mPictureList.get(position));
        holder.binding.pictureCover.setImageResource(banners[random.nextInt(4)]);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_PictureAdapter", "getItemCount");
        return mPictureList == null ? 0 : mPictureList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_PictureAdapter", "getItemId position = " + position);
        return mPictureList.get(position).getPictureId();
    }

    static class PictureViewHolder extends RecyclerView.ViewHolder {

        final ItemPictureBinding binding;

        public PictureViewHolder(ItemPictureBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_PictureAdapter", "PictureViewHolder");
            this.binding = binding;
        }
    }
}
