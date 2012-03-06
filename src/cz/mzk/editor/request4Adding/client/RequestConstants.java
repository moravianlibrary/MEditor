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

package cz.mzk.editor.request4Adding.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * '/home/kremser/workspace/MEditor/src/cz/fi/muni/xkremser/editor/request4Adding/client/RequestConstants.propertie
 * s ' .
 */
public interface RequestConstants
        extends com.google.gwt.i18n.client.Constants {

    /**
     * Translated "Vaše žádost o založení účtu byla uložena do systému.".
     * 
     * @return translated "Vaše žádost o založení účtu byla uložena do systému."
     */
    @DefaultStringValue("Vaše žádost o založení účtu byla uložena do systému.")
    @Key("added1")
    String added1();

    /**
     * Translated "Vaše identita: ".
     * 
     * @return translated "Vaše identita: "
     */
    @DefaultStringValue("Vaše identita: ")
    @Key("added2")
    String added2();

    /**
     * Translated "Autorizace se nezdařila".
     * 
     * @return translated "Autorizace se nezdařila"
     */
    @DefaultStringValue("Autorizace se nezdařila")
    @Key("authFailed")
    String authFailed();

    /**
     * Translated "O přidání již bylo v minulosti požádáno.".
     * 
     * @return translated "O přidání již bylo v minulosti požádáno."
     */
    @DefaultStringValue("O přidání již bylo v minulosti požádáno.")
    @Key("exist")
    String exist();

    /**
     * Translated
     * "Pokud si přejete zaslat žádost o přidání Vašeho uživatelského účtu, klikněte prosím na tlačítko pod textem."
     * .
     * 
     * @return translated
     *         "Pokud si přejete zaslat žádost o přidání Vašeho uživatelského účtu, klikněte prosím na tlačítko pod textem."
     */
    @DefaultStringValue("Pokud si přejete zaslat žádost o přidání Vašeho uživatelského účtu, klikněte prosím na tlačítko pod textem.")
    @Key("ifYouWant")
    String ifYouWant();

    /**
     * Translated "V databázi chybí záznam o uživateli ".
     * 
     * @return translated "V databázi chybí záznam o uživateli "
     */
    @DefaultStringValue("V databázi chybí záznam o uživateli ")
    @Key("recordMissing")
    String recordMissing();

    /**
     * Translated "Zaslat žádost".
     * 
     * @return translated "Zaslat žádost"
     */
    @DefaultStringValue("Zaslat žádost")
    @Key("send")
    String send();
}
