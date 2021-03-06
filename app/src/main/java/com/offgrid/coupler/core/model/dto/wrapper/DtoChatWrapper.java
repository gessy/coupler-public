package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.dto.UserDto;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.core.model.dto.ChatDto;

import static com.offgrid.coupler.data.model.ChatType.GROUP;

public class DtoChatWrapper {

    public static Bundle convertAndWrap(UserDto user) {
        return new ChatDto
                .BundleBuilder()
                .withReference(user.getId())
                .withTitle(user.getFullName())
                .withType(ChatType.PERSONAL)
                .build();
    }

    public static Bundle convertAndWrap(User user) {
        return new ChatDto
                .BundleBuilder()
                .withReference(user.getId())
                .withTitle(user.chatTitle())
                .withType(ChatType.PERSONAL)
                .build();
    }

    public static Bundle convertAndWrap(Group group) {
        return new ChatDto
                .BundleBuilder()
                .withReference(group.getId())
                .withTitle(group.getName())
                .withType(GROUP)
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
