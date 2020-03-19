package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.core.model.dto.ChatDto;

import static com.offgrid.coupler.data.model.ChatType.GROUP;

public class DtoChatWrapper {
    public static Bundle convertAndWrap(User user) {
        return new ChatDto
                .BundleBuilder()
                .withReference(user.getId())
                .withTitle(user.getFirstName() + " " + user.getLastName())
                .withType(ChatType.PERSONAL)
                .build();
    }

    public static Bundle convertAndWrap(Chat chat) {
        return new ChatDto
                .BundleBuilder()
                .withId(chat.getId())
                .withReference(chat.getType().equals(GROUP)
                        ? chat.getGroupId()
                        : chat.getUserId())
                .withTitle(chat.getTitle())
                .withType(chat.getType())
                .build();
    }
}
