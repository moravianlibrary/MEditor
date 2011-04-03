package cz.fi.muni.xkremser.editor.request4Adding.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * '/home/kremser/workspace/MEditor/src/cz/fi/muni/xkremser/editor/request4Adding/client/RequestConstants.properties'
 * .
 */
public interface RequestConstants extends com.google.gwt.i18n.client.Constants {

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
