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

// TODO: Auto-generated Javadoc
/**
 * Administrativni data pro obrazky.
 * 
 * @author xholcik
 */
public class ImageMetaData {

    /** The urn. */
    private String urn;

    /** The sici. */
    private String sici;

    /** The scanning device. */
    private String scanningDevice;

    /** The scanning parameters. */
    private String scanningParameters;

    /** The other imaging information. */
    private String otherImagingInformation;

    /**
     * Gets the scanning device.
     * 
     * @return the scanning device
     */
    public String getScanningDevice() {
        return scanningDevice;
    }

    /**
     * Sets the scanning device.
     * 
     * @param scanningDevice
     *        the new scanning device
     */
    public void setScanningDevice(String scanningDevice) {
        this.scanningDevice = scanningDevice;
    }

    /**
     * Gets the scanning parameters.
     * 
     * @return the scanning parameters
     */
    public String getScanningParameters() {
        return scanningParameters;
    }

    /**
     * Sets the scanning parameters.
     * 
     * @param scanningParameters
     *        the new scanning parameters
     */
    public void setScanningParameters(String scanningParameters) {
        this.scanningParameters = scanningParameters;
    }

    /**
     * Gets the other imaging information.
     * 
     * @return the other imaging information
     */
    public String getOtherImagingInformation() {
        return otherImagingInformation;
    }

    /**
     * Sets the other imaging information.
     * 
     * @param otherImagingInformation
     *        the new other imaging information
     */
    public void setOtherImagingInformation(String otherImagingInformation) {
        this.otherImagingInformation = otherImagingInformation;
    }

    /**
     * Gets the urn.
     * 
     * @return the urn
     */
    public String getUrn() {
        return urn;
    }

    /**
     * Sets the urn.
     * 
     * @param urn
     *        the new urn
     */
    public void setUrn(String urn) {
        this.urn = urn;
    }

    /**
     * Gets the sici.
     * 
     * @return the sici
     */
    public String getSici() {
        return sici;
    }

    /**
     * Sets the sici.
     * 
     * @param sici
     *        the new sici
     */
    public void setSici(String sici) {
        this.sici = sici;
    }

}
