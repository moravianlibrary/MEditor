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

// TODO: Auto-generated Javadoc
/**
 * Namespaces in fedora.
 * 
 * @author pavels
 */
public interface FedoraNamespaces {

    public static final String FOXML_NAMESPACE_URI = "info:fedora/fedora-system:def/foxml#";

    /** RDF namespace. */
    public static final String RDF_NAMESPACE_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    /** Our ontology relationship namespace. */
    public static final String ONTOLOGY_RELATIONSHIP_NAMESPACE_URI =
            "http://www.nsdl.org/ontologies/relationships#";

    /** Dublin core namespace. */
    public static final String DC_NAMESPACE_URI = "http://purl.org/dc/elements/1.1/";

    /** The Constant FEDORA_MODELS_URI. */
    public static final String FEDORA_MODELS_URI = "info:fedora/fedora-system:def/model#";

    /** The Constant KRAMERIUS_URI. */
    public static final String KRAMERIUS_URI = "http://www.nsdl.org/ontologies/relationships#";

    /** The Constant BIBILO_MODS_URI. */
    public static final String BIBILO_MODS_URI = "http://www.loc.gov/mods/v3";

    /** OAI namespace. */
    public static final String OAI_NAMESPACE_URI = "http://www.openarchives.org/OAI/2.0/";
}
