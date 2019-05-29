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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lee.vshare.R;
import com.lee.vshare.databinding.ItemChatInfoBinding;
import com.lee.vshare.listener.ItemClickListener;
import com.lee.vshare.model.ChatInfoModel;
import com.lee.vshare.model.ex.ChatInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ChatInfoAdapter extends RecyclerView.Adapter<ChatInfoAdapter.ChatInfoViewHolder> {

    private List<ChatInfo> mChatInfoList = new ArrayList<>();

    @Nullable
    private final ItemClickListener ChatInfoItemClickListener;

    public ChatInfoAdapter(@Nullable ItemClickListener itemClickListener) {
        Log.d("Lee_ChatInfoAdapter", "ChatInfoAdapter");
        ChatInfoItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setChatInfoList(final List<? extends ChatInfo> chatInfoList) {
        Log.d("Lee_ChatInfoAdapter", "setChatInfoList");
        int startIndex = mChatInfoList.size();
        mChatInfoList.addAll(chatInfoList.subList(startIndex, chatInfoList.size()));
        Log.d("Lee_ChatInfoAdapter", "notifyItemRangeInserted  " + startIndex + " - " + mChatInfoList.size());
        notifyItemRangeInserted(startIndex, mChatInfoList.size());
    }

    @Override
    public ChatInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Lee_ChatInfoAdapter", "onCreateViewHolder");
        ItemChatInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_chat_info, parent, false);
        binding.setListener(ChatInfoItemClickListener);
        return new ChatInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ChatInfoViewHolder holder, int position) {
        Log.d("Lee_ChatInfoAdapter", "onBindViewHolder");
        holder.binding.setChatInfo(mChatInfoList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d("Lee_ChatInfoAdapter", "getItemCount");
        return mChatInfoList == null ? 0 : mChatInfoList.size();
    }

    @Override
    public long getItemId(int position) {
        Log.d("Lee_ChatInfoAdapter", "getItemId position = " + position);
        return mChatInfoList.get(position).getTime();
    }

    static class ChatInfoViewHolder extends RecyclerView.ViewHolder {

        final ItemChatInfoBinding binding;

        public ChatInfoViewHolder(ItemChatInfoBinding binding) {
            super(binding.getRoot());
            Log.d("Lee_ChatInfoAdapter", "ChatInfoViewHolder");
            this.binding = binding;
        }
    }

}
