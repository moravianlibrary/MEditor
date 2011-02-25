package cz.fi.muni.xkremser.editor.request4Adding.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'/home/kremser/workspace/MEditor/src/cz/fi/muni/xkremser/editor/request4Adding/client/RequestConstants.properties'.
 */
public interface RequestConstants extends com.google.gwt.i18n.client.Constants {
  
  /**
   * Translated "Autorizace se nezdařila".
   * 
   * @return translated "Autorizace se nezdařila"
   */
  @DefaultStringValue("Autorizace se nezdařila")
  @Key("authFailed")
  String authFailed();

  /**
   * Translated "Pokud si přejete zaslat žádost o přidání Vašeho uživatelského účtu, klikněte prosím na tlačítko pod textem.".
   * 
   * @return translated "Pokud si přejete zaslat žádost o přidání Vašeho uživatelského účtu, klikněte prosím na tlačítko pod textem."
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
