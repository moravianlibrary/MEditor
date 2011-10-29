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

import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.BIBILO_MODS_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.DC_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.FEDORA_MODELS_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.FOXML_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.KRAMERIUS_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.OAI_DC_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.OAI_NAMESPACE_URI;
import static cz.fi.muni.xkremser.editor.shared.domain.FedoraNamespaces.RDF_NAMESPACE_URI;

// TODO: Auto-generated Javadoc
/**
 * The Class FedoraNamespaceContext.
 */
public class FedoraNamespaceContext
        implements NamespaceContext {

    /** The Constant MAP_PREFIX2URI. */
    private static final Map<String, String> MAP_PREFIX2URI = new IdentityHashMap<String, String>();

    /** The Constant MAP_URI2PREFIX. */
    private static final Map<String, String> MAP_URI2PREFIX = new IdentityHashMap<String, String>();

    static {
        MAP_PREFIX2URI.put("mods", BIBILO_MODS_URI);
        MAP_PREFIX2URI.put("dc", DC_NAMESPACE_URI);
        MAP_PREFIX2URI.put("fedora-models", FEDORA_MODELS_URI);
        MAP_PREFIX2URI.put("kramerius", KRAMERIUS_URI);
        MAP_PREFIX2URI.put("rdf", RDF_NAMESPACE_URI);
        MAP_PREFIX2URI.put("oai", OAI_NAMESPACE_URI);
        MAP_PREFIX2URI.put("oai_dc", OAI_DC_NAMESPACE_URI);
        MAP_PREFIX2URI.put("foxml", FOXML_NAMESPACE_URI);

        for (Map.Entry<String, String> entry : MAP_PREFIX2URI.entrySet()) {
            MAP_URI2PREFIX.put(entry.getValue(), entry.getKey());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     */
    @Override
    public String getNamespaceURI(String arg0) {
        return MAP_PREFIX2URI.get(arg0.intern());
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     */
    @Override
    public String getPrefix(String arg0) {
        return MAP_URI2PREFIX.get(arg0.intern());
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     */
    @Override
    public Iterator getPrefixes(String arg0) {
        String prefixInternal = MAP_URI2PREFIX.get(arg0.intern());
        if (prefixInternal != null) {
            return Arrays.asList(prefixInternal).iterator();
        } else {
            return Collections.emptyList().iterator();
        }
    }
}
