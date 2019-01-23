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
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import lee.com.vshare.R;
import lee.com.vshare.databinding.ItemLoginHistoryBinding;
import lee.com.vshare.db.entity.ex.LoginHistory;
import lee.com.vshare.listener.ItemClickListener;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.LoginHistoryViewHolder> {

    List<? extends LoginHistory> mLoginHistories;

    @Nullable
    private final ItemClickListener historyItemClickListener;

    public LoginHistoryAdapter(@Nullable ItemClickListener itemClickListener) {
        historyItemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void setLoginHistoryList(final List<? extends LoginHistory> loginHistories) {
        if (mLoginHistories == null) {
            mLoginHistories = loginHistories;
            notifyItemRangeInserted(0, loginHistories.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mLoginHistories.size();
                }

                @Override
                public int getNewListSize() {
                    return loginHistories.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mLoginHistories.get(oldItemPosition).getUserId() == loginHistories.get(newItemPosition).getUserId();
                }

                @SuppressLint("NewApi")
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    LoginHistory newProduct = loginHistories.get(newItemPosition);
                    LoginHistory oldProduct = mLoginHistories.get(oldItemPosition);
                    return newProduct.getUserId() == oldProduct.getUserId()
                            && Objects.equals(newProduct.getPassword(), oldProduct.getPassword())
                            && Objects.equals(newProduct.getUserName(), oldProduct.getUserName())
                            && Objects.equals(newProduct.getUserImgUrl(), oldProduct.getUserImgUrl());
                }
            });
            mLoginHistories = loginHistories;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public LoginHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLoginHistoryBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_login_history, parent, false);
        binding.setListener(historyItemClickListener);
        return new LoginHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LoginHistoryViewHolder holder, int position) {
        holder.binding.setLoginHistory(mLoginHistories.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mLoginHistories == null ? 0 : mLoginHistories.size();
    }

    @Override
    public long getItemId(int position) {
        return mLoginHistories.get(position).getUserId();
    }

    static class LoginHistoryViewHolder extends RecyclerView.ViewHolder {

        final ItemLoginHistoryBinding binding;

        public LoginHistoryViewHolder(ItemLoginHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
