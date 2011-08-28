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

package cz.fi.muni.xkremser.editor.server;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import javax.xml.bind.JAXBElement;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.mods.AbstractType;
import cz.fi.muni.xkremser.editor.server.mods.DetailType;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.mods.ModsType;
import cz.fi.muni.xkremser.editor.server.mods.NamePartType;
import cz.fi.muni.xkremser.editor.server.mods.NameType;
import cz.fi.muni.xkremser.editor.server.mods.NoteType;
import cz.fi.muni.xkremser.editor.server.mods.ObjectFactory;
import cz.fi.muni.xkremser.editor.server.mods.PartType;
import cz.fi.muni.xkremser.editor.server.mods.SubjectType;
import cz.fi.muni.xkremser.editor.server.mods.SubjectType.Cartographics;
import cz.fi.muni.xkremser.editor.server.mods.TitleInfoType;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerUtils.
 */
public class ServerUtils {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ServerUtils.class);

    /**
     * Checks if is caused by exception.
     * 
     * @param t
     *        the t
     * @param type
     *        the type
     * @return true, if is caused by exception
     */
    public static boolean isCausedByException(Throwable t, Class<? extends Exception> type) {
        if (t == null) return false;
        Throwable aux = t;
        while (aux != null) {
            if (type.isInstance(aux)) return true;
            aux = aux.getCause();
        }
        return false;
    }

    public static void checkExpiredSession(Provider<HttpSession> httpSessionProvider) throws ActionException {
        checkExpiredSession(httpSessionProvider.get());
    }

    public static void checkExpiredSession(HttpSession session) throws ActionException {
        if (session.getAttribute(HttpCookies.SESSION_ID_KEY) == null) {
            throw new ActionException(Constants.SESSION_EXPIRED_FLAG + URLS.ROOT()
                    + (URLS.LOCALHOST() ? URLS.LOGIN_LOCAL_PAGE : URLS.LOGIN_PAGE));
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null || clazz.getName().contains("java.util.List")
                || clazz.getName().contains("java.lang.String")) {
            return Collections.<Field> emptyList();
        }
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }
        return fields;
    }

    private static boolean hasOnlyNullFields(Object object) {
        if (object == null) {
            return true;
        }
        boolean isNull = true;
        try {
            if (object.getClass().getName().contains("JAXBElement")) {
                JAXBElement<?> elem = (JAXBElement<?>) object;
                if (!elem.isNil()) {
                    return hasOnlyNullFields(elem.getValue());
                } else
                    return false;
            } else if (object.getClass().getName().contains("java.lang.String")) {
                return "".equals(((String) object).trim());
            }
            List<Field> fields = getAllFields(object.getClass());

            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.get(object) != null) {
                    if (field.getType().getName().contains("java.util.List")
                            && !((List<?>) field.get(object)).isEmpty()) {
                        List<Object> list = (List<Object>) field.get(object);
                        for (int i = 0; i < list.size(); i++) {
                            if (!hasOnlyNullFields(list.get(i))) {
                                isNull = false;
                                break;
                            }
                        }
                        if (!isNull) {
                            break;
                        }
                    } else if (field.getType().getName().contains("java.lang.String")
                            && !"".equals(((String) field.get(object)).trim())) {
                        isNull = false;
                        break;
                    } else if (field.getType().getName().contains("CodeOrText")
                            || field.getType().getName().contains("NameTypeAttribute")
                            || field.getType().getName().contains("PlaceAuthority")
                            || field.getType().getName().contains("Yes")) {
                        if (!"".equals(field.getClass().getField("value").get(field.get(object)))) {
                            isNull = false;
                            break;
                        }
                    } else if (field.getType().getName().contains("long")) {
                        continue;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        } catch (SecurityException e) {

        } catch (NoSuchFieldException e) {

        }
        return isNull;
    }

    public static <T> T collapseStructure(T object) {
        if (hasOnlyNullFields(object)) {
            return null;
        }
        List<Field> fields = getAllFields(object.getClass());
        try {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.get(object) != null) {
                    if (field.getType().getName().contains("java.util.List")) {
                        List<Object> list = (List<Object>) field.get(object);
                        List<Object> newList = new ArrayList<Object>(list.size());
                        boolean isNull = true;
                        for (int i = 0; i < list.size(); i++) {
                            Object o = collapseStructure(list.get(i));
                            if (o != null) {
                                isNull = false;
                                newList.add(o);
                            }
                        }
                        field.set(object, isNull ? null : newList);
                        continue;
                    } else if (field.getType().getName().contains("java.lang.String")) {
                        if ("".equals(((String) field.get(object)).trim())) {
                            field.set(object, null);
                        }
                        continue;
                    } else if (field.getType().getName().contains("CodeOrText")
                            || field.getType().getName().contains("NameTypeAttribute")
                            || field.getType().getName().contains("PlaceAuthority")
                            || field.getType().getName().contains("Yes")) {
                        if ("".equals(field.getClass().getField("value").get(field.get(object)))) {
                            field.set(object, null);
                        }
                        continue;
                    } else {
                        collapseStructure(field.get(object));
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        } catch (SecurityException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        } catch (NoSuchFieldException e) {
            LOGGER.error("Unable to inspect the structure via reflection", e);
        }
        if (hasOnlyNullFields(object)) {
            return null;
        }
        return object;
    }

    public static void main(String... args) {
        ModsCollection modsC = new ModsCollection();
        ModsType mods = new ModsType();
        ModsType mods2 = new ModsType();
        TitleInfoType titleInfo1 = new TitleInfoType();
        titleInfo1.setActuate("actuate bla");
        titleInfo1.setTitle("Capek");
        TitleInfoType titleInfo2 = new TitleInfoType();
        titleInfo2.setType("type");
        TitleInfoType titleInfo3 = new TitleInfoType();

        AbstractType abs1 = new AbstractType();
        AbstractType abs2 = new AbstractType();
        AbstractType abs3 = new AbstractType();

        NoteType not1 = new NoteType();
        not1.setValue("poznamka");
        NoteType not2 = new NoteType();
        not1.setValue("taky poznamka");
        NoteType not3 = new NoteType();
        not1.setValue("dalsi poznamka");
        NoteType not4 = new NoteType();

        ObjectFactory factory = new ObjectFactory();
        NameType name1 = new NameType();
        NameType name2 = new NameType();
        NameType name3 = new NameType();
        NamePartType part = new NamePartType();
        part.setValue("Pepik");
        name3.getNamePartOrDisplayFormOrAffiliation().add(factory.createNameTypeNamePart(part));

        SubjectType subject1 = new SubjectType();
        subject1.getTopicOrGeographicOrTemporal().add(factory.createSubjectTypeName(name1));
        subject1.getTopicOrGeographicOrTemporal().add(factory.createSubjectTypeName(name2));
        subject1.getTopicOrGeographicOrTemporal().add(factory.createSubjectTypeName(name3));

        SubjectType subject2 = new SubjectType();
        Cartographics cart = new Cartographics();
        cart.getCoordinates().add("      6    ");
        subject2.getTopicOrGeographicOrTemporal().add(factory.createSubjectTypeCartographics(cart));

        SubjectType subject3 = new SubjectType();
        Cartographics cart2 = new Cartographics();
        cart.getCoordinates().add("  ");
        cart.getCoordinates().add(" ");
        cart.getCoordinates().add("");
        subject2.getTopicOrGeographicOrTemporal().add(factory.createSubjectTypeCartographics(cart2));

        DetailType detail1 = new DetailType();
        detail1.getNumberOrCaptionOrTitle().add(factory.createDetailTypeNumber("42"));
        PartType part1 = new PartType();
        part1.getDetailOrExtentOrDate().add(detail1);

        DetailType detail2 = new DetailType();
        detail2.getNumberOrCaptionOrTitle().add(factory.createDetailTypeNumber("  "));
        PartType part2 = new PartType();
        part2.getDetailOrExtentOrDate().add(detail2);

        DetailType detail3 = new DetailType();
        detail3.getNumberOrCaptionOrTitle().add(factory.createDetailTypeTitle(" "));
        detail3.setType(" ");
        PartType part3 = new PartType();
        part3.getDetailOrExtentOrDate().add(detail3);

        mods.getModsGroup().add(titleInfo1);
        mods.getModsGroup().add(titleInfo2);
        mods.getModsGroup().add(titleInfo3);
        mods.getModsGroup().add(name1);
        mods.getModsGroup().add(name2);
        mods.getModsGroup().add(abs1);
        mods.getModsGroup().add(abs2);
        mods.getModsGroup().add(abs3);
        mods.getModsGroup().add(not1);
        mods.getModsGroup().add(not2);
        mods.getModsGroup().add(not3);
        mods.getModsGroup().add(not4);
        mods.getModsGroup().add(subject1);
        mods.getModsGroup().add(subject2);
        mods.getModsGroup().add(subject3);
        mods.getModsGroup().add(part1);
        mods.getModsGroup().add(part2);
        mods.getModsGroup().add(part3);

        mods2.getModsGroup().add(subject3);
        mods2.getModsGroup().add(titleInfo3);
        modsC.getMods().add(mods);
        modsC.getMods().add(mods2);
        System.out.println(BiblioModsUtils.toXML(modsC));
        System.out.println("**********************\n\n\n\n********************\n collapsed:");

        long start = System.currentTimeMillis();
        ModsCollection modsC2 = collapseStructure(modsC);
        System.out.println("time in millis taken to collapse = " + (System.currentTimeMillis() - start)
                + "ms");
        if (modsC2 != null) System.out.println(BiblioModsUtils.toXML(modsC2));
    }
}
