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

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import org.w3c.dom.Document;

import org.fedora.api.FedoraAPIA;
import org.fedora.api.FedoraAPIM;
import org.fedora.api.ObjectFactory;

import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;

// TODO: Auto-generated Javadoc
/**
 * This is main point to access to Fedora through REST-API.
 * 
 * @see FedoraAccessImpl
 * @see SecuredFedoraAccessImpl
 * @author xkremser
 */
public interface FedoraAccess {

    /**
     * Returns parsed rels-ext.
     * 
     * @param uuid
     *        Object uuid
     * @return the rels ext
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public Document getRelsExt(String uuid) throws IOException;

    /**
     * Returns KrameriusModel parsed from given document.
     * 
     * @param relsExt
     *        RELS-EXT document
     * @return the kramerius model
     * @see KrameriusModel
     */
    public DigitalObjectModel getDigitalObjectModel(Document relsExt);

    /**
     * Returns KrameriusModel of given object.
     * 
     * @param uuid
     *        uuid of object
     * @return the kramerius model
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public DigitalObjectModel getDigitalObjectModel(String uuid) throws IOException;

    /**
     * Return parsed biblio mods stream.
     * 
     * @param uuid
     *        the uuid
     * @return the biblio mods
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public Document getBiblioMods(String uuid) throws IOException;

    /**
     * Returns DC stream.
     * 
     * @param uuid
     *        the uuid
     * @return the dC
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public Document getDC(String uuid) throws IOException;

    /**
     * Returns input stream of thumbnail.
     * 
     * @param uuid
     *        the uuid
     * @return the thumbnail
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public InputStream getThumbnail(String uuid) throws IOException;

    /**
     * Returns djvu image of the object.
     * 
     * @param uuid
     *        the uuid
     * @return the image full
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public InputStream getImageFULL(String uuid) throws IOException;

    /**
     * Check whether full image is available, is present and accessible.
     * 
     * @param uuid
     *        the uuid
     * @return true, if is image full available
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public boolean isImageFULLAvailable(String uuid) throws IOException;

    /**
     * Checks whether content is accessible.
     * 
     * @param uuid
     *        uuid of object which can be protected
     * @return true, if is content accessible
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public boolean isContentAccessible(String uuid) throws IOException;

    /**
     * Checks whether content is present.
     * 
     * @param uuid
     *        uuid of object which can be protected
     * @return true, if is digital object present
     */
    public boolean isDigitalObjectPresent(String uuid);

    /**
     * Gets the aPIA.
     * 
     * @return the aPIA
     */
    public FedoraAPIA getAPIA();

    /**
     * Gets the aPIM.
     * 
     * @return the aPIM
     */
    public FedoraAPIM getAPIM();

    /**
     * Gets the object factory.
     * 
     * @return the object factory
     */
    public ObjectFactory getObjectFactory();

    /**
     * Gets the pages uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the pages uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getPagesUuid(String uuid) throws IOException;

    /**
     * Gets the checks if is on pages uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the checks if is on pages uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getIsOnPagesUuid(String uuid) throws IOException;

    /**
     * Gets the int comp parts uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the int comp parts uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getIntCompPartsUuid(String uuid) throws IOException;

    /**
     * Gets the monograph units uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the monograph units uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getMonographUnitsUuid(String uuid) throws IOException;

    public List<String> getChildrenUuid(String uuid,
                                        DigitalObjectModel parentModel,
                                        DigitalObjectModel childModel) throws IOException;

    /**
     * Gets the periodical items uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the periodical items uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getPeriodicalItemsUuid(String uuid) throws IOException;

    /**
     * Gets the volumes uuid.
     * 
     * @param uuid
     *        the uuid
     * @return the volumes uuid
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public List<String> getVolumesUuid(String uuid) throws IOException;

    /**
     * Gets the fOXML.
     * 
     * @param uuid
     *        the uuid
     * @return the fOXML
     */
    public String getFOXML(String uuid);

    /**
     * Gets the ocr.
     * 
     * @param uuid
     *        the uuid
     * @return the ocr
     */
    public String getOcr(String uuid);

    /**
     * Gets the input stream of the foxml
     * 
     * @param uuid
     *        the uuid of the needed digital object
     * @return the input stream
     */
    public InputStream getFOXMLInputStream(String uuid);

    /**
     * @param pid
     * @param datastreamName
     * @return
     */
    public String getMimeTypeForStream(String pid, String datastreamName) throws IOException;

}
