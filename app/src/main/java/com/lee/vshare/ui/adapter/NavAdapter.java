package com.lee.vshare.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lee.vshare.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * CreateDate：19-3-15 on 下午5:35
 * Describe:
 * Coder: lee
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private static final String TAG = "Lee_NavAdapter";

    private Context mContext;
    private String[] titles = {"Import", "Gallery", "Slideshow", "Tools", "Share"};
    private int[] icons = {R.drawable.ic_menu_camera,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_slideshow,
            R.drawable.ic_menu_manage,
            R.drawable.ic_menu_share};
    private static final int ITEM_COUNT = 5;

    public NavAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nav, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles[position]);
        holder.icon.setImageResource(icons[position]);
        holder.itemRoot.setOnClickListener((view) -> {
            Log.d(TAG, "on navMenuItemClick : " + position);
        });
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private LinearLayout itemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_nav__menu_title);
            icon = itemView.findViewById(R.id.item_nav__menu_icon);
            itemRoot = itemView.findViewById(R.id.layout_item_nav);
        }
    }
}
