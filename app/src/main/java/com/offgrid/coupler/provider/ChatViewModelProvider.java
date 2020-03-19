package com.offgrid.coupler.provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.model.view.ChatViewModel;
import com.offgrid.coupler.model.view.GroupChatViewModel;
import com.offgrid.coupler.model.view.UserChatViewModel;

import static com.offgrid.coupler.data.model.ChatType.GROUP;

public class ChatViewModelProvider extends ViewModelProvider {

    public ChatViewModelProvider(@NonNull ViewModelStoreOwner owner) {
        super(owner);
    }

    @NonNull
    public ChatViewModel getPersonal() {
        return super.get(UserChatViewModel.class);
    }

    @NonNull
    public ChatViewModel getGroup() {
        return super.get(GroupChatViewModel.class);
    }

    @NonNull
    public ChatViewModel get(ChatType chatType) {
        return chatType.equals(GROUP)
                ? super.get(GroupChatViewModel.class)
                : super.get(UserChatViewModel.class);
    }
}
