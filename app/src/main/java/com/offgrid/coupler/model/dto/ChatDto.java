package com.offgrid.coupler.model.dto;

import android.os.Bundle;

import com.offgrid.coupler.data.model.ChatType;

public class ChatDto {
    private Long id;
    private String title;
    private ChatType type;

    private ChatDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ChatType getType() {
        return type;
    }

    public static ChatDto getInstance(Bundle bundle) {
        ChatDto dto = new ChatDto();
        dto.id = bundle.getLong("id");
        dto.title = bundle.getString("title");
        dto.type = ChatType.valueOf(bundle.getString("type"));
        return dto;
    }


    public static class BundleBuilder {
        private Long id = 0L;
        private String title = "";
        private ChatType type = ChatType.NONE;

        public BundleBuilder() {
        }

        public ChatDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ChatDto.BundleBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ChatDto.BundleBuilder withType(ChatType type) {
            this.type = type;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            bundle.putString("title", title);
            bundle.putString("type", type.toString());
            return bundle;
        }
    }
}
