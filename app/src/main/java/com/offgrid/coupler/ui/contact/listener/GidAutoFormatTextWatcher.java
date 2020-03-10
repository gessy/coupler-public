package com.offgrid.coupler.ui.contact.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.offgrid.coupler.ui.contact.Updatable;

import static com.offgrid.coupler.ui.contact.status.FieldState.CONTACT_GID;

public class GidAutoFormatTextWatcher implements TextWatcher {
    private static final int BUNCH_SIZE = 3;
    private static final char BUNCH_SEPARATOR = '-';
    private static final String DIGIT_REGEX = "[^\\d.]";
    private static final String GID_PATTERN = "^[0-9]{3}-[0-9]{3}-[0-9]{3}-[0-9]{3}$";

    private boolean inEdit = false;

    private final int bunchSize;
    private final char bunchSeparator;
    private EditText editText;

    private Updatable updatable;


    public GidAutoFormatTextWatcher(EditText editText, Updatable updatable) {
        this.editText = editText;
        this.bunchSize = BUNCH_SIZE;
        this.bunchSeparator = BUNCH_SEPARATOR;
        this.updatable = updatable;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!inEdit) {
            inEdit = true;
            String str = editText.getText().toString().replaceAll(DIGIT_REGEX, "");

            if (str.length() > bunchSize) {
                StringBuilder builder = new StringBuilder();
                builder.append(str.substring(0, Math.min(str.length(), bunchSize)));
                for (int i = bunchSize; i < str.length(); i += bunchSize) {
                    builder.append(bunchSeparator);
                    builder.append(str.substring(i, Math.min(str.length(), i + bunchSize)));
                }
                str = builder.toString();
            }

            editText.getText().replace(0, editText.getText().length(), str);
            inEdit = false;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        int state = editable.toString().matches(GID_PATTERN)
                ? CONTACT_GID
                : CONTACT_GID * (-1);
        updatable.updateState(state);
    }
}
