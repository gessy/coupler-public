package com.offgrid.coupler.core.proxy;

public class LoraProxy {
    static {
        System.loadLibrary("native-lib");
    }

    public static native Object etherMessage();

    public static native void sendMessage(String uid, String data);

    public static native void shmInit();

    public static native void shmFree();
}
