package com.offgrid.coupler.core.model.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.ChatMessages;
import com.offgrid.coupler.data.entity.Message;

public interface ChatViewModel {
    void loadByOwnerId(Long RefId);

    Object getOwner();

    void addMessage(Message message);

    void deleteMessages();

    boolean noMessages();

    boolean isPersonal();

    void setVisibility(boolean visible);

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super ChatMessages> observer);
}
