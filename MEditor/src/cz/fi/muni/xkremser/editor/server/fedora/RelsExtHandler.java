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
package cz.fi.muni.xkremser.editor.server.fedora;

import org.w3c.dom.Element;

import cz.fi.muni.xkremser.editor.client.FedoraRelationship;

// TODO: Auto-generated Javadoc
/**
 * Interface allows to user to be informed of events during processRelsExt
 * method call.
 * 
 * @author pavels
 * @see FedoraAccess#processRelsExt(org.w3c.dom.Document, RelsExtHandler)
 * @see FedoraAccess#processRelsExt(String, RelsExtHandler)
 */
public interface RelsExtHandler {

	/**
	 * Accept or deny this relation. If method returns false, algortighm skip this
	 * element and continues with next
	 * 
	 * @param relation
	 *          Relation type
	 * @return true, if successful
	 */
	public boolean accept(FedoraRelationship relation);

	/**
	 * Handle processing element.
	 * 
	 * @param elm
	 *          Processing element
	 * @param relation
	 *          Type of relation
	 * @param level
	 *          the level
	 */
	public void handle(Element elm, FedoraRelationship relation, int level);

	/**
	 * Break process.
	 * 
	 * @return true, if successful
	 */
	public boolean breakProcess();
}
