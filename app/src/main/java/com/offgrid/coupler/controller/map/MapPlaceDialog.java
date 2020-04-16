package com.offgrid.coupler.controller.map;

import android.content.Context;
import android.view.View;


import com.offgrid.coupler.controller.AbstractSimpleDialog;
import com.offgrid.coupler.core.callback.PlaceCallback;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;


public class MapPlaceDialog extends AbstractSimpleDialog {

    private PlaceDetailsViewHolder placeViewHolder;
    private String title;
    private PlaceCallback callback;


    public MapPlaceDialog(Context context) {
        super(context);
    }


    public MapPlaceDialog withTitle(String title) {
        this.title = title;
        return this;
    }


    public MapPlaceDialog withPlaceHolder(PlaceDetailsViewHolder viewHolder) {
        this.placeViewHolder = viewHolder;
        return this;
    }

    public MapPlaceDialog withOnClickListener(PlaceCallback callback) {
        this.callback = callback;
        return this;
    }

    public MapPlaceDialog create() {
        super.createDialog(title);
        return this;
    }


    @Override
    protected void onSave(View view) {
        placeViewHolder.setPlaceName(getInputName().getText().toString());
        callback.call(placeViewHolder.get());
        dismiss();
    }

    @Override
    protected void onCancel(View view) {
        cancel();
    }

}
