package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>AbstractLocalizationProvider</code> implements most of the methods from {@link ILocalizationProvider}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

  /**
   * List of {@link ILocalizationListener} listeners
   */
  private final List<ILocalizationListener> listeners;

  /**
   * Constructor for <code>AbstractLocalizationProvider</code>
   */

  public AbstractLocalizationProvider(){
    this.listeners = new ArrayList<>();
  }

  @Override
  public void addLocalizationListener(ILocalizationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeLocalizationListener(ILocalizationListener listener) {
    listeners.remove(listener);
  }

  /**
   * Goes through all listeners from <code>listeners</code> and calls its method
   */
  public void fire(){
    listeners.forEach(ILocalizationListener::localizationChanged);
  }
}
