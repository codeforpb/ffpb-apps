package net.freifunk.paderborn.nodes.persistence;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;

import net.freifunk.paderborn.nodes.api.*;

import java.sql.*;
import java.util.*;

/**
 * Created by ljan on 06.02.15.
 */
public class NodeDao extends BaseDaoImpl<Node, Long> {
    public NodeDao(ConnectionSource connectionSource, Class<Node> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public CreateOrUpdateStatus createOrUpdate(Node node) throws SQLException {
        List<Node> nodes = super.queryForEq(Node.REMOTE_ID, node.getRemoteId());
        if (nodes.size() == 0) {
            int createdLines = super.create(node);
            return new CreateOrUpdateStatus(true, false, createdLines);
        } else if (nodes.size() == 1) {
            int updatedLines = super.create(node);
            return new CreateOrUpdateStatus(false, true, updatedLines);
        }
        return new CreateOrUpdateStatus(false, false, 0);
    }
}
