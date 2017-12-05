package com.air.movieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.air.movieapp.R;

/**
 * Created by sagar on 1/8/16.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private final String[] mItems;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String string);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public SettingsAdapter(String[] items) {
        this.mItems = items;
    }

    @Override
    public int getItemCount() {
        return this.mItems.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public SettingsAdapter.SettingsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.view_settings, viewGroup, false);
        return  new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.SettingsViewHolder settingsViewHolder, int position) {
        settingsViewHolder.setData(mItems[position]) ;
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private LinearLayout llContainer;

        public SettingsViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            llContainer = (LinearLayout) view.findViewById(R.id.ll_container);
        }

        public void setData(final String string) {
            tvTitle.setText(string);
            llContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(string);
                }
            });
        }

    }


}