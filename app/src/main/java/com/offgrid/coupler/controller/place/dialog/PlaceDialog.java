package com.offgrid.coupler.controller.place.dialog;

import android.content.Context;
import android.view.View;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.callback.PlaceCallback;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;


public class PlaceDialog extends AbstractSimpleDialog {

    private PlaceDetailsViewHolder placeViewHolder;
    private PlaceCallback callback;

    public PlaceDialog(Context context) {
        super(context);
    }

    public PlaceDialog withPlaceHolder(PlaceDetailsViewHolder viewHolder) {
        this.placeViewHolder = viewHolder;
        return this;
    }

    public PlaceDialog withOnCreateListener(PlaceCallback callback) {
        this.callback = callback;
        return this;
    }

    public PlaceDialog create() {
        super.createDialog();
        super.setTitle(getString(R.string.dialog_place));
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
