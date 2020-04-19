package com.offgrid.coupler.controller.place.dialog;

import android.content.Context;
import android.view.View;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.callback.PlaceCallback;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;
import com.offgrid.coupler.data.entity.Place;


public class PlaceDialog extends AbstractSimpleDialog {

    private PlaceDetailsViewHolder viewHolder;
    private PlaceCallback callback;

    private Place place;

    public PlaceDialog(Context context) {
        super(context);
    }

    public PlaceDialog withPlaceHolder(PlaceDetailsViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        return this;
    }

    public PlaceDialog withOnCreateListener(PlaceCallback callback) {
        this.callback = callback;
        return this;
    }

    public PlaceDialog create() {
        super.createDialog();
        return this;
    }

    private Place place() {
        Place result = null;
        if (viewHolder != null) {
            viewHolder.setPlaceName(getInputName().getText().toString());
            result = viewHolder.get();
        }

        if (result == null) {
            result = place;
            result.setName(getInputName().getText().toString());
        }

        return result;
    }


    @Override
    public void show() {
        this.place = null;
        this.getInputName().setText("");
        super.setTitle(getString(R.string.dialog_place));
        super.show();
    }

    public void show(Place place) {
        this.place = place;
        this.getInputName().setText(place.getName());
        super.setTitle(getString(R.string.dialog_edit_place));
        super.show();
    }


    @Override
    protected void onSave(View view) {
        callback.call(place());
        dismiss();
    }

    @Override
    protected void onCancel(View view) {
        cancel();
    }

}
