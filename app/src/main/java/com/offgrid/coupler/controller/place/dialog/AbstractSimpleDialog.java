package com.offgrid.coupler.controller.place.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.offgrid.coupler.R;


public abstract class AbstractSimpleDialog implements View.OnClickListener {

    private Context context;
    private EditText inputName;
    private AlertDialog dialog;

    AbstractSimpleDialog(Context context) {
        this.context = context;
    }

    void createDialog() {
        dialog = new AlertDialog
                .Builder(context)
                .setTitle("")
                .setView(createView())
                .create();
    }

    void setTitle(String title) {
        dialog.setTitle(title);
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

    protected String getString(@StringRes int resId) {
        return context.getResources().getString(resId);
    }

    EditText getInputName() {
        return inputName;
    }

    abstract void onSave(View view);

    abstract void onCancel(View view);

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
