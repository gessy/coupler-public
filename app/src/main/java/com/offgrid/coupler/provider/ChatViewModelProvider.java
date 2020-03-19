package com.offgrid.coupler.provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.view.ChatViewModel;
import com.offgrid.coupler.model.view.UserChatViewModel;

public class ChatViewModelProvider extends ViewModelProvider {

    public ChatViewModelProvider(@NonNull ViewModelStoreOwner owner) {
        super(owner);
    }

    @NonNull
    public ChatViewModel getPersonal() {
        return super.get(UserChatViewModel.class);
    }


    @NonNull
    public ChatViewModel get(ChatType chatType) {
        if (chatType.equals(ChatType.GROUP)) {
            //TODO
        }
        return super.get(UserChatViewModel.class);
    }

}
