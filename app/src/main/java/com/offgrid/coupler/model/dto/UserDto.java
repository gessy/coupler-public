package com.offgrid.coupler.model.dto;

import android.os.Bundle;


public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gid;

    private UserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGid() {
        return gid;
    }

    public static UserDto getInstance(Bundle bundle) {
        UserDto dto = new UserDto();
        dto.id = bundle.getLong("id");
        dto.firstName = bundle.getString("firstName");
        dto.lastName = bundle.getString("lastName");
        dto.gid = bundle.getString("gid");
        return dto;
    }


    public static class BundleBuilder {
        private Long id = 0L;
        private String firstName = "";
        private String lastName = "";
        private String gid = "";

        public BundleBuilder() {
        }

        public UserDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserDto.BundleBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDto.BundleBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDto.BundleBuilder withGid(String gid) {
            this.gid = gid;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            bundle.putString("firstName", firstName);
            bundle.putString("lastName", lastName);
            bundle.putString("gid", gid);
            return bundle;
        }
    }
}
