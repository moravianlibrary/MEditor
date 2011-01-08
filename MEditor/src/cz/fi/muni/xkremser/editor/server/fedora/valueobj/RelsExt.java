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
package cz.fi.muni.xkremser.editor.server.fedora.valueobj;

import java.util.LinkedList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Value objekt pro naplneni dat RELS-EXT.
 *
 * @author xholcik
 */
public class RelsExt {

	/** The Constant ITEM_ID. */
	public static final String ITEM_ID = "itemID";

	/** The Constant HAS_MODEL. */
	public static final String HAS_MODEL = "hasModel";

	/** The Constant HAS_UNIT. */
	public static final String HAS_UNIT = "hasUnit";

	/** The Constant HAS_PAGE. */
	public static final String HAS_PAGE = "hasPage";

	/** The Constant HAS_ITEM. */
	public static final String HAS_ITEM = "hasItem";

	/** The Constant HAS_INT_COMP_PART. */
	public static final String HAS_INT_COMP_PART = "hasIntCompPart";

	/** The Constant HAS_VOLUME. */
	public static final String HAS_VOLUME = "hasVolume";

	/** The Constant HAS_DONATOR. */
	public static final String HAS_DONATOR = "hasDonator";

	/** The Constant IS_ON_PAGE. */
	public static final String IS_ON_PAGE = "isOnPage";

	/** The Constant POLICY. */
	public static final String POLICY = "policy";

	/** The Constant HANDLE. */
	public static final String HANDLE = "handle";

	/** The Constant CONTRACT. */
	public static final String CONTRACT = "contract";

	/** The Constant FILE. */
	public static final String FILE = "file";

	/** The Constant ISBN. */
	public static final String ISBN = "isbn";

	/** The Constant ISSN. */
	public static final String ISSN = "issn";

	/** The Constant EXTID. */
	public static final String EXTID = "extid";

	/** The pid. */
	private final String pid;

	/** The relations. */
	private final List<Relation> relations = new LinkedList<Relation>();

	/**
	 * Instantiates a new rels ext.
	 *
	 * @param pid the pid
	 * @param model the model
	 */
	public RelsExt(String pid, String model) {
		super();
		this.pid = pid;
		this.addRelation(HAS_MODEL, model, false);
		this.addRelation(ITEM_ID, pid, true);
	}

	/**
	 * Adds the relation.
	 *
	 * @param key the key
	 * @param id the id
	 * @param literal the literal
	 */
	public void addRelation(String key, String id, boolean literal) {
		if (id == null || "".equals(id))
			return;
		relations.add(new Relation(key, id, literal));
	}

	/**
	 * Gets the relations.
	 *
	 * @return the relations
	 */
	public List<Relation> getRelations() {
		return relations;
	}

	/**
	 * Gets the pid.
	 *
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * The Class Relation.
	 */
	public class Relation {

		/** The key. */
		private final String key;

		/** The id. */
		private final String id;

		/** The literal. */
		private final boolean literal;

		/**
		 * Instantiates a new relation.
		 *
		 * @param key the key
		 * @param id the id
		 * @param literal the literal
		 */
		public Relation(String key, String id, boolean literal) {
			super();
			this.key = key;
			this.id = id;
			this.literal = literal;
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Checks if is literal.
		 *
		 * @return true, if is literal
		 */
		public boolean isLiteral() {
			return literal;
		}

	}

}
