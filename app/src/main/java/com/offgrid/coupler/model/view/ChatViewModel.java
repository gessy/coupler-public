package com.offgrid.coupler.model.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.Message;

public interface ChatViewModel {
    void loadByReferenceId(Long RefId);

    void addMessage(Message message);

    void deleteMessages();

    void createChat();

    void deleteChat();

    boolean noMessages();

    boolean isPersonal();

    void setVisibility(boolean visible);

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Object> observer);
}
