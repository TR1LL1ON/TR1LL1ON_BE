package com.ybe.tr1ll1on.global.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

public class SortingInfo {
    private final String property;
    private final boolean descending;

    public SortingInfo(String property, boolean descending) {
        this.property = property;
        this.descending = descending;
    }

    public String getProperty() {
        return property;
    }

    public boolean isDescending() {
        return descending;
    }
}
