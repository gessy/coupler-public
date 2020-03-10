package com.offgrid.coupler.model;

import android.os.Bundle;

public class Info {
    public enum Action {
        none("none"),
        add_contact("add_contact"),
        contact_list("contact_list"),
        add_group("add_group"),
        new_message("new_message"),
        settings("settings"),
        info("info");

        String stringRep;

        Action(String string) {
            stringRep = string;
        }

        @Override
        public String toString() {
            return stringRep;
        }
    }


    String text = "";
    String title = "";
    Info.Action action = Info.Action.none;

    public static Info getInstance(Bundle bundle) {
        Info info = new Info();
        info.text = bundle.getString("text");
        info.title = bundle.getString("title");
        info.action = Info.Action.valueOf(bundle.getString("action"));

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

    public Action getAction() {
        return action;
    }

    public static class BundleBuilder {
        String text = "";
        String title = "";
        Info.Action action = Info.Action.none;

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


        public BundleBuilder withAction(Info.Action action) {
            this.action = action;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("text", text);
            bundle.putString("action", action.toString());
            return bundle;
        }

    }

}
