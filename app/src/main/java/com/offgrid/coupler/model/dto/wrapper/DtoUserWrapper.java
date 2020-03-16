package com.offgrid.coupler.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.model.dto.UserDto;

public class DtoUserWrapper {
    public static Bundle convertAndWrap(User user) {
        return new UserDto
                .BundleBuilder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withGid(user.getGid())
                .build();
    }
}
