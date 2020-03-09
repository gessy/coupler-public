package com.offgrid.coupler.ui.contact.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.offgrid.coupler.ui.contact.Updatable;

import static com.offgrid.coupler.ui.contact.status.FieldState.CONTACT_FIRST_NAME;

public class FirstNameTextWatcher implements TextWatcher {
    private static final int THRESHOLD = 3;

    private Updatable updatable;
    private EditText editText;


    public FirstNameTextWatcher(EditText editText, Updatable updatable) {
        this.updatable = updatable;
        this.editText = editText;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int state = editText.getText().length() >= THRESHOLD
                ? CONTACT_FIRST_NAME
                : CONTACT_FIRST_NAME * (-1);
        updatable.updateState(state);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
