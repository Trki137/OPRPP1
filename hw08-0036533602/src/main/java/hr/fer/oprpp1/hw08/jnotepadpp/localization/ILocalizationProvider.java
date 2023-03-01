package hr.fer.oprpp1.hw08.jnotepadpp.localization;

/**
 * Interface <code>ILocalizationProvider</code> defines a way to communicate with object that translates text to current language
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public interface ILocalizationProvider {
  void addLocalizationListener(ILocalizationListener listener);
  void removeLocalizationListener(ILocalizationListener listener);
  String getString(String value);
  String getCurrentLanguage();
}
