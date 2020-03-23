package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;


public class GroupDto {
    private Long id;
    private String name;
    private String description;

    private GroupDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static GroupDto getInstance(Bundle bundle) {
        GroupDto dto = new GroupDto();
        dto.id = bundle.getLong("id");
        dto.name = bundle.getString("name");
        dto.description = bundle.getString("description");
        return dto;
    }


    public static class BundleBuilder {
        private Long id = 0L;
        private String name = "";
        private String description = "";

        public BundleBuilder() {
        }

        public GroupDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public GroupDto.BundleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public GroupDto.BundleBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            bundle.putString("name", name);
            bundle.putString("description", description);
            return bundle;
        }
    }
}
