package net.freifunk.paderborn.nodes.api;

/**
 * Enumeration for Link Types.
 */
public enum LinkType {
    VPN(Constants.NAME_VPN), CLIENT(Constants.NAME_CLIENT), UNKNOWN(null);
    private String name;

    LinkType(String name) {
        this.name = name;
    }

    public static LinkType fromName(String name) {
        switch (name) {
            case Constants.NAME_VPN:
                return VPN;
            case Constants.NAME_CLIENT:
                return CLIENT;
            default:
                return UNKNOWN;
        }
    }

    private static class Constants {
        public static final String NAME_VPN = "vpn";
        public static final String NAME_CLIENT = "client";
    }
}
