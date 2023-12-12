package com.ybe.tr1ll1on.global.common;

public enum ReviewPeriod {
    ONE_MONTH(1),
    THREE_MONTH(3),
    SIX_MONTH(6),
    ONE_YEAR(12);

    private final int numOfPeriod;
    ReviewPeriod(int numOfPeriod) {
        this.numOfPeriod = numOfPeriod;
    }

    public int getNumOfPeriod() {
        return numOfPeriod;
    }
}