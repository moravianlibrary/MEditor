/*
 * Metadata Editor
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

package cz.fi.muni.xkremser.editor.server.newObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Namespace;

import cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces;

/**
 * @author Martin Řehánek <rehan at mzk.cz>
 */
public class Namespaces {

    public static final Namespace foxml = DocumentHelper
            .createNamespace("foxml", FedoraNamespaces.FOXML_NAMESPACE_URI);
    public static final Namespace fedora = DocumentHelper
            .createNamespace("fedora", FedoraNamespaces.FEDORA_NAMESPACE_URI);
    public static final Namespace fedora_model = DocumentHelper
            .createNamespace("fedora-model", FedoraNamespaces.FEDORA_MODELS_URI);
    public static final Namespace kramerius = DocumentHelper
            .createNamespace("kramerius", FedoraNamespaces.ONTOLOGY_RELATIONSHIP_NAMESPACE_URI);
    public static final Namespace mods = DocumentHelper.createNamespace("mods",
                                                                        FedoraNamespaces.BIBILO_MODS_URI);
    public static final Namespace xsi = DocumentHelper.createNamespace("xsi",
                                                                       FedoraNamespaces.SCHEMA_NAMESPACE_URI);
    public static final Namespace rdf = DocumentHelper.createNamespace("rdf",
                                                                       FedoraNamespaces.RDF_NAMESPACE_URI);
    public static final Namespace dc = DocumentHelper
            .createNamespace("dc", FedoraNamespaces.DC_NAMESPACE_URI);
    public static final Namespace oai_dc = DocumentHelper
            .createNamespace("oai_dc", FedoraNamespaces.OAI_DC_NAMESPACE_URI);
    public static final Namespace tei = DocumentHelper.createNamespace("tei",
                                                                       FedoraNamespaces.TEI_NAMESPACE_URI);
    public static final Namespace oai = DocumentHelper.createNamespace("oai",
                                                                       FedoraNamespaces.OAI_NAMESPACE_URI);
    public static final Namespace adm = DocumentHelper.createNamespace("adm",
                                                                       FedoraNamespaces.ADM_NAMESPACE_URI);

    private static final Map<String, String> unmodifiablePrefixUriMap;
    private static final Map<String, Namespace> prefixNamespaceMap = new HashMap<String, Namespace>();

    static {
        prefixNamespaceMap.put(foxml.getPrefix(), foxml);
        prefixNamespaceMap.put(fedora.getPrefix(), fedora);
        prefixNamespaceMap.put(fedora_model.getPrefix(), fedora_model);
        prefixNamespaceMap.put(kramerius.getPrefix(), kramerius);
        prefixNamespaceMap.put(mods.getPrefix(), mods);
        prefixNamespaceMap.put(xsi.getPrefix(), xsi);
        prefixNamespaceMap.put(rdf.getPrefix(), rdf);
        prefixNamespaceMap.put(dc.getPrefix(), dc);
        prefixNamespaceMap.put(oai_dc.getPrefix(), oai_dc);
        prefixNamespaceMap.put(tei.getPrefix(), tei);
        prefixNamespaceMap.put(oai.getPrefix(), oai);
        prefixNamespaceMap.put(adm.getPrefix(), adm);

        unmodifiablePrefixUriMap = initPrefixUriMap(prefixNamespaceMap);
    }

    private static Map<String, String> initPrefixUriMap(Map<String, Namespace> prefixNamespaceMap) {
        Map<String, String> prefixUriMap = new HashMap<String, String>();
        for (Namespace ns : prefixNamespaceMap.values()) {
            prefixUriMap.put(ns.getPrefix(), ns.getURI());
        }
        return Collections.unmodifiableMap(prefixUriMap);
    }

    public static Map<String, String> getPrefixUriMap() {
        return unmodifiablePrefixUriMap;
    }

    public static Namespace getNamespace(String prefix) {
        return prefixNamespaceMap.get(prefix);
    }
}
