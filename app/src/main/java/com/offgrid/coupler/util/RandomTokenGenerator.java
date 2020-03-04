package com.offgrid.coupler.util;

import java.util.Random;
import java.util.UUID;

public class RandomTokenGenerator {

    public static String get() {
        return UUID.randomUUID().toString();
    }

    public static int getInt() {
        return new Random().nextInt((100 - 10) + 1) + 10;
    }
}
