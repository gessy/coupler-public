package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.dto.GroupDto;
import com.offgrid.coupler.data.entity.Group;

public class DtoGroupWrapper {
    public static Bundle convertAndWrap(Group group) {
        return new GroupDto
                .BundleBuilder()
                .withId(group.getId())
                .withName(group.getName())
                .withDescription(group.getDescription())
                .build();
    }
}
