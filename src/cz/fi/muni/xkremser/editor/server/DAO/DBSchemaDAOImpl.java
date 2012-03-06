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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

/**
 * @author Jiri Kremser
 * @version 17. 1. 2011
 */
public class DBSchemaDAOImpl
        extends AbstractDAO
        implements DBSchemaDAO {

    public static final String SELECT_VERSION = "SELECT * FROM " + Constants.TABLE_VERSION_NAME
            + " WHERE id = 1";

    public static final String UPDATE_VERSION = "UPDATE " + Constants.TABLE_VERSION_NAME
            + " SET version = (?) WHERE id = 1";

    private static final Logger LOGGER = Logger.getLogger(DBSchemaDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConnect() {
        try {
            getConnection();
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkVersion(int version) throws DatabaseException {
        PreparedStatement statement = null;
        int versionDb = -1;
        try {
            statement = getConnection().prepareStatement(SELECT_VERSION);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                versionDb = Integer.parseInt(rs.getString("version"));
            }
            return versionDb == version;
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseException("unable to obtain the version of the DB", e);
        } catch (NumberFormatException nfe) {
            LOGGER.error(nfe);
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSchema(int version, String pathPrefix) throws DatabaseException {
        File dbSchema = new File(pathPrefix + File.separator + Constants.SCHEMA_PATH);
        if (!dbSchema.exists()) {
            throw new DatabaseException("Unable to find the file with DB schema "
                    + dbSchema.getAbsolutePath());
        }
        if (!dbSchema.canRead()) {
            throw new DatabaseException("Unable to read from the file with DB schema "
                    + dbSchema.getAbsolutePath());
        }
        try {
            ScriptRunner runner = new ScriptRunner(getConnection(), false, true);
            runner.setLogger(LOGGER);
            Reader reader = null;
            try {
                reader = new FileReader(dbSchema);
                runner.runScript(reader);
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to find the file with DB schema "
                        + dbSchema.getAbsolutePath(), e);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to read from file with DB schema "
                        + dbSchema.getAbsolutePath(), e);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to run SQL command: ", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                        reader = null;
                    }
                }
            }
            Writer writer = null;
            try {
                writer =
                        new BufferedWriter(new FileWriter(pathPrefix + File.separator
                                + Constants.SCHEMA_VERSION_PATH));
                writer.write(String.valueOf(version));
                writer.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new DatabaseException("Unable to write to file with DB schema version "
                        + dbSchema.getAbsolutePath(), e);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        e.printStackTrace();
                        writer = null;
                    }
                }
            }
        } finally {
            closeConnection();
        }
        updateVersionInDb(version);
    }

    private void updateVersionInDb(int version) throws DatabaseException {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(UPDATE_VERSION);
            statement.setInt(1, version);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DatabaseException("unable to update version number of the DB", e);
        } finally {
            closeConnection();
        }
    }
}
