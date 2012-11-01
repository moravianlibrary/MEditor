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

package cz.mzk.editor.server.DAO;

import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.ImageItem;

// TODO: Auto-generated Javadoc
/**
 * The Class InputQueueItemDAOImpl.
 */
public class ImageResolverDAOImpl
        extends AbstractDAO
        implements ImageResolverDAO {

    //  image (id, identifier, shown, old_fs_path, imagefile) -> image (identifier, shown, old_fs_path, imagefile)
    //                                                                  identifier, shown, old_fs_path, imagefile

    /** The Constant DELETE_ITEMS_STATEMENT. */
    public static final String DELETE_ITEMS_STATEMENT = "DELETE FROM " + Constants.TABLE_IMAGE
            + " WHERE shown < (NOW() - INTERVAL '%s day')";

    /** The Constant SELECT_ITEMS_FOR_DELETION_STATEMENT. */
    public static final String SELECT_ITEMS_FOR_DELETION_STATEMENT = "SELECT imageFile FROM "
            + Constants.TABLE_IMAGE + " WHERE shown < (NOW() - INTERVAL '%s day')";

    /** The Constant SELECT_IDENT_FILE_STATEMENT. */
    public static final String SELECT_IDENT_FILE_STATEMENT = "SELECT identifier, imageFile FROM "
            + Constants.TABLE_IMAGE + " WHERE old_fs_path = ((?))";

    /** The Constant SELECT_OLD_FS_PATH_STATEMENT. */
    public static final String SELECT_OLD_FS_PATH_STATEMENT = "SELECT old_fs_path FROM "
            + Constants.TABLE_IMAGE + " WHERE imagefile LIKE ((?))";

    /** The Constant UPDATE_SHOWN_ITEM_STATEMENT. */
    public static final String UPDATE_SHOWN_ITEM_STATEMENT = "UPDATE " + Constants.TABLE_IMAGE
            + " SET shown = CURRENT_TIMESTAMP WHERE identifier = (?)";

    /** The Constant INSERT_ITEM_STATEMENT. */
    public static final String INSERT_ITEM_STATEMENT = "INSERT INTO " + Constants.TABLE_IMAGE
            + " (identifier, imageFile, old_fs_path, shown) VALUES ((?),(?),(?),(CURRENT_TIMESTAMP))";

    /** The Constant DELETE_ITEM_STATEMENT. */
    public static final String DELETE_ITEM_STATEMENT = "DELETE FROM " + Constants.TABLE_IMAGE
            + " WHERE identifier = (?)";

    /** The Constant SELECT_NEW_IMAGE_FILE_PATH. */
    public static final String SELECT_NEW_IMAGE_FILE_PATH = "SELECT imageFile FROM " + Constants.TABLE_IMAGE
            + " WHERE identifier = (?)";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ImageResolverDAOImpl.class);

    /**
     * Gets the item insert statement.
     * 
     * @param item
     *        the item
     * @return the item insert statement
     * @throws DatabaseException
     *         the database exception
     */
    private PreparedStatement getItemInsertStatement(ImageItem item) throws DatabaseException {
        PreparedStatement itemStmt = null;
        try {
            itemStmt = getConnection().prepareStatement(INSERT_ITEM_STATEMENT);
            itemStmt.setString(1, item.getIdentifier());
            itemStmt.setString(2, item.getJpeg2000FsPath());
            itemStmt.setString(3, item.getJpgFsPath());
        } catch (SQLException ex) {
            LOGGER.error("Could not get insert item statement " + itemStmt, ex);
        }
        return itemStmt;
    }

    /**
     * Gets the item delete statement.
     * 
     * @param identifier
     *        the identifier
     * @return the item delete statement
     * @throws DatabaseException
     *         the database exception
     */
    private PreparedStatement getItemDeleteStatement(String identifier) throws DatabaseException {
        PreparedStatement deleteItemStmt = null;
        try {
            deleteItemStmt = getConnection().prepareStatement(DELETE_ITEM_STATEMENT);
            deleteItemStmt.setString(1, identifier);
        } catch (SQLException ex) {
            LOGGER.error("Could not get delete item statement " + deleteItemStmt, ex);
        }
        return deleteItemStmt;
    }

    /**
     * Insert items.
     * 
     * @param toInsert
     *        the to insert
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public void insertItems(List<ImageItem> toInsert) throws DatabaseException {
        if (toInsert == null) throw new NullPointerException("toInsert");

        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        try {
            // TX start
            int updated = 0;
            for (ImageItem item : toInsert) {
                getItemDeleteStatement(item.getIdentifier()).executeUpdate();
                updated += getItemInsertStatement(item).executeUpdate();
            }
            if (updated == toInsert.size()) {
                getConnection().commit();
                LOGGER.debug("DB has been updated.");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback!");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Resolve items.
     * 
     * @param oldJpgFsPaths
     *        the old jpg fs paths
     * @return the array list
     * @throws DatabaseException
     *         the database exception {@inheritDoc}
     */
    @Override
    public ArrayList<String> resolveItems(List<String> oldJpgFsPaths) throws DatabaseException {
        if (oldJpgFsPaths == null) throw new NullPointerException("oldJpgFsPaths");
        ArrayList<String> ret = new ArrayList<String>(oldJpgFsPaths.size());
        if (!oldJpgFsPaths.isEmpty()) {
            try {
                Connection con = getConnection();
                PreparedStatement selectSt = null, updateSt = null;
                selectSt = con.prepareStatement(SELECT_IDENT_FILE_STATEMENT);
                updateSt = con.prepareStatement(UPDATE_SHOWN_ITEM_STATEMENT);
                for (String oldJpgFsPath : oldJpgFsPaths) {
                    ret.add(resolveItem(oldJpgFsPath, selectSt, updateSt));
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        return ret;
    }

    /**
     * Resolve item.
     * 
     * @param oldJpgFsPath
     *        the old jpg fs path
     * @param selectSt
     *        the select st
     * @param updateSt
     *        the update st
     * @return the string
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     *         the sQL exception
     */
    private String resolveItem(String oldJpgFsPath, PreparedStatement selectSt, PreparedStatement updateSt)
            throws DatabaseException, SQLException {
        if (oldJpgFsPath == null || "".equals(oldJpgFsPath)) throw new NullPointerException("oldJpgFsPath");
        String ret = null;
        String identifier = null;

        try {
            selectSt.setString(1, oldJpgFsPath);
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                identifier = rs.getString("identifier");
                ret = rs.getString("imageFile");
            }
        } catch (SQLException e) {
            selectSt.clearWarnings();
            LOGGER.error(e);
        }

        // no need for closing the connection
        try {
            if (ret != null) {
                File img = new File(ret);
                if (identifier != null && img.exists() && img.length() > 0) {
                    updateSt.setString(1, identifier);
                    updateSt.executeUpdate();
                    return ret;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        // no need for closing the connection
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<String> cacheAgeingProcess(int numberOfDays) throws DatabaseException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement statement = null;
        ArrayList<String> ret = new ArrayList<String>();
        try {
            // TX start
            statement =
                    getConnection().prepareStatement(String.format(SELECT_ITEMS_FOR_DELETION_STATEMENT,
                                                                   String.valueOf(numberOfDays)));
            ResultSet rs = statement.executeQuery();
            int i = 0;
            int rowsAffected = 0;
            while (rs.next()) {
                ret.add(rs.getString("imageFile"));
                i++;
            }
            if (i > 0) {
                statement =
                        getConnection().prepareStatement(String.format(DELETE_ITEMS_STATEMENT,
                                                                       String.valueOf(numberOfDays)));
                rowsAffected = statement.executeUpdate();
            }
            if (rowsAffected == i) {
                getConnection().commit();
                LOGGER.debug("DB has been updated.");
                LOGGER.debug(i + " images are going to be removed.");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback!");
            }
            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOldJpgFsPath(String imageFile) throws DatabaseException {
        if (imageFile == null || "".equals(imageFile)) throw new NullPointerException("imageFile");
        PreparedStatement statement = null;
        String oldJpgFsPath = null;

        try {
            // TX start
            statement = getConnection().prepareStatement(SELECT_OLD_FS_PATH_STATEMENT);
            String s = "%" + imageFile + "%";
            statement.setString(1, s);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                oldJpgFsPath = rs.getString("old_fs_path");
            }

            // TX end
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }

        return oldJpgFsPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNewImageFilePath(String identifier) throws DatabaseException {
        if (identifier == null || "".equals(identifier)) throw new NullPointerException("identifier");
        PreparedStatement statement = null;
        String newFilePath = null;

        try {
            statement = getConnection().prepareStatement(SELECT_NEW_IMAGE_FILE_PATH);
            statement.setString(1, identifier);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                newFilePath = rs.getString("imageFile");
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }

        return newFilePath;
    }
}
