package com.offgrid.coupler.model;

import android.os.Bundle;

public class Info {
    String text = "";
    String title = "";

    public static Info getInstance(Bundle bundle) {
        Info info = new Info();
        info.text = bundle.getString("text");
        info.title = bundle.getString("title");
        return info;
    }

    public static Info getEmpty() {
        return new Info();
    }


    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public static class BundleBuilder {
        String text = "";
        String title = "";

        public BundleBuilder() {
        }

        public BundleBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public BundleBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("text", text);
            return bundle;
        }

    }

}
