package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.SourceActivity;
import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.core.model.dto.UserDto;

public class DtoUserWrapper {
    public static Bundle convertAndWrap(User user) {
        return new UserDto
                .BundleBuilder()
                .withId(user.getId())
                .withFullName(user.fullName())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withGid(user.getGid())
                .build();
    }

    public static Bundle convertAndWrap(User user,
                                        GroupDto groupDto,
                                        SourceActivity sourceActivity) {
        return new UserDto
                .BundleBuilder()
                .withId(user.getId())
                .withFullName(user.fullName())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withGid(user.getGid())
                .withSourceActivity(sourceActivity)
                .withSourceDto(groupDto)
                .build();
    }
}
