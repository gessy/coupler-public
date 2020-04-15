package com.offgrid.coupler.controller.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.offgrid.coupler.R;


abstract class MapSimpleDialog implements View.OnClickListener {

    private Context context;
    private EditText inputName;
    private AlertDialog dialog;


    public MapSimpleDialog(Context context) {
        this.context = context;
    }

    protected void createDialog(String title) {
        dialog = new AlertDialog
                .Builder(context)
                .setTitle(title)
                .setView(createView())
                .create();
    }


    private View createView() {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.dialog_entity_name, null, false);

        inputName = view.findViewById(R.id.entity_name);
        inputName.setText("");

        view.findViewById(R.id.save_entity).setOnClickListener(this);
        view.findViewById(R.id.cancel_entity).setOnClickListener(this);

        return view;
    }

    protected EditText getInputName() {
        return inputName;
    }

    protected abstract void onSave(View view);

    protected abstract void onCancel(View view);


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_entity) {
            onSave(view);
        }

        if (view.getId() == R.id.cancel_entity) {
            onCancel(view);
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
