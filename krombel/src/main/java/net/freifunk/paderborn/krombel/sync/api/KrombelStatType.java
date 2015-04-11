package net.freifunk.paderborn.krombel.sync.api;

import net.freifunk.paderborn.krombel.R;

/**
 * Different types of krombel's stats
 */
public enum KrombelStatType {
    CURRENT_NODES(0, R.string.descrCurrentNodes), CURRENT_CLIENTS(1, R.string.descrCurrentClients), MAX_NODES(2, R.string.descrMaxNodes), MAX_CLIENTS(3, R.string.descrMaxClients);
    public final int stringResId;
    private final int ordinal;

    KrombelStatType(int ordinal, int stringResId) {
        this.ordinal = ordinal;
        this.stringResId = stringResId;
    }

}
