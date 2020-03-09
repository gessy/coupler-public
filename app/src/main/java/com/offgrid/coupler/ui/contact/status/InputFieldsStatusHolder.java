package com.offgrid.coupler.ui.contact.status;

import static com.offgrid.coupler.ui.contact.status.FieldState.CONTACT_FIRST_NAME;
import static com.offgrid.coupler.ui.contact.status.FieldState.CONTACT_GID;

public class InputFieldsStatusHolder {
    private volatile int currentState;

    public InputFieldsStatusHolder() {
        this.currentState = 0;
    }

    public int updateState(int state) {
        int newState = currentState;

        if (state > 0) {
            newState |= state;
        } else if (state < 0) {
            newState = ~((~newState) | state);
        }

        currentState = newState;

        return newState;
    }

    public boolean makeActive() {
        if (CONTACT_FIRST_NAME != (CONTACT_FIRST_NAME & currentState)) {
            return false;
        }
        return CONTACT_GID == (CONTACT_GID & currentState);
    }

}
