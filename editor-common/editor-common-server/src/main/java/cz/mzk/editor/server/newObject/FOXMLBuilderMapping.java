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

package cz.mzk.editor.server.newObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.inject.Injector;

import org.apache.log4j.Logger;

import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
public class FOXMLBuilderMapping {

    private static final Logger LOGGER = Logger.getLogger(FOXMLBuilderMapping.class);

    @Inject
    private static Injector injector;

    private static final Map<DigitalObjectModel, Class<? extends FoxmlBuilder>> MAP =
            new HashMap<DigitalObjectModel, Class<? extends FoxmlBuilder>>(DigitalObjectModel.values().length);
    static {
        MAP.put(DigitalObjectModel.MONOGRAPH, MonographBuilder.class);
        MAP.put(DigitalObjectModel.MAP, MonographBuilder.class);
        MAP.put(DigitalObjectModel.MANUSCRIPT, MonographBuilder.class);
        MAP.put(DigitalObjectModel.ARCHIVE, MonographBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICAL, PeriodicalBuilder.class);
        MAP.put(DigitalObjectModel.INTERNALPART, IntPartBuilder.class);
        MAP.put(DigitalObjectModel.PAGE, PageBuilder.class);
        MAP.put(DigitalObjectModel.MONOGRAPHUNIT, MonographUnitBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICALVOLUME, PeriodicalVolumeBuilder.class);
        MAP.put(DigitalObjectModel.PERIODICALITEM, PeriodicalItemBuilder.class);
        MAP.put(DigitalObjectModel.SOUND_UNIT, SoundUnitBuilder.class);
        MAP.put(DigitalObjectModel.GRAPHIC, MonographBuilder.class);
        //MAP.put(DigitalObjectModel.IMAGE_UNIT, MonographUnitBuilder.class);
        MAP.put(DigitalObjectModel.TRACK, TrackBuilder.class);
        MAP.put(DigitalObjectModel.SOUNDRECORDING, SoundRecordingBuilder.class);
        MAP.put(DigitalObjectModel.SHEETMUSIC, MonographBuilder.class);
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
                    FoxmlBuilder builder = constructors[0].newInstance(object);
                    injector.injectMembers(builder);
                    return builder;
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
