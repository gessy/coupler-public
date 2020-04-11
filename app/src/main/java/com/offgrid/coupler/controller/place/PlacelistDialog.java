package com.offgrid.coupler.controller.place;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Placelist;

public class PlacelistDialog {

    public interface UpsertAction {
        void apply(Placelist placelist);
    }

    private UpsertAction action;
    private Context context;


    public PlacelistDialog(Context context) {
        this.context = context;
    }

    public PlacelistDialog withAction(UpsertAction action) {
        this.action = action;
        return this;
    }

    public void show() {
        show(null);
    }

    public void show(final Placelist placelist) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.dialog_placelist, null, false);

        final EditText placelistName = view.findViewById(R.id.placelist_name);
        placelistName.setText(placelist != null ? placelist.getName() : "");

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(placelist != null ? "Edit List" : "New List")
                .setView(view)
                .create();

        view.findViewById(R.id.save_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placelist current = placelist != null ? placelist : Placelist.empty();
                current.setName(placelistName.getText().toString());
                action.apply(current);
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.cancel_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
