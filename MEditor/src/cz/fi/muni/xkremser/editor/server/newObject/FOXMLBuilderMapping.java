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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
public class FOXMLBuilderMapping {

    private static final Logger LOGGER = Logger.getLogger(FOXMLBuilderMapping.class);

    private static final Map<DigitalObjectModel, Class<? extends FoxmlBuilder>> MAP =
            new HashMap<DigitalObjectModel, Class<? extends FoxmlBuilder>>(DigitalObjectModel.values().length);
    static {
        MAP.put(DigitalObjectModel.MONOGRAPH, MonographBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICAL, PeriodicalBuilder.class);
        MAP.put(DigitalObjectModel.INTERNALPART, IntPartBuilder.class);
        MAP.put(DigitalObjectModel.PAGE, PageBuilder.class);
        MAP.put(DigitalObjectModel.MONOGRAPHUNIT, MonographUnitBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICALVOLUME, PeriodicalVolumeBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICALITEM, PeriodicalItemBuilder.class);
    }

    @SuppressWarnings("unchecked")
    public static FoxmlBuilder getBuilder(NewDigitalObject object) {
        try {
            Class<? extends FoxmlBuilder> clazz = MAP.get(object.getModel());
            if (clazz != null) {
                Constructor<? extends FoxmlBuilder>[] constructors =
                        (Constructor<? extends FoxmlBuilder>[]) clazz.getConstructors();
                if (constructors.length == 0) {
                    return null;
                } else {
                    return constructors[0].newInstance(object);
                }
            }
            return null;
            //            return new MonographBuilder(object);
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
