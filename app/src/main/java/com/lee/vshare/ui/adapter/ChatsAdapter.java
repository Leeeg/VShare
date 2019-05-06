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

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.vshare.R;
import com.lee.vshare.databinding.ItemChatsBinding;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    List<? extends com.lee.vshare.model.ex.Chats> newChatsList;

    @Nullable
    private final com.lee.vshare.listener.ItemClickListener chatsItemClickListener;

    public ChatsAdapter(@Nullable com.lee.vshare.listener.ItemClickListener itemClickListener) {
        Log.d("Lee_ChatsAdapter", "ChatsAdapter");
        chatsItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setChatsList(final List<? extends com.lee.vshare.model.ex.Chats> oldChatsList) {
        Log.d("Lee_ChatsAdapter", "setChatsList");
        if (newChatsList == null) {
            newChatsList = oldChatsList;
            Log.d("Lee_ChatsAdapter", "notifyItemRangeInserted");
            notifyItemRangeInserted(0, oldChatsList.size());
        } else {
            Log.d("Lee_ChatsAdapter", "calculateDiff");
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return newChatsList.size();
                }

                @Override
                public int getNewListSize() {
                    return oldChatsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return newChatsList.get(oldItemPosition).getChatsId() == oldChatsList.get(newItemPosition).getChatsId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    com.lee.vshare.model.ex.Chats newProduct = oldChatsList.get(newItemPosition);
                    com.lee.vshare.model.ex.Chats oldProduct = newChatsList.get(oldItemPosition);
                    return newProduct.getChatsId() == oldProduct.getChatsId()
                            && newProduct.getChatsLikes() == oldProduct.getChatsLikes()
                            && newProduct.getChatsReads() == oldProduct.getChatsReads()
                            && Objects.equals(newProduct.getChatsTitle(), oldProduct.getChatsTitle());
                }
            });
            newChatsList = oldChatsList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_ChatsAdapter", "onCreateViewHolder");
        ItemChatsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chats, parent, false);
        binding.setListener(chatsItemClickListener);
        return new ChatsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder holder, int position) {
        Log.d("Lee_ChatsAdapter", "onBindViewHolder");
        holder.binding.setChats(newChatsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_ChatsAdapter", "getItemCount");
        return newChatsList == null ? 0 : newChatsList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_ChatsAdapter", "getItemId position = " + position);
        return newChatsList.get(position).getChatsId();
    }

    static class ChatsViewHolder extends RecyclerView.ViewHolder {

        final ItemChatsBinding binding;

        public ChatsViewHolder(ItemChatsBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_ChatsAdapter", "ChatsViewHolder");
            this.binding = binding;
        }
    }
}
