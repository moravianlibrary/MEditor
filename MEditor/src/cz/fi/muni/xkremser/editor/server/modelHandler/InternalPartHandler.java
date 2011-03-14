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
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class InternalPartHandler.
 */
public class InternalPartHandler extends DigitalObjectHandler {

	/** The page handler. */
	private transient final PageHandler pageHandler;

	/**
	 * Instantiates a new internal part handler.
	 * 
	 * @param fedoraAccess
	 *          the fedora access
	 * @param pageHandler
	 *          the page handler
	 */
	@Inject
	public InternalPartHandler(@Named("securedFedoraAccess") FedoraAccess fedoraAccess, PageHandler pageHandler) {
		super(fedoraAccess);
		this.pageHandler = pageHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.server.modelHandler.DigitalObjectHandler#
	 * getDigitalObject(java.lang.String)
	 */
	@Override
	public AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) {
		InternalPartDetail detail = new InternalPartDetail(findRelated ? getRelated(uuid) : null);
		DublinCore dc = handleDc(uuid, findRelated);
		ArrayList<PageDetail> pages = new ArrayList<PageDetail>();
		try {
			if (findRelated) {
				List<String> pageUuids = getFedoraAccess().getIsOnPagesUuid(uuid);
				for (String pageUuid : pageUuids) {
					pages.add((PageDetail) pageHandler.getDigitalObject(pageUuid, false));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		detail.setDc(dc);
		detail.setPages(pages);

		return detail;
	}

}
