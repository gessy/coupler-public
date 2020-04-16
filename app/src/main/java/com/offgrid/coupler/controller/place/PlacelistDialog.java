package com.offgrid.coupler.controller.place;

import android.content.Context;
import android.view.View;


import com.offgrid.coupler.controller.AbstractSimpleDialog;
import com.offgrid.coupler.core.callback.PlacelistCallback;
import com.offgrid.coupler.data.entity.Placelist;

public class PlacelistDialog extends AbstractSimpleDialog {

    private String title;
    private PlacelistCallback callback;
    private Placelist placelist;

    public PlacelistDialog(Context context) {
        super(context);
    }

    public PlacelistDialog withOnClickListener(PlacelistCallback callback) {
        this.callback = callback;
        return this;
    }

    public PlacelistDialog create() {
        super.createDialog(title);
        return this;
    }

    @Override
    public void show() {
        this.placelist = null;
        this.getInputName().setText("");
        this.title = "New List";
        super.show();
    }

    public void show(Placelist placelist) {
        this.placelist = placelist;
        this.getInputName().setText(placelist.getName());
        this.title = "Edit List";
        super.show();
    }


    @Override
    protected void onSave(View view) {
        Placelist current = placelist != null ? placelist : Placelist.empty();
        current.setName(getInputName().getText().toString());
        callback.call(current);
        dismiss();
    }

    @Override
    protected void onCancel(View view) {
        cancel();
    }
}
