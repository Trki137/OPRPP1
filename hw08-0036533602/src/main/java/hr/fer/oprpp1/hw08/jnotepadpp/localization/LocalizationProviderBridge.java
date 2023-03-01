package hr.fer.oprpp1.hw08.jnotepadpp.localization;

/**
 * Class <code>LocalizationProviderBridge</code> extends {@link AbstractLocalizationProvider} and acts like a bridge between swing application and <code>LocalizationProvider</code>
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

  /**
   * Main localization provider
   */
  private final ILocalizationProvider parent;

  /**
   * Listener for this localization provider
   */
  private final ILocalizationListener listener;

  /**
   * Language active before disconnecting from parent
   */
  private String cachedLanguage;

  /**
   * Flag that tels us if <code>LocalizationProviderBridge</code> is connected with <code>parent</code>
   */
  private boolean connected;

  /**
   * Constructor for LocalizationProviderBridge
   * @param parent instance of {@link ILocalizationProvider}
   */
  public LocalizationProviderBridge(ILocalizationProvider parent){
    this.connected = false;
    this.parent = parent;
    this.listener = (this::fire);
  }

  /**
   * Removes listener from parent
   */
  public void disconnect(){
    if(!connected) return;

    connected = false;
    parent.removeLocalizationListener(listener);
    cachedLanguage = parent.getCurrentLanguage();
  }

  /**
   * Adds listener to parent
   */

  public void connect(){
    if(connected) return;

    connected = true;
    parent.addLocalizationListener(listener);

    if(cachedLanguage != null && !cachedLanguage.equals(parent.getCurrentLanguage())){
      fire();
      cachedLanguage = parent.getCurrentLanguage();
    }
  }

  @Override
  public String getCurrentLanguage() {
    return parent.getCurrentLanguage();
  }

  @Override
  public String getString(String value) {
    return parent.getString(value);
  }
}
