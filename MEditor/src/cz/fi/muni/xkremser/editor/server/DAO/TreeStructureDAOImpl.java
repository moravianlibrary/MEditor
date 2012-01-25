/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.server.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureNode;

/**
 * @author Jiri Kremser
 * @version 25. 1. 2011
 */
public class TreeStructureDAOImpl
        extends AbstractDAO
        implements TreeStructureDAO {

    public static final String SELECT_INFOS = "SELECT * FROM " + Constants.TABLE_TREE_STRUCTURE_NAME
            + " WHERE user_id = (?)";

    public static final String SELECT_NODES = "SELECT * FROM " + Constants.TABLE_TREE_STRUCTURE_NODE_NAME
            + " WHERE tree_id = (?)";

    public static final String DELETE_NODES = "DELETE FROM " + Constants.TABLE_TREE_STRUCTURE_NODE_NAME
            + " WHERE tree_id = (?)";

    public static final String DELETE_INFO = "DELETE FROM " + Constants.TABLE_TREE_STRUCTURE_NAME
            + " WHERE id = (?)";

    public static final String INSERT_INFO = "INSERT INTO" + Constants.TABLE_TREE_STRUCTURE_NAME
            + " (user_id, created, description) VALUES ((?), (CURRENT_TIMESTAMP), (?))";

    public static final String INSERT_NODE =
            "INSERT INTO"
                    + Constants.TABLE_TREE_STRUCTURE_NODE_NAME
                    + " (tree_id, prop_id, prop_parent, prop_name, prop_picture, prop_type, prop_type_id, prop_page_type, prop_date_issued, prop_exist) VALUES ((?), (?), (?), (?), (?), (?), (?), (?), (?), (?))";

    private static final Logger LOGGER = Logger.getLogger(TreeStructureDAOImpl.class);

    /**
     * {@inheritDoc}
     * 
     * @throws DatabaseException
     */
    @Override
    public ArrayList<TreeStructureInfo> getSavedStructuresOfUser(int userId) throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<TreeStructureInfo> retList = new ArrayList<TreeStructureInfo>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_INFOS);
            selectSt.setInt(1, userId);
        } catch (SQLException e) {
            LOGGER.error("Could not get select infos statement", e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                retList.add(new TreeStructureInfo(rs.getLong("id"), formatter.format(rs
                        .getTimestamp("created")), rs.getString("description")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSavedStructure(long id) throws DatabaseException {
        PreparedStatement deleteSt1 = null, deleteSt2 = null;
        try {
            deleteSt1 = getConnection().prepareStatement(DELETE_NODES);
            deleteSt1.setLong(1, id);
            deleteSt1.executeUpdate();

            deleteSt2 = getConnection().prepareStatement(DELETE_INFO);
            deleteSt2.setLong(1, id);
            deleteSt2.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Could not delete tree structure (info) with id " + id + "\n Query1: " + deleteSt1
                    + "\n Query2: " + deleteSt2, e);
        } finally {
            closeConnection();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<TreeStructureNode> loadStructure(long structureId) throws DatabaseException {
        PreparedStatement selectSt = null;
        ArrayList<TreeStructureNode> retList = new ArrayList<TreeStructureNode>();
        try {
            selectSt = getConnection().prepareStatement(SELECT_NODES);
            selectSt.setLong(1, structureId);
        } catch (SQLException e) {
            LOGGER.error("Could not get select nodes statement", e);
        }

        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                retList.add(new TreeStructureNode(rs.getString("prop_id"), rs.getString("prop_parent"), rs
                        .getString("prop_name"), rs.getString("prop_picture"), rs.getString("prop_type"), rs
                        .getString("prop_type_id"), rs.getString("prop_page_type"), rs
                        .getString("prop_date_issued"), rs.getBoolean("prop_exist")));
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
        } finally {
            closeConnection();
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStructure(int userId, TreeStructureInfo info, ArrayList<TreeStructureNode> structure)
            throws DatabaseException {
        if (info == null) throw new NullPointerException("info");
        if (structure == null) throw new NullPointerException("structure");
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement insertInfoSt = null, insSt = null;
        try {
            // TX start
            insertInfoSt = getConnection().prepareStatement(INSERT_INFO, Statement.RETURN_GENERATED_KEYS);
            insertInfoSt.setInt(1, userId);
            insertInfoSt.setString(2, info.getDescription());
            insertInfoSt.executeQuery();
            ResultSet rs = insertInfoSt.getGeneratedKeys();
            int key = -1;
            if (rs.next()) {
                key = rs.getInt(1);
            }
            if (key == -1) {
                getConnection().rollback();
                throw new DatabaseException("Unable to obtain new id from DB when executing query: "
                        + insertInfoSt);
            }
            int total = 0;
            for (TreeStructureNode node : structure) {
                insSt = getConnection().prepareStatement(INSERT_NODE);
                insSt.setLong(1, key);
                insSt.setString(2, node.getPropId());
                insSt.setString(3, node.getPropParent());
                insSt.setString(4, node.getPropName());
                insSt.setString(5, node.getPropPicture());
                insSt.setString(6, node.getPropType());
                insSt.setString(7, node.getPropTypeId());
                insSt.setString(8, node.getPropPageType());
                insSt.setString(9, node.getPropDateIssued());
                insSt.setBoolean(10, node.getPropExist());
                total += insSt.executeUpdate();
            }
            if (total != structure.size()) {
                getConnection().rollback();
                throw new DatabaseException("Unable to insert _ALL_ nodes: " + total
                        + " nodes were inserted of " + structure.size());
            }

            getConnection().commit();
            // TX end
        } catch (SQLException e) {
            LOGGER.error("Queries: \"" + insertInfoSt + "\" and \"" + insSt + "\"", e);
        } finally {
            closeConnection();
        }
    }
}
