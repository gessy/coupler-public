package com.offgrid.coupler.controller.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.offgrid.coupler.R;
import com.offgrid.coupler.core.callback.PlaceCallback;
import com.offgrid.coupler.core.holder.PlaceDetailsViewHolder;


public class MapPlaceDialog implements View.OnClickListener {

    private PlaceDetailsViewHolder placeViewHolder;
    private AlertDialog dialog;
    private Context context;
    private String title;
    private EditText placeName;

    private PlaceCallback callback;


    public MapPlaceDialog(Context context) {
        this.context = context;
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
        dialog = new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setView(createView())
                .create();
        return this;
    }


    private View createView() {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.dialog_entity_name, null, false);

        placeName = view.findViewById(R.id.entity_name);
        placeName.setText(placeViewHolder.getPlaceName());

        view.findViewById(R.id.save_entity).setOnClickListener(this);
        view.findViewById(R.id.cancel_entity).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_entity) {
            placeViewHolder.setPlaceName(placeName.getText().toString());
            callback.call(placeViewHolder.get());
            dialog.dismiss();
        }

        if (v.getId() == R.id.cancel_entity) {
            dialog.cancel();
        }
    }


    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void cancel() {
        dialog.cancel();
    }
}
