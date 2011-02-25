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
import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.request4Adding.client.OpenIDRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAOImpl.
 */
public class RequestDAOImpl extends AbstractDAO implements RequestDAO {

	public static final String SELECT_IDENTITIES_STATEMENT = "SELECT id, name, identity, modified FROM request_for_adding ORDER BY modified";

	public static final String SELECT_IDENTITY_STATEMENT = "SELECT id, name, identity, modified FROM request_for_adding WHERE identity = (?) ORDER BY modified";

	/** The Constant INSERT_IDENTITY_STATEMENT. */
	public static final String INSERT_IDENTITY_STATEMENT = "INSERT INTO request_for_adding (name, identity, modified) VALUES ((?),(?), (CURRENT_TIMESTAMP))";

	/** The Constant DELETE_IDENTITY. */
	public static final String DELETE_IDENTITY = "DELETE FROM request_for_adding WHERE id = (?)";

	@Override
	public void removeOpenIDRequest(long id) {
		PreparedStatement deleteSt = null;
		try {
			deleteSt = getConnection().prepareStatement(DELETE_IDENTITY);
			deleteSt.setLong(1, id);
			deleteSt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Could not delete request for adding with id " + id, e);
		} finally {
			closeConnection();
		}
	}

	@Override
	public boolean addOpenIDRequest(String name, String openID) {
		if (name == null)
			throw new NullPointerException("name");
		if (openID == null || "".equals(openID))
			throw new NullPointerException("openID");

		boolean found = false;
		try {
			PreparedStatement findSt = getConnection().prepareStatement(SELECT_IDENTITY_STATEMENT);
			findSt.setString(1, openID);
			ResultSet rs = findSt.executeQuery();
			found = rs.next();
			if (!found) {
				PreparedStatement insSt = getConnection().prepareStatement(INSERT_IDENTITY_STATEMENT);
				insSt.setString(1, name);
				insSt.setString(2, openID);
				insSt.executeUpdate();
			}

		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return !found;
	}

	@Override
	public ArrayList<OpenIDRequest> getAllOpenIDRequests() {
		PreparedStatement selectSt = null;
		ArrayList<OpenIDRequest> retList = new ArrayList<OpenIDRequest>();
		try {
			selectSt = getConnection().prepareStatement(SELECT_IDENTITIES_STATEMENT);
		} catch (SQLException e) {
			logger.error("Could not get select roles statement", e);
		}
		try {
			ResultSet rs = selectSt.executeQuery();
			while (rs.next()) {
				retList.add(new OpenIDRequest(rs.getLong("id"), rs.getString("name"), rs.getString("identity"), rs.getDate("modified")));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			closeConnection();
		}
		return retList;
	}

}
