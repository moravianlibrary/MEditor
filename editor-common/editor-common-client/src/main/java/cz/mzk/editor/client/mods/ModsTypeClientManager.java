/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.client.mods;

import java.util.List;

import com.smartgwt.client.widgets.form.fields.FormItem;

import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version May 7, 2012
 */
public interface ModsTypeClientManager {

    public String getSubtitle();

    public String getTitle();

    public String getLevelName();

    public String getType();

    public String getPartName();

    public String getPartNumber();

    public String getNote();

    public String getDateIssued();

    public String getPublisher();

    public String getShelfLocator();

    public String getPlace();

    public String getExtent();

    public void modifyOriginInfoList(String publisher, String dateIssued);

    public void modifyTitle(String title, DigitalObjectModel model);

    public void modifySubtitle(String subtitle);

    public void modifyNames(List<FormItem> authorPartsOfName1,
                            List<FormItem> authorPartsOfName2,
                            String author1,
                            String author2);

    public void modifyShelfLocatorAndPlace(String shelfLocator, String place);

    public void modifyExtent(String extent);

    public void modifyNote(String note);

    /**
     * @param partNumber
     */
    public void modifyPartNumber(String partNumber);

    /**
     * @param levelName
     */
    public void modifyLevelName(String levelName);

    /**
     * @param type
     */
    public void modifyType(String type);

    /**
     * @param partNumber
     */
    public void modifyPartName(String partName);

}
