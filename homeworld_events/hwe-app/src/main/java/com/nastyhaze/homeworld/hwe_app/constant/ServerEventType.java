package com.nastyhaze.homeworld.hwe_app.constant;

/**
 * Enumeration for Server-side Event Types
 */
public enum ServerEventType {
    TILE_SNAPSHOT("tile-snapshot"),
    TILE_UPDATE("tile-update"),
    HEARTBEAT("keepalive");

    private final String desc;

    ServerEventType(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return desc;
    }
}
