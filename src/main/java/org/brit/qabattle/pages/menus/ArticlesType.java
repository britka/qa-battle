package org.brit.qabattle.pages.menus;

import java.util.StringJoiner;

/**
 * @author sbrit
 */
public enum ArticlesType {
    ADVERTISERS("Advertisers"),
    PUBLISHERS("Publishers"),
    TOP_LEVEL_CLIENTS("Top level clients");

    private final String value;

    ArticlesType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ArticlesType.class.getSimpleName() + "[", "]")
                .add("value='" + value + "'")
                .toString();
    }
}
