package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class <code>LocalizationProvider</code> extends functionality of {@link AbstractLocalizationProvider}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

  /**
   * Currently used language
   */
  private String language;

  /**
   * Maps translations from files that are in <code>pathToTranslations</code>
   */
  private ResourceBundle bundle;

  /**
   * Path to properties file for language translations
   */
  private final String pathToTranslations = "languages.";

  /**
   * An instance of <code>LocalizationProvider</code>
   */
  private static final LocalizationProvider instance = new LocalizationProvider();

  /**
   * Private constructor for <code>LocalizationProvider</code>
   * Sets language that is used for default
   */
  private LocalizationProvider(){
    this.language = Locale.getDefault().getLanguage().toString();
    this.bundle = ResourceBundle.getBundle(pathToTranslations + language, Locale.getDefault());
  }

  /**
   * Returns instance of <code>LocalizationProvider</code>
   * @return instance of <code>LocalizationProvider</code>
   */
  public static LocalizationProvider getInstance(){
    return instance;
  }

  /**
   * Sets new language
   * @param language new language for application
   */
  public void setLanguage(String language) {
    this.language = language;
    this.bundle = ResourceBundle.getBundle(pathToTranslations + language, Locale.forLanguageTag(language));

    this.fire();
  }

  @Override
  public String getString(String value) {
    return bundle.getString(value);
  }

  @Override
  public String getCurrentLanguage() {
    return language;
  }
}
