package com.polar.android_seed.bus;

import com.squareup.otto.Bus;

public class MyBus {
    private static final Bus BUS = new Bus();

    private MyBus() {
        // No instances.
    }

    public static Bus getInstance() {
        return BUS;
    }
}