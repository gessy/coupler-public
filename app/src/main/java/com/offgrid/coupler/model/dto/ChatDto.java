package com.offgrid.coupler.model.dto;

import android.os.Bundle;

import com.offgrid.coupler.data.model.ChatType;

public class ChatDto {
    private Long id;
    private Long reference;
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

    public Long getReference() {
        return reference;
    }

    public static ChatDto getInstance(Bundle bundle) {
        ChatDto dto = new ChatDto();
        dto.id =  bundle.containsKey("id") ? bundle.getLong("id") : null;
        dto.reference = bundle.containsKey("reference") ? bundle.getLong("reference") : null;
        dto.title = bundle.getString("title");
        dto.type = ChatType.valueOf(bundle.getString("type"));
        return dto;
    }


    public static class BundleBuilder {
        private Long id;
        private Long reference;
        private String title;
        private ChatType type = ChatType.NONE;

        public BundleBuilder() {
        }

        public ChatDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ChatDto.BundleBuilder withReference(Long reference) {
            this.reference = reference;
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
            if (id != null) {
                bundle.putLong("id", id);
            }

            if (reference != null) {
                bundle.putLong("reference", reference);
            }

            bundle.putString("title", title);
            bundle.putString("type", type.toString());
            return bundle;
        }
    }
}
