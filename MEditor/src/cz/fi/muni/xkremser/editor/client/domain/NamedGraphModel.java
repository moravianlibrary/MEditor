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
package cz.fi.muni.xkremser.editor.client.domain;

import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.INTERNALPART;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.MONOGRAPH;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.MONOGRAPHUNIT;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.PAGE;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.PERIODICAL;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.PERIODICALITEM;
import static cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel.PERIODICALVOLUME;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship.hasIntCompPart;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship.hasItem;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship.hasPage;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship.hasUnit;
import static cz.fi.muni.xkremser.editor.client.domain.FedoraRelationship.hasVolume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Enum NamedGraphModel.
 */
public class NamedGraphModel implements IsSerializable {

	private static final Map<List<DigitalObjectModel>, FedoraRelationship> relations = new HashMap<List<DigitalObjectModel>, FedoraRelationship>();
	private static Map<DigitalObjectModel, List<DigitalObjectModel>> children = new HashMap<DigitalObjectModel, List<DigitalObjectModel>>();

	static {
		/** The MONOGRAPH. */
		putRelationship(MONOGRAPH, hasPage, PAGE);
		putRelationship(MONOGRAPH, hasUnit, MONOGRAPHUNIT);
		putRelationship(MONOGRAPH, hasIntCompPart, INTERNALPART);
		putRelationship(MONOGRAPHUNIT, hasPage, PAGE);
		putRelationship(MONOGRAPHUNIT, hasIntCompPart, INTERNALPART);
		putRelationship(INTERNALPART, hasPage, PAGE);
		/** The PERIODICAL. */
		putRelationship(PERIODICAL, hasPage, PAGE);
		putRelationship(PERIODICAL, hasVolume, PERIODICALVOLUME);
		putRelationship(PERIODICALVOLUME, hasPage, PAGE);
		putRelationship(PERIODICALVOLUME, hasItem, PERIODICALITEM);
		putRelationship(PERIODICALVOLUME, hasIntCompPart, INTERNALPART);
		putRelationship(PERIODICALITEM, hasPage, PAGE);
		putRelationship(PERIODICALITEM, hasIntCompPart, INTERNALPART);
	}

	private static void putRelationship(DigitalObjectModel source, FedoraRelationship relationship, DigitalObjectModel target) {
		NamedGraphModel.relations.put(Arrays.asList(target, source), relationship);
		putChild(source, target);
	}

	public static FedoraRelationship getRelationship(DigitalObjectModel source, DigitalObjectModel target) {
		return relations.get(Arrays.asList(target, source));
	}

	private static void putChild(DigitalObjectModel parent, DigitalObjectModel child) {
		if (children.get(parent) == null) {
			children.put(parent, new ArrayList<DigitalObjectModel>());
		}
		children.get(parent).add(child);
	}

	public static List<DigitalObjectModel> getChildren(DigitalObjectModel parent) {
		return children.get(parent);
	}

}