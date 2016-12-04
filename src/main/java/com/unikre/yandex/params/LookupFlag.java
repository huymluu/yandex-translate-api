package com.unikre.yandex.params;

public enum LookupFlag {
    FAMILY(0x0001),
    MORPHO(0x0004),
    POS_FILTER(0x0008);

    private final int bitmask;

    private LookupFlag(int bitmask) {
        this.bitmask = bitmask;
    }

    public int getBitmask() {
        return bitmask;
    }
}
