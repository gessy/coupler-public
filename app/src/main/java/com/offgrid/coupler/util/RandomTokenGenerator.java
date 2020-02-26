package com.offgrid.coupler.util;

import java.util.UUID;

public class RandomTokenGenerator {

    public static String get() {
        return UUID.randomUUID().toString();
    }
}
