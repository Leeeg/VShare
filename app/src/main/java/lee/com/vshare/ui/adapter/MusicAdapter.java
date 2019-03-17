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
import lee.com.vshare.databinding.ItemMusicBinding;
import lee.com.vshare.listener.ItemClickListener;
import lee.com.vshare.model.ex.Music;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    List<? extends Music> mMusicList;

    @Nullable
    private final ItemClickListener MusicItemClickListener;

    public MusicAdapter(@Nullable ItemClickListener itemClickListener) {
        Log.d("Lee_MusicAdapter", "MusicAdapter");
        MusicItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setMusicList(final List<? extends Music> MusicList) {
        Log.d("Lee_MusicAdapter", "setMusicList");
        if (mMusicList == null) {
            mMusicList = MusicList;
            Log.d("Lee_MusicAdapter", "notifyItemRangeInserted");
            notifyItemRangeInserted(0, MusicList.size());
        } else {
            Log.d("Lee_MusicAdapter", "calculateDiff");
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMusicList.size();
                }

                @Override
                public int getNewListSize() {
                    return MusicList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMusicList.get(oldItemPosition).getMusicId() == MusicList.get(newItemPosition).getMusicId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Music newProduct = MusicList.get(newItemPosition);
                    Music oldProduct = mMusicList.get(oldItemPosition);
                    return newProduct.getMusicId() == oldProduct.getMusicId()
                            && newProduct.getMusicLikes() == oldProduct.getMusicLikes()
                            && newProduct.getMusicReads() == oldProduct.getMusicReads()
                            && Objects.equals(newProduct.getMusicTitle(), oldProduct.getMusicTitle());
                }
            });
            mMusicList = MusicList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_MusicAdapter", "onCreateViewHolder");
        ItemMusicBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_music, parent, false);
        binding.setListener(MusicItemClickListener);
        return new MusicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        Log.d("Lee_MusicAdapter", "onBindViewHolder");
        holder.binding.setMusic(mMusicList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_MusicAdapter", "getItemCount");
        return mMusicList == null ? 0 : mMusicList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_MusicAdapter", "getItemId position = " + position);
        return mMusicList.get(position).getMusicId();
    }

    static class MusicViewHolder extends RecyclerView.ViewHolder {

        final ItemMusicBinding binding;

        public MusicViewHolder(ItemMusicBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_MusicAdapter", "MusicViewHolder");
            this.binding = binding;
        }
    }
}
