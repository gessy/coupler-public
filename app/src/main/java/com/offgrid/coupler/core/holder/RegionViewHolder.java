package com.offgrid.coupler.core.holder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.model.DownloadState;

public class RegionViewHolder extends RecyclerView.ViewHolder {

    @DrawableRes
    private final int DOWNLOAD_RES_ID = R.drawable.ic_download_blue_24dp;
    @DrawableRes
    private final int IN_PROGRESS_RES_ID = R.drawable.ic_cancel_blue_24dp;

    private final TextView regionName;
    private final TextView tilesCount;
    private final ImageView downloadState;


    private View.OnClickListener clickListener;

    public RegionViewHolder(View itemView) {
        super(itemView);

        regionName = itemView.findViewById(R.id.region_name);
        tilesCount = itemView.findViewById(R.id.tiles_count);
        downloadState = itemView.findViewById(R.id.download_state_icon);
    }

    public void update(Region region) {
        regionName.setText(region.getName());
        tilesCount.setText("3.2K");

        if (region.getDownloadState().equals(DownloadState.DOWNLOADED)) {
            downloadState.setVisibility(View.GONE);
        } else {
            Drawable drawable = region.getDownloadState().equals(DownloadState.READY_TO_LOAD)
                    ? ContextCompat.getDrawable(itemView.getContext(), DOWNLOAD_RES_ID)
                    : ContextCompat.getDrawable(itemView.getContext(), IN_PROGRESS_RES_ID);

            downloadState.setImageDrawable(drawable);
            downloadState.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        downloadState.setOnClickListener(clickListener);
    }

}
